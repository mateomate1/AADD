package es.ciudadescolar.servicios;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Examen;
import es.ciudadescolar.persistencia.dao.ExamenDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ExamenServicio {
    private static final Logger LOG = LoggerFactory.getLogger(ExamenServicio.class);

    /**
     * Este método tiene poca "chicha" pero nos vale para evidenciar cómo desde examen se puede acceder 
     * al alumno que lo ha realizado (1:N bidir)
     * Al tener la certeza de que en este método no modificamos nada, puedo evitar el uso de la transacción.
     * En otros métodos que quizás a futuro tenga algún cambio que requiera persistir, mejor mantener estructura.
     * @param idExamen
     */
    public void mostrarExamenConNombreAlumno(Long idExamen) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        
        try 
        {
            ExamenDAO examenDAO = new ExamenDAO(em);
            
            Examen examen = examenDAO.buscarPorId(idExamen);
            if (examen != null) 
            {
                LOG.info("Examen de: " + examen.getModulo());
    
                // no puede existir examen sin alumno
                LOG.info("Alumno: " + examen.getAlumno().getNombre());
            }
            else
            {
                LOG.warn("Examen no encontrado: "+idExamen);
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
     * Este método tiene la lógica de negocio necesaria para recupera los examenes aprobados
     * se apoya en un método del DAO de examen: listarExamenesPorNota 
     * @return  lista de exámenes aprobados
     */
    public List<Examen> recuperaExamenesAprobados()
    {
        EntityManager em = JPAUtil.getEntityManager();
        List<Examen> lista = null;
        try 
        {
            ExamenDAO examenDAO = new ExamenDAO(em);
            lista = examenDAO.listarExamenesPorNota(Double.valueOf(5), Double.valueOf(10));
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
        return lista;
    }

    /**
     * Este método tiene la lógica de negocio necesaria para recupera los examenes suspensos
     * se apoya en un método del DAO de examen: listarExamenesPorNota
     * @return lista de exámenes suspensos
     */
    public List<Examen> recuperaExamenesSuspensos()
    {
        EntityManager em = JPAUtil.getEntityManager();
        List<Examen> lista = null;
        try 
        {
            ExamenDAO examenDAO = new ExamenDAO(em);
            lista = examenDAO.listarExamenesPorNota(Double.valueOf(0), Double.valueOf(4.9));
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
        return lista;
    }
    
    /**
     * Este método tiene la lógica de negocio necesaria para modificar la nota de un determinado examen ya evaluado
     * por ejemplo en el escenario de una revisión de examen.
     * @param idExamen
     * @param nuevaNota
     */
    public void modificarNota(Long idExamen, Double nuevaNota)
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            ExamenDAO examenDAO = new ExamenDAO(em);
            
            // 1º BuscaMOS examen existente
            Examen examen = examenDAO.buscarPorId(idExamen);
            if (examen == null) 
            {
                LOG.warn("El examen a modificar nota no existe.");
                trans.rollback();
                return;
            }
            // examen está administrado, luego...
            examen.setNota(nuevaNota);
            trans.commit();

            LOG.info("Examen modificado (cambiar nota)"+ examen.toString());
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

    /**
     * Este método tiene la lógica de negocio necesaria para fijar la nota de un determinado examen no calificación aún
     * @param idExamen
     * @param nuevaNota
     */
    public void fijarNota(Long idExamen, Double nuevaNota)
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            ExamenDAO examenDAO = new ExamenDAO(em);
            
            // 1º BuscaMOS examen existente
            Examen examen = examenDAO.buscarPorId(idExamen);
            if (examen == null) 
            {
                LOG.warn("El examen a fijar nota no existe.");
                trans.rollback();
                return;
            }

            if (examen.getNota() != null)
            {
                LOG.warn("El examen a fijar nota ya tenía nota.");
                trans.rollback();
                return;
            }
            // examen está administrado, luego...
            examen.setNota(nuevaNota);
            trans.commit();

            LOG.info("Examen modificado (ponerle nota): "+ examen.toString());
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
}
