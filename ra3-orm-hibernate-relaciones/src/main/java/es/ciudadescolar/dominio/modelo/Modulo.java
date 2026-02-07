package es.ciudadescolar.dominio.modelo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="modulo")
public class Modulo  implements Serializable{

    private static final long serialVersionUID = 1L;

    // En esta ocasión, como cada módulo tiene por ley un determinado código, no quiero que sea auto_increment
    // ejemplo:  486 AADD , 488 DI, 489 PSP, 485 Programación de 1º
    @Id
    @Column(name="id")
    private Long codigo;

    @Column(name="curso", nullable = false)
    private String curso;

    @Column(name="nombre", nullable = false)
    private String nombre;

   @OneToMany(mappedBy = "modulo", cascade=CascadeType.PERSIST,fetch = FetchType.LAZY)
    private Set<Matricula> alumnos= new HashSet<Matricula>();

    public Modulo(){}

    public Modulo(Long cod, String curso, String nombre)
    {
        this.curso=curso;
        this.codigo=cod;
        this.nombre= nombre;
    }

    public Long getCodigo() {
        return codigo;
    }


    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }


    public String getCurso() {
        return curso;
    }


    public void setCurso(String curso) {
        this.curso = curso;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public Set<Matricula> getAlumnos() {
        return alumnos;
    }


    public void setAlumnos(Set<Matricula> alumnos) {
        this.alumnos = alumnos;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
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
        Modulo other = (Modulo) obj;
        if (codigo == null) {
            if (other.codigo != null)
                return false;
        } else if (!codigo.equals(other.codigo))
            return false;
        return true;
    }


    @Override
    public String toString() {
        return "Modulo [codigo=" + codigo + ", curso=" + curso + ", nombre=" + nombre + "]";
    }
    
    public boolean aniadirAlumno(Matricula al)
    {
        return alumnos.add(al);
    }

    public boolean quitarAlumno(Matricula al)
    {
        return alumnos.remove(al);
    }
    
}
