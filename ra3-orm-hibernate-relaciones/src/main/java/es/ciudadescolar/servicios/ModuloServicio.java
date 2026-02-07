package es.ciudadescolar.servicios;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.dominio.modelo.Modulo;
import es.ciudadescolar.persistencia.dao.ModuloDAO;
import es.ciudadescolar.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ModuloServicio {
    private static final Logger LOG = LoggerFactory.getLogger(ExpedienteServicio.class);

    public Long crearModulo(Long codModulo, String curso, String nombre) 
    {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction trans = em.getTransaction();

        Long idMod = -1L;
        try 
        {
            trans.begin();
            
            // Podríamos tener varios DAOs, todos ellos con el mismo EntityManager y bajo la misma transacción
            ModuloDAO moduloDAO = new ModuloDAO(em);
            
            // 1º BuscaMOS alumno existente o crear
            Modulo modulo = moduloDAO.buscarPorId(codModulo);
            if (modulo != null) 
            {
                idMod = codModulo;
                LOG.warn("El modulo ya existe");
                trans.rollback();
                return idMod;
            }
            
            Modulo moduloNuevo= new Modulo();
            moduloNuevo.setCodigo(codModulo);
            moduloNuevo.setCurso(curso);
            moduloNuevo.setNombre(nombre);
            
            moduloDAO.guardar(moduloNuevo); 

            trans.commit();
            LOG.info("Modulo creado correctamente: "+ moduloNuevo.toString());
            
            idMod = moduloNuevo.getCodigo();
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
        return idMod;
     }
}
