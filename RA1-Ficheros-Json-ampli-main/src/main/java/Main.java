import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;
import es.ciudadescolar.instituto.Instituto;
import es.ciudadescolar.util.JsonManager;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        File ficheroJsonAlumnos = new File("alumnos.json");
        File ficheroSalidaAlumnos = new File("nuevosAlumnos.json");

        Instituto insti = JsonManager.parsearJsonInstituto(ficheroJsonAlumnos);
        if (insti != null) {
            LOG.debug(insti.toString());
            if (insti.getAlumnos() != null) {
                for (Alumno al : insti.getAlumnos()) {
                    LOG.debug(al.toString());
                }
            }

        }
        List<Alumno> alumnosJson = JsonManager.parsearJsonAlumnos(ficheroJsonAlumnos);

        if (alumnosJson.isEmpty()) {
            for (Alumno alu : alumnosJson) {
                LOG.info(alu.toString());
            }
            // a partir de esta lista generaremos un nuevo Json con distinta estructura
            JsonManager.crearJsonAlumnos2(alumnosJson, ficheroSalidaAlumnos);

            alumnosJson = new ArrayList<Alumno>();
            alumnosJson = JsonManager.parsearJsonAlumnos2(ficheroSalidaAlumnos);
            LOG.debug("Objetos Alumno serializados por Jackson");
            for (Alumno al : alumnosJson) {
                LOG.debug(al.toString());
            }
        } else {
            LOG.warn("No se han recuperado alumnos del fichero json: " + ficheroJsonAlumnos.getAbsolutePath());
        }
    }
}
