package com.example.dao;

import java.util.List;

import com.example.model.Pedido;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class PedidoDAO {
    private final EntityManager em;

    public PedidoDAO(EntityManager em) {
        this.em = em;
    }

    public void crearPedido(Pedido p){
        em.persist(p);
    }

    public void eliminarPedido(Pedido p){
        em.remove(p);
    }

    public Pedido findById(Short id){
        return em.find(Pedido.class, id);
    }

    public List<Pedido> getPedidos(){
        Query q = em.createQuery("SELECT p FROM Pedido p", Pedido.class);
        return q.getResultList();
    }
}
