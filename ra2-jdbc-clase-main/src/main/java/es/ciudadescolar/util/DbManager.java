package es.ciudadescolar.util;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import java.io.FileInputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.ciudadescolar.instituto.Alumno;

public class DbManager {
    private static final Logger log = LoggerFactory.getLogger(DbManager.class);
    private static final String DRIVER = "driver";
    private static final String URL = "url";
    private static final String USUARIO = "user";
    private static final String PWD = "password";

    private Connection con = null;

    public DbManager() {
        Properties prop = new Properties();

        try {
            prop.load(new FileInputStream("conexionBD.properties"));

            // registramos driver (en versiones actuales no es necesario)
            Class.forName(prop.getProperty(DRIVER));
            // con =
            // DriverManager.getConnection("jdbc:mysql://192.168.203.77:3306/dam2_2425",
            // "dam2", "dam2");
            con = DriverManager.getConnection(prop.getProperty(URL), prop.getProperty(USUARIO), prop.getProperty(PWD));
            log.debug("Establecida conexión satisfactoriamente");
        } catch (IOException e) {
            log.error("Imposible cargar propiedades de la conexión");
        } catch (ClassNotFoundException e) {
            log.error("Registro de driver con error: " + e.getMessage());
        } catch (SQLException e) {
            log.error("Imposible establecer conexion con la BD: " + e.getMessage());
        }
    }

    public List<Alumno> getAllAlumnos() {
        List<Alumno> alumnos = null;

        Statement stAlumnos = null;

        ResultSet rstAlumno = null;

        Alumno alumno = null;

        if (con != null) {
            try {
                stAlumnos = con.createStatement();
                rstAlumno = stAlumnos.executeQuery(SQL.RECUPERA_ALUMNOS);

                if (rstAlumno.next()) // if(rstAlumno.first())
                {
                    alumnos = new ArrayList<Alumno>();
                    do {
                        alumno = new Alumno();
                        alumno.setExpediente(Integer.valueOf(rstAlumno.getInt(1)));
                        alumno.setNombre(rstAlumno.getString(2));

                        Date fecha = rstAlumno.getDate(3);
                        if (fecha != null)
                            alumno.setFecha_nac(fecha.toLocalDate());

                        alumnos.add(alumno);
                    } while (rstAlumno.next());
                }

                log.debug("Se ha ejecutado correctamente la sentencia SELECT");
            } catch (SQLException e) {
                log.error("Imposible consultar alumnos: " + e.getMessage());
            } finally {
                try {
                    if (rstAlumno != null)
                        rstAlumno.close();
                    if (stAlumnos != null)
                        stAlumnos.close();
                } catch (SQLException e) {
                    log.error("Error durante el cierre de la conexión");
                }
            }
        }
        return alumnos;

    }

    public boolean cerrarBd() {
        boolean status = false;

        if (con != null) {
            try {
                con.close();
                log.debug("Cerrada conexión satisfactoriamente");
                status = true;
            } catch (SQLException e) {
                log.error("Error cerrando la conexion: " + e.getMessage());
            }
        }
        return status;
    }

    public Alumno getAlumnoPorExpYNom(int exped, String nombre) {
        Alumno al = null;
        PreparedStatement pstAlumno = null;
        ResultSet rstAlumno = null;
        // al realizar la busqueda por PK, o me devuelve uno o ninguno (nunca varios)
        try {
            pstAlumno = con.prepareStatement(SQL.RECUPERA_ALUMNO_EXP);
            pstAlumno.setInt(1, exped);
            pstAlumno.setString(2, nombre);

            rstAlumno = pstAlumno.executeQuery();

            if (rstAlumno.next()) {
                al = new Alumno(rstAlumno.getInt(1), rstAlumno.getString(2), rstAlumno.getDate(3));
                log.debug("Recuperado alumno exp[" + exped + "] y nombre[" + nombre + "]");
            }

        } catch (SQLException e) {
            log.error("Error durante la consulta parametrizada: " + e.getMessage());
        } finally {
            try {

                if (rstAlumno != null) {
                    rstAlumno.close();
                    log.debug("Se ha cerrado correctamente el resultSet");
                }
                if (pstAlumno != null) {
                    pstAlumno.close();
                    log.debug("Se ha cerrado correctamente el statement");
                }
            } catch (SQLException e) {
                log.error("Error liberando recursos de la consulta parametrizada");
            }
        }

        return al;
    }

    public boolean altaDeAlumno(Alumno al) {
        boolean status = false;
        PreparedStatement psNuevoAlumno = null;

        if (con != null) {
            try {
                psNuevoAlumno = con.prepareStatement(SQL.ALTA_NUEVO_ALUMNO);
                psNuevoAlumno.setInt(1, al.getExpediente());
                psNuevoAlumno.setString(2, al.getNombre());
                psNuevoAlumno.setDate(3, Date.valueOf(al.getFecha_nac()));

                if (psNuevoAlumno.executeUpdate() == 1) {
                    log.debug("Insercion realizada correctamente: Alumno[" + al.toString() + "]");
                    status = true;
                }else{
                    log.warn("Alumno no encontrado");
                }
            } catch (SQLException e) {
                log.error("Error durante el alta del alumno: " + e.getMessage());
            } finally{
                if (psNuevoAlumno != null) {
                    try {
                        psNuevoAlumno.close();
                    } catch (SQLException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return status;
    }
}
