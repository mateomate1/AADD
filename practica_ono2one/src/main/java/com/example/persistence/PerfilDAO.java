package com.example.persistence;

import java.util.List;

import com.example.model.Perfil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

/**
 * Las clases dao no hacen cambios en la base de datos directamente, solo guarda
 * esos cambios en el contexto de persistencia, cuando el EntityManager haga un
 * commit ya se guarda todo
 */
public class PerfilDAO {
    private final EntityManager em;

    public PerfilDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Perfil p) {
        em.persist(p);
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
