package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.SQL.QUERYS;
import es.ciudadescolar.models.Actor;
import es.ciudadescolar.models.Pelicula;

public class DBManager {
    private static final Logger log = LoggerFactory.getLogger(DBManager.class);
    private Connection c;

    public DBManager(String name) {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream(name));
            Class.forName(p.getProperty("driver"));

            /*
             * Pasandole el properties es mas delicado, si el usuario no se llama user o la
             * contraseña password dara error
             */
            c = DriverManager.getConnection(p.getProperty("url"), p);
            log.debug("Conexion establecida con exito");
        } catch (ClassNotFoundException e) {
            log.error("Registro de driver erroneo: " + e.getMessage());
        } catch (SQLException e) {
            log.error("Error estableciendo la conexion con la BD: " + e.getMessage());
        } catch (FileNotFoundException e) {
            log.error("No se encontro el fichero properties: " + e.getMessage());
        } catch (IOException e) {
            log.error("msg");
        }
    }

    public boolean cerrarDB() {
        if (c != null) {
            try {
                c.close();
                log.debug("Conexion cerrada satisfactoriamente");
                return true;
            } catch (SQLException e) {
                log.error("Error cerrando la conexion: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    private boolean startTransaction() {
        try {
            c.setAutoCommit(false);
            return true;
        } catch (SQLException e) {
            log.error("Error empezando la transaccion" + e.getMessage());
            return false;
        }
    }

    private boolean endTransaction() {
        try {
            c.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            log.error("Error terminando la transaccion" + e.getMessage());
            return false;
        }
    }

    private boolean commit() {
        try {
            c.commit();
            return true;
        } catch (SQLException e) {
            log.error("Error haciendo commit" + e.getMessage());
            return false;
        }
    }

    private boolean rollback() {
        try {
            c.rollback();
            return true;
        } catch (SQLException e) {
            log.error("Error haciendo rollback" + e.getMessage());
            return false;
        }
    }

    public boolean insertPeliculas(List<Pelicula> pelicula) {
        boolean status = false;

        try {
            c.setAutoCommit(false);
            // 1. Buscar Actor
            // 2.a Si no esta asignado el actor, insertar actor
            // 2.b Si esta insertado el actor siguiente paso
            // 3. Insertar pelicula
            // 4. Insertar peliculas y actores en la tabla film_actor

            // film_id, title, language_id, rental_duration, rental_rate, replacement_cost,
            // last_update
            c.setAutoCommit(true);
        } catch (SQLException e) {

            log.error("Error guardando las peliculas o los actores");
        }
        return status;
    }

    public boolean insertPelicula(Pelicula pelicula) {
        boolean status = false;

        PreparedStatement psAltaPelicula = null;
        if (c != null) {
            try {
                psAltaPelicula = c.prepareStatement(QUERYS.INSERT_FILM);
                if(pelicula.getFilm_id() == null)
                    psAltaPelicula.setNull(1, java.sql.Types.INTEGER);
                else
                    psAltaPelicula.setInt(1, pelicula.getFilm_id());
                psAltaPelicula.setString(2, pelicula.getTitle());
                psAltaPelicula.setInt(3, pelicula.getLanguage_id());
                psAltaPelicula.setInt(4, pelicula.getRental_duration());
                psAltaPelicula.setDouble(5, pelicula.getRental_rate());
                psAltaPelicula.setDouble(6, pelicula.getReplacement_cost());
                for (Actor a : pelicula.getActores()) {
                    if(a.equals(getActor(a))){

                    }
                }
            } catch (SQLException e) {

                log.error("Error guardando las peliculas o los actores");
            }
        }
        return status;
    }

    public boolean insertarActores(List<Actor> actor) {
        boolean status = false;
        PreparedStatement psAltaActor = null;
        if (c != null) {

        }
        return status;
    }

    public boolean insertarActor(Actor actor) {
        boolean status = false;
        PreparedStatement psAltaActor = null;
        if (c != null) {
            try {
                psAltaActor = c.prepareStatement(QUERYS.INSERT_ACTOR);
                // psAltaActor = c.prepareStatement(QUERYS.INSERT_ACTOR(actor));
                // actor_id, first_name, last_name
                if (actor.getId() == null)
                    psAltaActor.setNull(1, java.sql.Types.INTEGER);
                else
                    psAltaActor.setInt(1, actor.getId());
                psAltaActor.setString(2, actor.getFirst_name());
                psAltaActor.setString(3, actor.getLast_name());
                if (psAltaActor.executeUpdate() == 1) {
                    log.debug("Actor insertado con exito");
                    status = true;
                }
            } catch (SQLException e) {
                log.error("Error insertando actor[" + actor + "]");
            }
        }
        return status;
    }

    public Actor getActor(Actor a){
        
        return null;
    }

    public boolean existsActor(Actor a){
        boolean status = false;
        
        return status;
    }

}
