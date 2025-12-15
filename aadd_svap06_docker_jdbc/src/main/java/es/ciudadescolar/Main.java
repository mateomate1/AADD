package es.ciudadescolar;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.models.Actor;
import es.ciudadescolar.models.Pelicula;
import es.ciudadescolar.util.DBManager;
import es.ciudadescolar.util.TXTManager;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String TxtPath = "nuevas_pelis.txt";
    //private static final String PROPERTIES = "BBDD_AWS_ADMINISTRATOR.properties";
    //private static final String PROPERTIES = "BBDD_AWS_ALUMNO.properties";
    private static final String PROPERTIES = "BBDD_local.properties";

    public static void main(String[] args) {
        DBManager db = new DBManager(PROPERTIES);
        TXTManager text = new TXTManager(TxtPath);

        List<Pelicula> peliculas = text.getPeliculas();
        for (Pelicula pelicula : peliculas) {
            log.trace(pelicula.toString());
            for (Actor a : pelicula.getActores()) {
                log.trace(a.toString());
            }
        }
        Actor a = new Actor();
        a.setFirst_name("Mateo");
        a.setLast_name("Ayarra");
        db.insertarActor(a);
        // TODO: programa

        db.cerrarDB();
    }
}