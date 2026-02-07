package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="matricula")
public class Matricula implements Serializable{

    private static final long serialVersionUID = 1L;

    // PK compuesta 
    @EmbeddedId
    private ClaveMatricula clave = new ClaveMatricula();

    @ManyToOne
    @MapsId("codigoModulo") // Así debe llamarse el atributo dentro de ClaveMatricula que identifica al módulo
    @JoinColumn(name="id_modulo")
    private Modulo modulo;

    @ManyToOne
    @MapsId("codigoAlumno") // Así debe llamarse el atributo dentro de ClaveMatricula que identifica al alumno
    @JoinColumn(name="id_alumno")
    private Alumno alumno;

    @Column(name = "calificacion", nullable = true)
    private Double nota;

    public Matricula() {
    }

    public Matricula(Modulo modulo, Alumno alumno) {
        this.modulo = modulo;
        this.alumno = alumno;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ClaveMatricula getClave() {
        return clave;
    }

    public void setClave(ClaveMatricula clave) {
        this.clave = clave;
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Matricula [clave=" + clave + ", nota=" + nota + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((clave == null) ? 0 : clave.hashCode());
        result = prime * result + ((modulo == null) ? 0 : modulo.hashCode());
        result = prime * result + ((alumno == null) ? 0 : alumno.hashCode());
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
        Matricula other = (Matricula) obj;
        if (clave == null) {
            if (other.clave != null)
                return false;
        } else if (!clave.equals(other.clave))
            return false;
        if (modulo == null) {
            if (other.modulo != null)
                return false;
        } else if (!modulo.equals(other.modulo))
            return false;
        if (alumno == null) {
            if (other.alumno != null)
                return false;
        } else if (!alumno.equals(other.alumno))
            return false;
        return true;
    }    
}
