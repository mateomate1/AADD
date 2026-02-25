package com.example.persistence;

import java.util.List;

import com.example.model.Usuario;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class UsuarioDAO {
    private final EntityManager em;

    public UsuarioDAO(EntityManager em) {
        this.em = em;
    }

    public void guardar(Usuario u){
        em.persist(u);
    }

    public void eliminar(Usuario u){
        em.remove(u);
    }

    public void actualizar(Usuario u){
        em.merge(u);
    }

    public Usuario findById(String id){
        return em.find(Usuario.class, id);
    }

    public Usuario findByEmail(String email){
        TypedQuery<Usuario> consulta = em.createQuery("SELECT u FROM Usuario WHERE u.email = :email", Usuario.class);
        consulta.setParameter("email", email);
        List<Usuario> resultado = consulta.getResultList();
        if(resultado.size() > 1)
            return null;
        else
            return resultado.get(0);
    }

    public List<Usuario> getUsuarios(){
        Query consulta = em.createQuery("SELECT u FROM Usuario u");
        List<Usuario> usuarios = consulta.getResultList();
        return usuarios;
    }
}
