package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.time.LocalDate;

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
public class Examen implements Serializable
{
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
   
    /**
     * anotación para reflejar la relación 1:N entre Alumno y Examen (estamos en el lado del "muchos")
     * implícitamente JPA utiliza fetch EAGER del lado del "muchos", es decir, al recuperar un examen, te recupera
     * automáticamente la información del asociado alumno. Nosotros vamos ir en contra fijando explicitamente LAZY
     * 
     * Para asegurar que ningún examen se registra sin alumno asociado, también añadiremos nullable =false
     */
    @ManyToOne
    @JoinColumn(name = "alumno", nullable = false)
    private Alumno alumno;

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Examen() {}

    public Examen(String mod, LocalDate fecha)
    {
        this.modulo = mod;
        this.fecha = fecha;
    }

    public Examen (String mod)
    {
        this.modulo = mod;
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
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
        result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
        result = prime * result + ((nota == null) ? 0 : nota.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Examen other = (Examen) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (modulo == null) {
            if (other.modulo != null)
                return false;
        } else if (!modulo.equals(other.modulo))
            return false;
        if (fecha == null) {
            if (other.fecha != null)
                return false;
        } else if (!fecha.equals(other.fecha))
            return false;
        if (nota == null) {
            if (other.nota != null)
                return false;
        } else if (!nota.equals(other.nota))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Examen [id=" + id + ", modulo=" + modulo + ", fecha=" + fecha + ", nota=" + nota + "]";
    }
    
}
