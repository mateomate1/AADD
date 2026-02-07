package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "examen")
public class Examen implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "modulo", nullable = false)
    private String modulo;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "nota")
    private Double nota;

    // Se usa many to one pq un alumno puede tener varios examenes
    // Many Examenes to One Alumno
    @ManyToOne
    @JoinColumn(name = "alumno")
    private Alumno alumno;

    public Examen() {
    }

    public Examen(LocalDate fecha, String modulo) {
        this.fecha = fecha;
        this.modulo = modulo;
    }

    public Examen(String modulo) {
        this.modulo = modulo;
        this.fecha = LocalDate.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.modulo);
        hash = 97 * hash + Objects.hashCode(this.fecha);
        hash = 97 * hash + Objects.hashCode(this.nota);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Examen other = (Examen) obj;
        if (!Objects.equals(this.modulo, other.modulo)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.fecha, other.fecha)) {
            return false;
        }
        return Objects.equals(this.nota, other.nota);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Examen{");
        sb.append("id=").append(id);
        sb.append(", modulo=").append(modulo);
        sb.append(", fecha=").append(fecha);
        sb.append(", nota=").append(nota);
        sb.append('}');
        return sb.toString();
    }

}
