package es.ciudadescolar.Instituto;

import java.util.ArrayList;
import java.util.List;

public class Instituto 
{
    private String centro;
    private String curso;
    private Integer codigo;
    private List<Alumno> alumnos;
    public String getCentro() {
        return centro;
    }
    public void setCentro(String centro) {
        this.centro = centro;
    }
    public String getCurso() {
        return curso;
    }
    public void setCurso(String curso) {
        this.curso = curso;
    }
    public Integer getCodigo() {
        return codigo;
    }
    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }
    public List<Alumno> getAlumnos() {
        return alumnos;
    }
    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }
    public Instituto()
    {
        
    }
    public Instituto(String centro, String curso, Integer codigo)
    {
        this.centro=centro;
        this.curso=curso;
        this.codigo=codigo;
    }

    public void addAlumno(Alumno al)
    {
        if (this.alumnos ==null)
        {
             this.alumnos= new ArrayList<Alumno>();
        }
        this.alumnos.add(al);
    }
    
    public void removeAlumno(Alumno al)
    {
        if (this.alumnos !=null)
        {
             alumnos.remove(al);
        }
    }
    @Override
    public String toString() {
        return "Instituto [centro=" + centro + ", curso=" + curso + ", codigo=" + codigo + "]";
    }
    
}
