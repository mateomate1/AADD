package es.ciudadescolar.SQL;

public class QUERYS {
    // Selects
    // _______________________________________________________________________________________________

    // Inserts
    // _______________________________________________________________________________________________
    private static final String INSERT_FILM = "INSERT INTO film(film_id, title, language_id, rental_duration, rental_rate, replacement_cost, last_update) VALUES(?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_ACTOR = "INSERT INTO actor(actor_id, first_name, last_name, last_update) VALUES(?, ?, ?, ?)";
    private static final String INSERT_FILM_ACTOR = "INSERT INTO film_actor(actor_id, film_id, last_update) VALUES(?, ?, ?)";
    // Deletes
    // _______________________________________________________________________________________________
}
