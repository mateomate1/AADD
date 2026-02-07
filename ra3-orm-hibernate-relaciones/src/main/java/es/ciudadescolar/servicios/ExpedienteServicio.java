package es.ciudadescolar.servicios;

import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.Expediente;
import es.ciudadescolar.dominio.modelo.Expediente.Estado;
import es.ciudadescolar.persistencia.dao.AlumnoDAO;
import es.ciudadescolar.persistencia.dao.ExpedienteDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ExpedienteServicio {

    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteServicio.class);

    public Long crearExpediente(Long idAlumno) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        Long idExp = -1L;
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            AlumnoDAO alumnoDAO = new AlumnoDAO(em);
            
            // 1º BuscaMOS alumno existente o crear
            Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
            if (alumno == null) 
            {
                LOG.warn("El alumno sobre el que crear expediente no existe");
                trans.rollback();
                return idExp;
            }
            
            Expediente expediente = new Expediente();
            Random random = new Random();
            // generamos un valor aleatorio entre 10.000 y 100.000
            String valor = String.valueOf(random.nextInt(100000 - 10000 + 1) + 10000);
            
            expediente.setNumeroExpediente(valor);

            expediente.setEstado(Estado.ABIERTO);
            expediente.setAlumno(alumno);
            
            alumno.setExpediente(expediente);

            ExpedienteDAO dao = new ExpedienteDAO(em);
            dao.guardar(expediente); 

            trans.commit();
            LOG.info("Expediente creado para el alumno: "+ alumno.toString());
            
            idExp = expediente.getId();
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
        return idExp;
     }

     public void cerrarExpediente(Long idExpediente) 
     {

        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try {
            trans.begin();

            ExpedienteDAO dao = new ExpedienteDAO(em);
            Expediente exp = dao.buscarPorId(idExpediente);

            if (exp == null) {
                 LOG.warn("El expediente a cerrar no existe");
                 trans.rollback();
                 return;
            }
            
            if (exp.getEstado() == Estado.CERRADO) {
                    LOG.warn("Expediente ya estaba cerrado");
                    trans.rollback();
                 return;
            }
            
            exp.setEstado(Estado.CERRADO);
            
            trans.commit();

            LOG.info("Expediente cerrado satisfactoriamente:", idExpediente);

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
     * Este método tiene poca "chicha" pero nos vale para evidenciar cómo desde expediente se puede acceder 
     * al alumno correspondiente (1:1 bidir)
     * Al tener la certeza de que en este método no modificamos nada, puedo evitar el uso de la transacción.
     * En otros métodos que quizás a futuro tenga algún cambio que requiera persistir, mejor mantener estructura.
     * @param idAlumno
     */
    public void mostrarExpedienteConNombreAlumno(Long idExpediente) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        
        try 
        {
            ExpedienteDAO expedienteDAO = new ExpedienteDAO(em);
            
            Expediente expediente = expedienteDAO.buscarPorId(idExpediente);
            if (expediente != null) 
            {
                LOG.info("Expediente: " + expediente.getNumeroExpediente());
                // no puede existir expediente sin alumno
                LOG.info("Alumno: " + expediente.getAlumno().getNombre());
            }
            else    
            {
                LOG.warn("Expediente no encontrado: "+idExpediente);
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
}
