package es.ciudadescolar.instituto;

public class Alumno {
    private String expediente;
    private String nombre;
    private Integer edad;

    //Hay que incluir el constructor por defecto porque jackson lo necesita para serializar y deserializar
    public Alumno() {
    }

    public Alumno(Integer edad, String expediente, String nombre) {
        this.edad = edad;
        this.expediente = expediente;
        this.nombre = nombre;
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
