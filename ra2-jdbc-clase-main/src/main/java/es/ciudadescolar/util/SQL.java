package es.ciudadescolar.util;

public class SQL {
    protected static final String RECUPERA_ALUMNOS = "SELECT expediente, nombre, fecha_nac FROM alumnos";

    protected static final String RECUPERA_ALUMNO_EXP = "SELECT expediente, nombre, fecha_nac FROM alumnos WHERE expediente = ? AND nombre = ?";

    protected static final String ALTA_NUEVO_ALUMNO = "INSERT INTO alumnos (expediente, nombre, fecha_nac) VALUES (?, ?, ?)";
}
