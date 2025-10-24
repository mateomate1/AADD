package es.ciudadescolar;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.util.JsonManager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        File fichero = new File("alumnos.json");
        File ficheroSalidaJsonAlumnos = new File("nuevosAlumnos.json");
        List<Alumno> alumnosJson = JsonManager.parseoJsonAlumnos(fichero);
        if (!alumnosJson.isEmpty()) {
            // A partir de esta lista geberaremos un nuevo json con distinta estructura
            JsonManager.crearJsonAlumnos1(alumnosJson, ficheroSalidaJsonAlumnos);
        } else {
            log.warn("No se han recuperado alumnos del fichero Json: ");
        }
    }
}