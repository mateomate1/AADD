package com.cheatsheet.RA2;

public class Querys {
    public static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";
    public static final String RECUPERA_ALUMNO_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";
    public static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos (expediente, nombre, fecha_nac) VALUES (?, ?, ?)";

    /* Explicacion del select
    SELECT datos(dato1, dato2, da...)
    FROM tabla
    */
    public static final String RECUPERAR_GENERALIZADO = "SELECT dato1, dato2... FROM  tabla";
    /* Explicacion del select
    SELECT datos(dato1, dato2, da...)
    FROM tabla
    WHERE datoX(dato a parametrizar) = ?(? significa que el valor lo pondra el statement)
    Esta logica se puede complicar lo necesario
    */
    public static final String RECUPERAR_PARAMETRIZADO = "SELECT dato1, dato2... FROM  tabla WHERE datoX = ? AND datoY = ?";
}
