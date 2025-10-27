package es.ciudadescolar.instituto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Alumno {

    private String nombre;

    private String expediente;

    @JsonIgnore
    private Integer edad;

    @JsonProperty("fecha")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha_nacimiento;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    @JsonIgnore
    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Alumno(String nombre, String expediente, Integer edad, LocalDate fecha_nac) {
        this.nombre = nombre;
        this.expediente = expediente;
        this.edad = edad;
        this.fecha_nacimiento = fecha_nac;
    }

    public Alumno() {

    }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String fechaFormateada = fecha_nacimiento.format(formato);
        return "Alumno [nombre=" + nombre + ", expediente=" + expediente + ", edad=" + edad + ", fecha nacimiento="
                + fechaFormateada + "]";
    }

    public LocalDate getFecha_nacimiento() {
        return fecha_nacimiento;
    }

    public void setFecha_nacimiento(LocalDate fecha_nacimiento) {
        this.fecha_nacimiento = fecha_nacimiento;
    }

}
