package com.example;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.example.service.ClienteService;
import com.example.service.PedidoService;

public class Main {
    public static void main(String[] args) {
        ClienteService cs = new ClienteService();
        cs.altaCliente("Juan", "juan@gmail.com");
        PedidoService ps = new PedidoService();
        ps.hacerPedido(LocalDate.of(2026, 2, 24), BigDecimal.valueOf(12.5), cs.recuperarClientePorEmail("juan@gmail.com"));
        

    }
}