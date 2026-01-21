package es.ciudadescolar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.servicios.AlumnoServicio;
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
            AlumnoServicio service = new AlumnoServicio();
            // Al acceder por primera vez a cualquier método estático, Java inicializa los campos estáticos.
            // Por tanto el EntityManagerFactory de JPAUtil se instanciará cuando el service haga JPAUtil.getEntityManager() por primera vez.
            service.registrarAlumno("Anchón García", "agarcia@ciudadescolar.es");
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
