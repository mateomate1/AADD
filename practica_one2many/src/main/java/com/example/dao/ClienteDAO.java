package com.example.dao;

import java.util.List;

import com.example.model.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

public class ClienteDAO {
    private final EntityManager em;

    public ClienteDAO(EntityManager em) {
        this.em = em;
    }

    public void crearCliente(Cliente c){
        em.persist(c);
    }

    public void eliminarCliente(Cliente c){
        em.remove(c);
    }

    public Cliente buscar(Integer id){
        return em.find(Cliente.class, id);
    }

    public List<Cliente> getClientes(){
        Query q = em.createQuery("SELECT c FROM Cliente");
        // List<Cliente>
        return q.getResultList();
    }

    public Cliente getClientePorEmail(String email){
        TypedQuery q = em.createQuery("SELECT c FROM Cliente c WHERE c.email = :email", Cliente.class);
        q.setParameter("email", email);
        List<Cliente> resultado = q.getResultList();
        if(resultado.size() == 1)
            return resultado.get(0);
        else
            return null;
    }
}
