package es.ciudadescolar.servicios;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Direccion;
import es.ciudadescolar.dominio.modelo.Examen;
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

    /**
     * Este método tiene poca "chicha" pero nos vale para evidenciar cómo desde alumno se puede acceder 
     * al expediente correspondiente en caso de tenerlo (1:1 bidir)
     * Al tener la certeza de que en este método no modificamos nada, puedo evitar el uso de la transacción.
     * En otros métodos que quizás a futuro tenga algún cambio que requiera persistir, mejor mantener estructura.
     * @param idAlumno
     */
    public void mostrarNombreAlumnoConExpediente(Long idAlumno) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        
        try 
        {
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno != null) 
            {
                LOG.info("Alumno: " + alumno.getNombre());

                if (alumno.getExpediente() != null) 
                {
                     LOG.info("Expediente: " + alumno.getExpediente().getNumeroExpediente());
                }
            }
            else
            {
                LOG.warn("Alumno no encontrado: "+idAlumno);
            }
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
            }
        }
    }


    /**
     * Este método tiene poca "chicha" pero nos vale para evidenciar cómo desde alumno se puede acceder 
     * a los exámenes correspondientes en caso de tenerlos (1:N bidir)
     * Al tener la certeza de que en este método no modificamos nada, puedo evitar el uso de la transacción.
     * En otros métodos que quizás a futuro tenga algún cambio que requiera persistir, mejor mantener estructura.
     * @param idAlumno
     */
    public void mostrarNombreAlumnoConExamenes(Long idAlumno) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        
        try 
        {
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno != null) 
            {
                LOG.info("Alumno: " + alumno.getNombre());

                if (alumno.getExamenes() != null) 
                {
                    
                    List<Examen> examenes = alumno.getExamenes();
                    LOG.info("Num examenes realizados: " +examenes.size());
                    int i = 0;

                    for (Examen ex:examenes)
                        LOG.info("Examen ["+ ++i +"]: " + ex);
                }
            }
            else
            {
                LOG.warn("Alumno no encontrado: "+idAlumno);
            }
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
            }
        }
    }

    /**
     * Como tenemos fijado el parámetro CASCADE PERSIST, al añadir un nuevo examen a un alumno 
     * en estado administrado, se guardará automáticamente el examen sin necesidad de utilizar
     * un método explícitamente del ExamenDAO. 
     * @param idAlumno
     * @param modulo
     * @param fecha
     * @param nota
     * @return devuelve el id del nuevo examen realizado el alumno 
     */
    public Long aniadirExamenAAlumno(Long idAlumno, String modulo, LocalDate fecha, Double nota) {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        Long idExam = -1L;
        try 
        {
            trans.begin();
            
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null) 
            {
                LOG.warn("El alumno al que añadir examen no existe.");
                trans.rollback();
            }
            else
            {
                Examen examen = new Examen();
                examen.setModulo(modulo);
                examen.setFecha(fecha);
                examen.setNota(nota);

                // Nosotros debemos relacionar las entidades alumno y examen:
                examen.setAlumno(alumno);
                alumno.aniadirExamen(examen);
                
                // como está habilitado el persist en cascada desde Alumno a Examen, 
                // se guardará automáticamente el nuevo examen sin hacer "examenDAO.guardar(examen)"
                trans.commit();

                LOG.info("Examen añadido al alumno["+idAlumno+"]: "+ examen.toString());
                idExam = examen.getId();
            }
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

        return idExam;
    }

}
