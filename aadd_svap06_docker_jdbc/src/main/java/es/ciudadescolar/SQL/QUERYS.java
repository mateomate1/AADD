package es.ciudadescolar.SQL;

import es.ciudadescolar.models.Actor;

import java.util.ArrayList;
import java.util.List;

import es.ciudadescolar.global.ConstantesSQL;

public class QUERYS {
    // Selects
    // _______________________________________________________________________________________________
    public static final String SELECT_ACTOR_STRICT = "SELECT actor_id, first_name, last_name FROM actor WHERE first_name = '?' AND last_name = '?'";
    public static final String SELECT_ACTOR = "SELECT actor_id, first_name, last_name FROM actor WHERE first_name LIKE '?' AND last_name LIKE '?'";

    // Inserts
    // _______________________________________________________________________________________________
    public static final String INSERT_FILM = "INSERT INTO film(film_id, title, language_id, rental_duration, rental_rate, replacement_cost) VALUES(?, ?, ?, ?, ?, ?)";
    public static final String INSERT_ACTOR = "INSERT INTO actor(actor_id, first_name, last_name) VALUES(?, ?, ?)";
    public static final String INSERT_FILM_ACTOR = "INSERT INTO film_actor(actor_id, film_id) VALUES(?, ?)";
    // Deletes
    // _______________________________________________________________________________________________

    public static String SELECT_ACTOR_AS(Actor a) {
        String base = "SELECT actor_id, first_name, last_name, last_update FROM actor";
        String where = "";

        List<String> condiciones = new ArrayList<>();

        if (a.getId() != null)
            condiciones.add(ConstantesSQL.ACTOR_ID + " = " + a.getId());

        if (a.getFirst_name() != null)
            condiciones.add(ConstantesSQL.A_FIRST_NAME + " LIKE '" + a.getFirst_name() + "'");

        if (a.getLast_name() != null)
            condiciones.add(ConstantesSQL.A_LAST_NAME + " LIKE '" + a.getLast_name() + "'");

        if (a.getLast_update() != null)
            condiciones.add(ConstantesSQL.A_LAST_UPDATE + " LIKE '" + a.getLast_name() + "'");

        if (!condiciones.isEmpty())
            where = " WHERE " + String.join(" AND ", condiciones);

        return base + where;
    }

}
