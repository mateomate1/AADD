package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Instituto.Alumno;
import es.ciudadescolar.Util.Bbddoo;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Bbddoo db = new Bbddoo(new File("baseDatos.dbo"), true);
        Alumno al1 = new Alumno("1", "Paco", 12);
        Alumno al2 = new Alumno("2", "Andres", 13);
        Alumno al3 = new Alumno("3", "Manu", 11);

        db.guardarAlumno(al1);
        db.guardarAlumno(al2);
        db.guardarAlumno(al3);

        List<Alumno> alumnos = db.recuperarTodosAlumnos();

        for (Alumno alumno : alumnos) {
            log.info(alumno.toString());
        }
        String expediente = "1";
        log.info("Expediente " + expediente + ": "+ db.recuperarAlumnoPorExpediente(expediente).toString());

        db.borrarAlumno(al1);
        log.info("Despues de borrar el alumno con expediente " + expediente);
        db.recuperarTodosAlumnos();

        db.borrarTodosAlumnos();
        log.info("Despues de borrar todos los alumnos");
        db.recuperarTodosAlumnos();

        Alumno al4 = new Alumno("4", "Lucia", 14);
        Alumno al5 = new Alumno("5", "Marta", 12);
        db.guardarAlumno(al4);
        db.guardarAlumno(al5);

        db.modificarEdadAlumno(al5, 18);
        log.info("Despues de modificar la edad de Marta");
        db.recuperarTodosAlumnos();
        
        db.cerrar();
    }
}