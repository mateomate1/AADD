package com.example.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.dao.PedidoDAO;
import com.example.model.Cliente;
import com.example.model.Pedido;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PedidoService {
    private static final Logger log = LoggerFactory.getLogger(PedidoService.class);

    public void hacerPedido(LocalDate fecha, BigDecimal importe, Cliente c) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        PedidoDAO dao = new PedidoDAO(em);

        try {
            et.begin();
            log.info("Comienza la transaccion");
            Pedido p = new Pedido();
            p.setFecha(fecha);
            p.setImporte(importe);
            p.setCliente(c);
            dao.crearPedido(p);
            log.info("Pedido añadido a la uidad de persistencia");
            et.commit();
            log.info("Pedido creado correctamente");
        } catch (RuntimeException e) {
            et.rollback();
            log.error("Error haciendo pedido, se procede a hacer rollback");
        } finally {
            try {
                if (em.isOpen()) {
                    em.close();
                }
            } catch (RuntimeException e) {
                log.error("Error cerrando entity manager");
            }
        }
    }

    public void eliminarPedidos(List<Pedido> pedidos){
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        PedidoDAO dao = new PedidoDAO(em);
        try {
            et.begin();
            for (Pedido p : pedidos) {
                Pedido pp = dao.findById(p.getId());
                dao.eliminarPedido(pp);
            }
            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            log.error("Error eliminando los pedidos");
        } finally {
            try {
                if(em.isOpen())
                    em.close();
                log.info("Gestor de entidades cerrado");
            } catch (RuntimeException e) {
                log.error("No se pudo cerrar el gestor de entidades");
            }
        }
    }
}
