package es.ciudadescolar.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.Instituto.Alumno;
import es.ciudadescolar.SQL.Query;

public class DBManager {
    private static final Logger log = LoggerFactory.getLogger(DBManager.class);
    private Connection connection;

    private static final String DRIVER = "", URL = "url", USER = "user", PASSWORD = "password";

    public DBManager() {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("conexionBD.properties"));
            Class.forName(p.getProperty("driver"));
            // connection =
            // DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425",
            // "dam2", "dam2");

            /*
             * Pasandole el properties es mas delicado, si el usuario no se llama user o la
             * contraseña password dara error
             */
            connection = DriverManager.getConnection(p.getProperty("url"), p);
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
        if (connection != null) {
            try {
                connection.close();
                log.debug("Conexion cerrada satisfactoriamente");
                return true;
            } catch (SQLException e) {
                log.error("Error cerrando la conexion: " + e.getMessage());
                return false;
            }
        }
        return false;
    }

    public List<Alumno> getAlumnos() {
        List<Alumno> alumnos = null;
        Statement stAlumnos = null;
        ResultSet rsAlumnos = null;

        if (connection != null) {
            try {
                stAlumnos = connection.createStatement();
                rsAlumnos = stAlumnos.executeQuery(Query.getAlumnos);

                if (rsAlumnos.next()) {
                    alumnos = new ArrayList<>();
                    do {
                        Alumno a = new Alumno();
                        a.setExpediente(rsAlumnos.getInt(1));
                        a.setNombre(rsAlumnos.getString(2));
                        if (rsAlumnos.getString(3) != null) {
                            a.setFecha_nac(LocalDate.parse(rsAlumnos.getString(3)));
                        }
                        alumnos.add(a);
                    } while (rsAlumnos.next());
                }
            } catch (SQLException e) {
                log.error("Error recuperando los alumnos: " + e.getMessage());
            } finally {
                try {
                    if (rsAlumnos != null) {
                        rsAlumnos.close();
                    }
                    if (stAlumnos != null) {
                        stAlumnos.close();
                    }
                } catch (SQLException e) {
                    log.error("Error cerrando el statement o el resultset: " + e.getMessage());
                }

            }
        }
        return alumnos;
    }
}
