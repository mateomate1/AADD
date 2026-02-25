package com.cheatsheet.RA3.One2One.persistence;

import java.util.List;

import com.cheatsheet.RA3.One2One.model.Perfil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class PerfilDAO {
    private final EntityManager em;

    public PerfilDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Perfil p) {
        em.getTransaction().begin();
        em.persist(p);
        em.getTransaction().commit();
    }

    public void eliminar(Perfil p) {
        em.remove(p);
    }

    public void actualizar(Perfil p) {
        em.merge(p);
    }

    public Perfil findById(Integer id) {
        return em.find(Perfil.class, id);
    }

    public Perfil findByUsername(String u) {
        TypedQuery<Perfil> consulta = em.createQuery("SELECT p FROM Perfil p WHERE p.username = :u ", Perfil.class);
        consulta.setParameter("u", u);
        List<Perfil> perfiles = consulta.getResultList();
        if (perfiles.size() > 1)
            return null;
        else
            return perfiles.get(0);
    }

    public List<Perfil> getPerfiles() {
        Query consulta = em.createQuery("SELECT p FROM Perfil p");
        List<Perfil> perfiles = consulta.getResultList();
        return perfiles;
    }

}
