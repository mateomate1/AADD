package es.ciudadescolar;

import java.io.Serializable;

/**
 * Clase Modulo que representa un módulo profesional en un sistema escolar.
 * Contiene atributos como el nombre del módulo, horas semanales, profesor, curso y contraseña AV.
 * 
 * @author Mateo Ayarra
 * @version 1.0
 */
public class Modulo implements Serializable{
    //Módulo profesional|horas semanales|profesor|curso|contraseña AV
    String modulo;
    Double horas;
    String profesor;
    Integer curso;
    String contrasena;

    /**
     * Constructor vacio de la clase Modulo.
     */
    public Modulo() {}
    /**
     * Constructor de la clase Modulo.
     *
     * @param modulo
     * @param horas
     * @param profesor
     * @param curso
     * @param contrasena
     */
    public Modulo(String modulo, Double horas, String profesor, Integer curso, String contrasena) {
        this.modulo = modulo;
        this.horas = horas;
        this.profesor = profesor;
        this.curso = curso;
        this.contrasena = contrasena;
    }
    public Modulo(String[] datos){
        this.modulo = datos[0];
        this.horas = Double.parseDouble(datos[1]);
        this.profesor = profesor;
        this.curso = curso;
        this.contrasena = contrasena;
    }
    /**
     * Getter del módulo profesional.
     * @return
     */
    public String getModulo() {
        return modulo;
    }
    /**
     * Setter del módulo profesional.
     * @param modulo
     */
    public void setModulo(String modulo) {
        this.modulo = modulo;
    }
    /**
     * Getter de las horas semanales.
     * @return
     */
    public Double getHoras() {
        return horas;
    }
    /**
     * Setter de las horas semanales.
     * @param horas
     */
    public void setHoras(Double horas) {
        this.horas = horas;
    }
    /**
     * Getter del profesor.
     * @return
     */
    public String getProfesor() {
        return profesor;
    }
    /**
     * Setter del profesor.
     * @param profesor
     */
    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    /**
     * Getter del curso.
     * @return
     */
    public Integer getCurso() {
        return curso;
    }
    /**
     * Setter del curso.
     * @param curso
     */
    public void setCurso(Integer curso) {
        this.curso = curso;
    }
    /**
     * Getter de la contraseña AV.
     * @return
     */
    public String getContrasena() {
        return contrasena;
    }
    /**
     * Setter de la contraseña AV.
     * @param contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    /**
     * Método toString para representar el objeto Modulo como una cadena.
     */
    @Override
    public String toString() {
        return modulo + "|" + horas + "|" + profesor + "|" + curso
                + "|" + contrasena ;
    }
}
