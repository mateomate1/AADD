package es.ciudadescolar.persistencia.dao;

import es.ciudadescolar.dominio.modelo.Modulo;
import jakarta.persistence.EntityManager;

public class ModuloDAO 
{
     private final EntityManager em;

    public ModuloDAO(EntityManager em) {
        this.em = em;
    }
    public void guardar(Modulo mod) {
        em.persist(mod);
    }

    public void eliminar(Modulo mod) {
        em.remove(mod);
    }

    public void actualizar(Modulo mod) {
        em.merge(mod);
    } 

    public Modulo buscarPorId(Long id) {
        return em.find(Modulo.class, id);
    }

}