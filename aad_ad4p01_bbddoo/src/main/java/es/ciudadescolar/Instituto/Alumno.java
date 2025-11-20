package es.ciudadescolar.Instituto;

public class Alumno {
    String expediente;
    String nombre;
    Integer edad;

    public Alumno(String expediente, String nombre, Integer edad) {
        this.edad = edad;
        this.expediente = expediente;
        this.nombre = nombre;
    }

    public Alumno() {
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Alumno{");
        sb.append("expediente=").append(expediente);
        sb.append(", nombre=").append(nombre);
        sb.append(", edad=").append(edad);
        sb.append('}');
        return sb.toString();
    }


}
