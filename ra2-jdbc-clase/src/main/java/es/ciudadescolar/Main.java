package es.ciudadescolar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Instituto.Alumno;
import es.ciudadescolar.util.DBManager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        DBManager manager = new DBManager();
        List<Alumno> alumnos = manager.getAlumnos();
        for (Alumno a : alumnos) {
            log.info(a.toString());
        }
        manager.cerrarDB();
    }
}