package com.example.Model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="libro")
public class Libro implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_libro")
    private Long id;

    @Column(name="titulo", nullable=false)
    private String titulo;

    @Column(name="fechaPublicacion")
    private LocalDate fPublicacion;

    @ManyToMany
    private List<Autor> autor;


}
