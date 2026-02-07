package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="expediente")
public class Expediente implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numeroExpediente;

    // tipo enum: "clase" especial que representa un grupo de constantes.
    public static enum Estado {ABIERTO,CERRADO}
    
    // atributo de instancia con el valor por defecto alineado a la definión del campo en la BD 
    // no podemos con JPA meter dominios ni checks pues la implementación depende del SGBD en cuestión...
    @Enumerated(EnumType.STRING) // si no indico el tipo dentro del enumerado, JPA lo considera ORDINAL (0,1)
    @Column(name="estado", nullable = false)
    private Estado estado = Estado.ABIERTO;

    @Column(name="notamedia")
    private Double notaMedia;

    @Column(name="comentarios")
    private String observaciones;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alumno", unique = true, nullable = false)
    private Alumno alumno;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroExpediente() {
        return numeroExpediente;
    }

    public void setNumeroExpediente(String numeroExpediente) {
        this.numeroExpediente = numeroExpediente;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Double getNotaMedia() {
        return notaMedia;
    }

    public void setNotaMedia(Double notaMedia) {
        this.notaMedia = notaMedia;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    
}

