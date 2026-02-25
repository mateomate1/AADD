package com.example.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.ClienteDAO;
import com.example.model.Cliente;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClienteService {
    private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

    public void altaCliente(String nombre, String email) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();

        try {
            et.begin();
            log.info("Comenzando transaccion");

            Cliente c = new Cliente();
            c.setNombre(nombre);
            c.setEmail(email);

            log.info("Cliente creado con exito");

            ClienteDAO dao = new ClienteDAO(em);

            dao.crearCliente(c);
            log.info("Cliente añadido a la unidad de persistencia");

            et.commit();
            log.info("Cliente dado de alta correctamente");
        } catch (RuntimeException e) {
            log.error("Error: {}", e.getMessage());
            et.rollback();
        } finally{
            try {
                if(em.isOpen()){
                    em.close();
                    log.info("Gestor de entidades cerrado");
                }
            } catch (RuntimeException e) {
                log.error("Error cerrando el gestor de entidades");
            }
        }
    }

    public List<Cliente> recuperarClientes() {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAO dao = new ClienteDAO(em);
        return dao.getClientes();
    }

    public Cliente recuperarClientePorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteDAO dao = new ClienteDAO(em);
        return dao.getClientePorEmail(email);
    }

    public void eliminarCliente(String email){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        ClienteDAO dao = new ClienteDAO(em);
        try {
            et.begin();
            log.info("Empezando transaccion");
            dao.eliminarCliente(dao.getClientePorEmail(email));
            log.info("Eliminando cliente del contexto de persistencia");
            et.commit();
            log.info("Cliente eliminado de la base de datos");
        } catch (RuntimeException e) {
            log.error("Error eliminando cliente: {}",e.getMessage());
            et.rollback();
        } finally {
            try {
                if(em.isOpen())
                    em.close();
                log.info("Gestor de entidades cerrado");
            } catch (RuntimeException e) {
                log.error("Error cerrando el gestor de entidades: {}", e.getMessage());
            }
        }
    }
}
