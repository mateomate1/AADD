package es.ciudadescolar.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Direccion;
import es.ciudadescolar.persistencia.dao.AlumnoDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class AlumnoServicio 
{
    private static final Logger LOG = LoggerFactory.getLogger(AlumnoServicio.class);

    public void registrarAlumno(String nombreAlumno, String emailAlumno) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorEmail(emailAlumno);
            if (alumno == null) 
            {
                alumno = new Alumno(nombreAlumno,emailAlumno);
                alumnoDAO.guardar(alumno);
            }
            else
                LOG.warn("El alumno con email ["+emailAlumno+"] ya estaba registrado");

            trans.commit();
            LOG.info("Alumno registrado: "+ alumno.toString());
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error durante la transacción: "+ e.getMessage());

            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        } 
        finally 
        {
            try 
            {
                if (em != null && em.isOpen()) 
                {
                    em.close();
                    LOG.debug("Cerrado EntityManager");
                }
            } 
            catch (RuntimeException e) 
            {
                LOG.error("Error cerrando EntityManager:: "+ e.getMessage());
                // La transacción ya se ha intentado commit o rollback. 
                // Aquí no propagamos la excepción para evitar ocultar la excepción original que pudo motivar el rollback o fallo de negocio.
            }
        }
     }

    public Long registrarAlumnoConDireccion(String nombreAlumno, String emailAlumno, String calleAlumno, Integer numCalleAlumno) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        Long idAlumno = -1L;

        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorEmail(emailAlumno);
            if (alumno == null) 
            {
                alumno = new Alumno(nombreAlumno,emailAlumno);
                Direccion dirAlumno = new Direccion(calleAlumno, numCalleAlumno);
                alumno.setDirec(dirAlumno);

                // como hemos indicado CASCADETYPE.ALL se incluye el persist)
                alumnoDAO.guardar(alumno);
            }
            else
                LOG.warn("El alumno con email ["+emailAlumno+"] ya estaba registrado");

            trans.commit();
            LOG.info("Alumno registrado con direccion: "+ alumno.toString());
            
            idAlumno = alumno.getId();
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error durante la transacción: "+ e.getMessage());

            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        } 
        finally 
        {
            try 
            {
                if (em != null && em.isOpen()) 
                {
                    em.close();
                    LOG.debug("Cerrado EntityManager");
                }
            } 
            catch (RuntimeException e) 
            {
                LOG.error("Error cerrando EntityManager: "+ e.getMessage());
                // La transacción ya se ha intentado commit o rollback. 
                // Aquí no propagamos la excepción para evitar ocultar la excepción original que pudo motivar el rollback o fallo de negocio.
            }
        }

        return idAlumno;
     }

     /**
      * Este método me permite cambiar la dirección de un alumno existente
      * Con ello queremos probar la directiva orphanRemoval = true 
      * @param idAlumno
      * @param calleNueva
      * @param numCalleNuevo
      */
     public void cambiarDireccionAlumno(Long idAlumno, String calleNueva, Integer numCalleNuevo)
     {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º Buscamos alumno existente 
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null) 
            {
                LOG.warn("Alumno a cambiar dirección NO EXISTE. Abortando operación de actualización.");
                trans.rollback();
                return;
            }
            Direccion dirNueva = new Direccion(calleNueva,numCalleNuevo);
            alumno.setDirec(dirNueva);
            trans.commit();
            
            LOG.info("Cambiada la dirección del alumno: "+ alumno.toString());
            
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error durante la transacción: "+ e.getMessage());

            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        } 
        finally 
        {
            try 
            {
                if (em != null && em.isOpen()) 
                {
                    em.close();
                    LOG.debug("Cerrado EntityManager");
                }
            } 
            catch (RuntimeException e) 
            {
                LOG.error("Error cerrando EntityManager: "+ e.getMessage());
                // La transacción ya se ha intentado commit o rollback. 
                // Aquí no propagamos la excepción para evitar ocultar la excepción original que pudo motivar el rollback o fallo de negocio.
            }
        }

     }

     public void darBajaAlumno(Long idAlumno)
     {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º Buscamos alumno existente 
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null) 
            {
                LOG.warn("Alumno a borrar NO EXISTE. Abortando operación de borrado.");
                trans.rollback();
                return;
            }
            alumnoDAO.eliminar(alumno);
            trans.commit();
            
            LOG.info("Alumno dado de baja: "+idAlumno);
            
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error durante la transacción: "+ e.getMessage());

            if (trans != null && trans.isActive()) 
            {
                trans.rollback();
                LOG.debug("Rollback de la transacción");
   
            }
            throw e; // Propagamos error al main o a la capa superior
        } 
        finally 
        {
            try 
            {
                if (em != null && em.isOpen()) 
                {
                    em.close();
                    LOG.debug("Cerrado EntityManager");
                }
            } 
            catch (RuntimeException e) 
            {
                LOG.error("Error cerrando EntityManager: "+ e.getMessage());
                // La transacción ya se ha intentado commit o rollback. 
                // Aquí no propagamos la excepción para evitar ocultar la excepción original que pudo motivar el rollback o fallo de negocio.
            }
        }

     }
}
