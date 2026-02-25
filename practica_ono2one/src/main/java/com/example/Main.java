package com.example;

import java.time.LocalDate;

import com.example.model.Perfil;
import com.example.model.Usuario;
import com.example.persistence.PerfilDAO;
import com.example.util.JPAUtil;

import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {
        EntityManager em = JPAUtil.getEntityManager();
        PerfilDAO dao = new PerfilDAO(em);
        Perfil p = new Perfil();
        p.setUsername("mateomate1");
        p.setPasswordHash("password");
        Usuario u = new Usuario();
        u.setNombre("Mateo");
        u.setApellidos("Ayarra Barbero");
        u.setfNac(LocalDate.of(2003, 4, 2));
        u.setEmail("mateoayarrabarbero@gmail.com");
        p.setUsuario(u);
        dao.guardar(p);
        em.close();
    }
}