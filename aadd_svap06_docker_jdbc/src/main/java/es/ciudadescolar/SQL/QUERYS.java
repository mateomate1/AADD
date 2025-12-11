package es.ciudadescolar.SQL;

import es.ciudadescolar.models.Actor;
import es.ciudadescolar.models.Pelicula;

public class QUERYS {
    // Selects
    // _______________________________________________________________________________________________

    // Inserts
    // _______________________________________________________________________________________________
    public static final String INSERT_FILM = "INSERT INTO film(film_id, title, language_id, rental_duration, rental_rate, replacement_cost, last_update) VALUES(?, ?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ACTOR = "INSERT INTO actor(actor_id, first_name, last_name) VALUES(?, ?, ?)";
    public static final String INSERT_FILM_ACTOR = "INSERT INTO film_actor(actor_id, film_id, last_update) VALUES(?, ?, ?)";
    // Deletes
    // _______________________________________________________________________________________________

    public static String INSERT_ACTOR(Actor a) {
        String salida = "";

        String INSERT = "INSERT INTO actor(";
        String VALUES = ") VALUES(";
        if (a.getId() != null) {
            INSERT += "film_id, ";
            VALUES += a.getId() + ", ";
        }
        INSERT += "first_name, last_name";
        VALUES += a.getFirst_name() + ", " + a.getLast_name() + ")";
        salida = INSERT + VALUES;
        return salida;
    }

    public static String INSERT_FILM(Pelicula p){
        return "";
    }
}
