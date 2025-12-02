package com.cheatsheet.RA2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cheatsheet.Example.Alumno;
import com.cheatsheet.Example.Objeto;

public class JDBCManager {
    private static final Logger log = LoggerFactory.getLogger(JDBCManager.class);

    // IMPORTANT: Tiene que ser algun tipo de IMPUTSTREAM para el properties
    private FileInputStream ficheroProperties = null;
    private final String propertiesPath = "conexion.properties";

    // Nombre que recibe el driver en el fichero .properties
    private static final String DRIVER = "driver";
    // Nombre que recibe la url en el fichero .properties
    private static final String URL = "url";
    // Nombre que recibe el en el fichero .properties
    private static final String USUARIO = "user";
    // Nombre que recibe la contraseña en el fichero .properties
    private static final String PWD = "password";

    // Objeto que guarda la conexion a la base de datos
    private Connection conexion = null;

    public JDBCManager() {
        Properties p = new Properties();
        String driver, url, user, password;
        try {
            ficheroProperties = new FileInputStream(propertiesPath);
            p.load(ficheroProperties);

            // El comando getProperty(String) devuelve null si no encuentra la propiedad
            driver = p.getProperty(DRIVER);
            url = p.getProperty(URL);
            // Este metodo se puede usar si se quiere hacer un acceso por defecto si no se
            // encuentra el usuario especificado, simplemente devuelve el valor dam2 si no
            // encuentra la propiedad USUARIO
            user = p.getProperty(USUARIO, "dam2");
            password = p.getProperty(PWD);

            // Registramos el driver:
            Class.forName(driver);

            // Solo funciona si las propiedades en el .properties estan puestas de la manera
            // que espera la clase
            // EVITAR
            conexion = DriverManager.getConnection(url, p);
            conexion = DriverManager.getConnection(url, user, password);
            log.debug("Si no hay errores significa que la conexion se establecio con exito");
        } catch (FileNotFoundException e) {
            log.error("Excepcion disparada por el FileInputStream");
        } catch (ClassNotFoundException e) {
            log.error("Excepcion disparada por la linea Class.forName(p.getProperty(DRIVER))");
        } catch (IOException e) {
            log.error("Excepcion que lanza el comando load de la clase Properties");
        } catch (SQLException e) {
            log.error("Error que lanza el metodo getConnection");
        }
    }

    public boolean cerrarBd() {
        boolean status = false;

        if (conexion != null) {
            try {
                conexion.close();
                log.debug("Cerrada conexión satisfactoriamente");
                status = true;
            } catch (SQLException e) {
                log.error("Error cerrando la conexion: " + e.getMessage());
            }
        }
        return status;
    }

    public List<Alumno> getAllObjetos() {
        List<Alumno> alumnos = null;
        Statement st = null;
        ResultSet rs = null;

        if (conexion != null) {
            try {
                st = conexion.createStatement();
                rs = st.executeQuery(Querys.RECUPERA_ALUMNOS);
                if (rs.next()) {
                    alumnos = new ArrayList<>();
                    do {
                        Alumno temp = new Alumno();
                        /*
                         * logica de recuperar datos del alumno
                         */
                        alumnos.add(temp);
                    } while (rs.next());
                }
            } catch (SQLException e) {

            } finally {
                try {
                    if (rs != null)
                        rs.close();
                    if (st != null)
                        st.close();
                } catch (SQLException e) {
                    log.error("Error durante el cierre de la conexión");
                }
            }
        }
        return alumnos;
    }

    private ResultSet ejecutarQuery(String query) throws SQLException {
        Statement st = conexion.createStatement();
        return st.executeQuery(query);
    }

