package es.ciudadescolar.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Alumno;
import es.ciudadescolar.dominio.modelo.ClaveMatricula;
import es.ciudadescolar.dominio.modelo.Matricula;
import es.ciudadescolar.dominio.modelo.Modulo;
import es.ciudadescolar.persistencia.dao.AlumnoDAO;
import es.ciudadescolar.persistencia.dao.MatriculaDAO;
import es.ciudadescolar.persistencia.dao.ModuloDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class MatriculaServicio 
{
    private static final Logger LOG = LoggerFactory.getLogger(MatriculaServicio.class);
    
    public ClaveMatricula crearMatricula(Long idAlumno, Long idModulo) 
  {
    EntityManager em = JPAUtil.getEntityManager();
    EntityTransaction trans = em.getTransaction();
    ClaveMatricula idMat = null;
    try 
    {
        trans.begin();
        
        AlumnoDAO alumnoDAO = new AlumnoDAO(em);
        Alumno alumno = alumnoDAO.buscarPorId(idAlumno);
       
        ModuloDAO moduloDAO = new ModuloDAO(em);
        Modulo modulo = moduloDAO.buscarPorId(idModulo);
       
        MatriculaDAO matriculaDAO = new MatriculaDAO(em);
        
        if (alumno == null || modulo == null)
        {
            LOG.error("Imposible matricular: alumno o módulo inexistentes");
            trans.rollback();
            return idMat;
        }

        if (matriculaDAO.existeMatricula(idAlumno, idModulo)) 
        {
            LOG.warn("Matricula del alumno["+alumno.getNombre()+"] y modulo["+modulo.getNombre()+"] ya existe");
            trans.rollback();
            return idMat;  
        }

        Matricula matricula = new Matricula(modulo,alumno);
        matriculaDAO.guardar(matricula);
       
        trans.commit();
        LOG.info("Matricula registrada: "+ matricula.toString());
        idMat = matricula.getClave();
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
        return idMat;
    }

    public void fijarNota(ClaveMatricula idMatricula, Double notaFinal)
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        try
        {
            trans.begin();

            MatriculaDAO matriculaDAO = new MatriculaDAO(em);
            Matricula matricula = matriculaDAO.buscarPorId(idMatricula);
            if (matricula != null)
            {
                matricula.setNota(notaFinal);
                trans.commit();
                LOG.info("nota final actualizada en matricula["+idMatricula+"]");
                LOG.info (matricula.toString());
            }
            else
            {
                LOG.warn("La matricula a fijar nota, no existe");
                trans.rollback();
            }
        }
        catch(RuntimeException e)
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
