package es.ciudadescolar;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.util.DbManager;
public class Main 
{
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) 
    {
        DbManager manager = new DbManager();

        /*
        List<Alumno> alumnos = manager.getAllAlumnos();

        if (alumnos != null)
        {
            for (Alumno al: alumnos )
            {
                LOG.info(al.toString());
            }
        }

        Alumno albuscado = manager.getAlumnoPorExpYNom(1009,"paco");
        if (albuscado != null)
        {
            LOG.info("Alumno localizado: "+ albuscado);
        }
        else
        {
            LOG.warn("Alumno buscado no encontrado");
        }

        Alumno alumnoNuevo = new Alumno(3004,"David", Date.valueOf(LocalDate.of(2001,10,23)));
        if (!manager.altaDeAlumno(alumnoNuevo))
        {
            LOG.warn("No se ha podido dar de alta el alumno: "+alumnoNuevo);
        }

        Alumno alumnoX = new Alumno(3,"Francisco",Date.valueOf(LocalDate.of(2001,10,23)));

        alumnoX.setNombre("Manuel");

        manager.modificarNombreDeAlumno(alumnoX);

        manager.borrarAlumno(3004);

        */

        // invocación a procedimiento con parámetro de entrada
        int expAMostrar = 9;
        if (!manager.muestraAlumno(expAMostrar))
        {
            LOG.warn("No se ha invocado correctamente el SP para mostrar un determinado alumno ["+expAMostrar+"]");
        }

        // invocación a procedimiento con parámetro de salida
        int numAlumnos = manager.recuperaNumAlumnosSP();

        if (numAlumnos < 0)
        {
            LOG.warn("Error durante el invocado de SP para recuperar el número de alumnos");
        }
        else
        {
            LOG.info("El número de alumnos en la BD es ["+numAlumnos+"]");
        }

        // invocación a función 
        int expARecuperarNota = 41;
        int notaDelAlumno  = manager.getNotaAlumno(expARecuperarNota);
        if (notaDelAlumno == 0)
        {
           LOG.warn("No se ha recuperado nota mediante FUNC del expediente ["+expARecuperarNota+"]"); 
        }
        else
        {
            LOG.info("La nota del expediente ["+ expARecuperarNota+"] es ["+notaDelAlumno+"]");
        }

        manager.cerrarBd();
    }
}