    /**
     * Metodo mas optimo si quieres devolver la lista como null si no hay
     * coinccidencias
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<Alumno> convertResultSetToList1(ResultSet rs) throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        if (rs.next()) {
            alumnos = new ArrayList<>();
            do {
                Alumno temp = new Alumno();
                /*
                 * logica de recuperar datos del alumno
                 */
                alumnos.add(temp);
            } while (rs.next());
        }
        return alumnos;
    }

    /**
     * Metodo que si no hay alumnos devuelve una lista vacia pero iniciada
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private List<Alumno> convertResultSetToList2(ResultSet rs) throws SQLException {
        List<Alumno> alumnos = new ArrayList<>();
        while (rs.next()) {
            Alumno temp = new Alumno();
            /*
             * logica de recuperar datos del alumno
             */
            alumnos.add(temp);
        }
        return alumnos;
    }

    public List<Alumno> getAll() {
        List<Alumno> salida = null;
        if (conexion != null) {
            try {
                ResultSet rs = ejecutarQuery(Querys.ALTA_NUEVO_ALUMNO);
                salida = convertResultSetToList2(rs);
            } catch (Exception e) {
                salida = null;
            } finally {
                // IMPORTANT: cerrar ResultSet y Statement para no gastar recursos
            }
        }
        return salida;
    }

    /**
     *
     * @param parametros Mapa que utiliza la posicion como clave y el valor como
     *                   objeto
     * @return
     */
    public List<Objeto> getAlumnosWhithParameters() {
        List<Objeto> alumnos = new ArrayList<>();

        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement(Querys.RECUPERAR_PARAMETRIZADO);
            // Estructura de los tipos de set en prepared statement:
            // (dato1 [siempre es la posicion, empezando desde el 1],
            // dato2 [tipo de objeto a introducir])

            // Tipos:
            // - Basicos:
            ps.setString(1, "texto");
            ps.setInt(1, 1);
            ps.setLong(1, 1L);
            ps.setDouble(1, 1D);
            ps.setFloat(1, 3F);
            ps.setBoolean(1, true);
            ps.setByte(1, (byte) 5);
            ps.setShort(1, (short) 7);

            // -Tipos SQL(Tienen que ser de java.sql):
            ps.setDate(1, Date.valueOf("YYYY-MM-DD"));
            ps.setTime(1, Time.valueOf("HH:MM:SS"));
            ps.setTimestamp(1, java.sql.Timestamp.valueOf("YYYY-MM-DD HH:MM:SS"));

            // -Otros tipos:
            ps.setObject(1, new Object());
            ps.setNull(1, java.sql.Types.INTEGER);

            rs = ps.executeQuery();

            while (rs.next()) {
                Objeto temp = new Objeto();
                temp.setDato1((String) rs.getObject("nombre de la columna en la bdd"));
                // Recibe la posicion de la columna en el SELECT hecho, empezando desde el 1
                temp.setDato2((String) rs.getObject(1));

                // Tipos principales
                rs.getInt(1);
                rs.getString(1);
                rs.getDouble(1);
                rs.getDate(1);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return alumnos;
    }

    /**
     * Metodo por partes para recuperar objetos de una base de datos parametrizando
     * el select
     *
     * @return
     */
    public List<Objeto> getWhithParameters() {
        PreparedStatement ps = crearPreparedStatement();
        asignarParametros(ps);
        ResultSet rs = ejecutarConsulta(ps);
        List<Objeto> lista = mapearResultados(rs);
        return lista;
    }

    private PreparedStatement crearPreparedStatement() {
        try {
            return conexion.prepareStatement(Querys.RECUPERAR_PARAMETRIZADO);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void asignarParametros(PreparedStatement ps) {
        try {
            ps.setString(1, "texto");
            ps.setInt(2, 1);
            ps.setLong(3, 1L);
            ps.setDouble(4, 1D);
            ps.setFloat(5, 3F);
            ps.setBoolean(6, true);
            ps.setByte(7, (byte) 5);
            ps.setShort(8, (short) 7);

            ps.setDate(9, java.sql.Date.valueOf("2000-01-01"));
            ps.setTime(10, java.sql.Time.valueOf("10:00:00"));
            ps.setTimestamp(11, java.sql.Timestamp.valueOf("2000-01-01 10:00:00"));

            ps.setObject(12, new Object());
            ps.setNull(13, java.sql.Types.INTEGER);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ResultSet ejecutarConsulta(PreparedStatement ps) {
        try {
            return ps.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Objeto> mapearResultados(ResultSet rs) {
        List<Objeto> lista = new ArrayList<>();
        try {
            while (rs.next()) {
                Objeto temp = new Objeto();
                temp.setDato1((String) rs.getObject("columna1"));
                temp.setDato2((String) rs.getObject(2));
                lista.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

}
