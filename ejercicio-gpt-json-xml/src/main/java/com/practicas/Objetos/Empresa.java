package com.practicas.Objetos;

import java.util.List;

public class Empresa {
    private String nombre;
    private String sector;
    private List<Empleado> empleados;
    private Direccion direccion;

    public Empresa() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Empresa [nombre=" + nombre + ", sector=" + sector + ", empleados=" + empleados.size() + ", direccion="
                + direccion + "]";
    }

    

}
