package es.ciudadescolar;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.ClaveMatricula;
import es.ciudadescolar.dominio.modelo.Examen;
import es.ciudadescolar.servicios.AlumnoServicio;
import es.ciudadescolar.servicios.ExamenServicio;
import es.ciudadescolar.servicios.ExpedienteServicio;
import es.ciudadescolar.servicios.MatriculaServicio;
import es.ciudadescolar.servicios.ModuloServicio;
import es.ciudadescolar.util.JPAUtil;

/**
    En esta aplicación usaremos una arquitectura de responsabilidades multicapa (buenas prácticas) 

            main():  Inicia la aplicación, coordina el flujo de ejecución de los servicios, capturar excepciones de alto nivel y cierra recursos críticos al final (EntityManagerFactory)
            ↓        No contiene la lógica de negocio. No accede directamente a la base de datos.
            service: Es la capa de negocio: Implementar reglas y casos de negocio. Gestiona transacciones. Coordina múltiples DAOs en una transacción. Registra información relevante al negocio en el log.
            ↓        No contiene queries JPQL o SQL. No acede directamente a EntityManager (solo vía DAOs) salvo para la gestión de transacciones.
            dao:     Es la capa de persistencia: Encapsula todas las operaciones de acceso a la BD (CRUD y consultas específicas). 
            ↓        No contiene lógica de negocio. No decide reglas de transacción ni Logging (salvo excepciones técnicas de persistencia).
            domain   Es el modelo de datos (entidad JPA): Representa una tabla de la base de datos en forma de objeto Java. Define campos, relaciones, restricciones

            JPAUtil de forma transversal: Crea y mantiene un único EntityManagerFactory (EMF) y provee métodos para obtener un EntityManager nuevo cuando se necesite. Cierra el EMF al finalizar la aplicación.
*/
public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        LOG.debug ("Inicio de aplicación");
        try 
        {
            AlumnoServicio alumnoService = new AlumnoServicio();
            ExamenServicio examenService = new ExamenServicio();

            // relación 1:1 unidireccional
            Long idAlumno  = alumnoService.registrarAlumnoConDireccion("Paco Sanz", "psanz@ciudadescolar.es", "Avda. Miraflores", 33);
            if (idAlumno != null && idAlumno > -1L )
            {
                alumnoService.cambiarDireccionAlumno(idAlumno, "Calle Goya", 198);
            
                // relación 1:1 bidireccional puedo acceder desde expediente a alumno y viceversa
                ExpedienteServicio expedienteService = new ExpedienteServicio();
                Long idExpediente = expedienteService.crearExpediente(idAlumno);

                // al ser bidir, puedo acceder desde alumno a expediente
                alumnoService.mostrarNombreAlumnoConExpediente(idAlumno);
                
                // al ser bidir, puedo también acceder desde expediente a alumno
                if (!idExpediente.equals(Long.valueOf(-1L)))
                    expedienteService.mostrarExpedienteConNombreAlumno(idExpediente);

                Long idExamen1 =alumnoService.aniadirExamenAAlumno(idAlumno, "Acceso a Datos", LocalDate.of(2026,2,25), Double.valueOf(7.7));
                alumnoService.aniadirExamenAAlumno(idAlumno, "Desarrollo de Interfaces", LocalDate.of(2026,2,25), Double.valueOf(2.5));
                alumnoService.aniadirExamenAAlumno(idAlumno, "Programación de Servicios y Procesos", LocalDate.of(2026,2,23), Double.valueOf(5.2));
                
                // al ser bidir, puedo acceder desde alumno a examen
                alumnoService.mostrarNombreAlumnoConExamenes(idAlumno);

                // al ser bidir, puedo acceder desde examen a alumno
                if (idExamen1 != null && !idExamen1.equals(Long.valueOf(-1L)))
                {
                       examenService.mostrarExamenConNombreAlumno(idExamen1);
                        // tras la revisión, se decide cambiar la nota del examen...
                       examenService.modificarNota(idExamen1, Double.valueOf(8.1));
                }
            }

            // Como entidad fuerte, puede que sólo necesite aplicar lógica sobre exámenes (de forma independiente de los alumnos),
            // por ejemplo...
            List<Examen> aprobados = examenService.recuperaExamenesAprobados();
                LOG.info("Examenes aprobados:");
                for (Examen e:aprobados)
                {
                    LOG.info(e.toString());
                }
            
            List<Examen> suspensos = examenService.recuperaExamenesSuspensos();
                LOG.info("Examenes suspensos:");
                for (Examen e:suspensos)
                {
                    LOG.info(e.toString());
                }


            // relación N:M bidireccional entre Alumno y Modulo (con atributo en relación)
            ModuloServicio moduloService = new ModuloServicio();
            Long idModNuevo = moduloService.crearModulo(489L, "1º", "Programación");
            
            MatriculaServicio matriculaService = new MatriculaServicio();

            if (idAlumno != null && idAlumno > -1L && idModNuevo != null && idModNuevo > -1L)
            {
                 ClaveMatricula cm = matriculaService.crearMatricula(idAlumno, idModNuevo);
                 if (cm != null)
                    matriculaService.fijarNota(cm, 8.75);
            }   
        }
        catch (Exception e)
        {
            // Todas las excepciones (checked + unchecked)
            // Aseguramos que cualquier error en la aplicación se capture antes de cerrar recursos críticos (EMF).
            LOG.error ("Error en la aplicación: "+e.getMessage());
        } 
        finally 
        {
            JPAUtil.close(); 
        }
        LOG.debug ("Fin de aplicación");
    }
}
