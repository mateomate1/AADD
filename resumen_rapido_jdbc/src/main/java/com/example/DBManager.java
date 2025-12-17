package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
    private static final String ficheroProperties = "conexion.properties";

    // Objetos de jdbc:

    /*
     * 1. Connection: se usa para realizar la conexion a la bdd, utiliza la URL,
     * usuario y contraseña
     */
    private Connection con = null;

    /*
     * 2. Statement: Se utiliza para hacer sentencias de sql simples, normalmente
     * selects
     *
     * Ejemplo simple de uso:
     * Statement st = conn.createStatement();
     * ResultSet rs = st.executeQuery("SELECT * FROM alumno");
     */
    Statement s = null;

    /*
     * 3. PreparedStatement: Se utiliza para sentencias simples pero tipadas.
     *
     * Ej Select de un actor con un nombre especifico, gracias a PreparedStatement
     * se puede hacer sin construir un String con la sentencuia a cada vez, sino que
     * el propio PreparedStatement cambiara los simbolos ? con la funcion
     * setDatatype(pos, DATA) siendo pos un numero del 1 a la cantidad de ? en el
     * String
     *
     * Ejemplo simple de uso:
     * PreparedStatement ps =
     * conn.prepareStatement("SELECT * FROM alumno WHERE id = ?");
     * ps.setInt(1, 10);
     * ResultSet rs = ps.executeQuery();
     *
     * En caso de ser un INSERT, UPDATE O DELETE usar
     * int FilasAfectadas = ps.executeUpdate();
     */
    PreparedStatement ps = null;

    /*
     * 4. CallableStatement: Usado para llamar a funciones o procedimientos, recibe
     * un string con la funcion o procedure entre {}
     *
     * Ejemplo de uso:
     * CallableStatement cs = conn.prepareCall("{CALL sp_getalumno(?)}");
     * cs.setInt(1, 10);
     * ResultSet rs = cs.executeQuery();
     *
     * En caso de tener parametros OUT o INOUT se usa:
     * cs.registerOutParameter(POS, Types.INTEGER);
     * En caso de ser inout el parametro x abra que hacer un setData(x,"") y
     * registerOutParameter(x, Types)
     */
    CallableStatement cs = null;

    /*
     * 5. ResultSet: Se utiliza para almacenar el resultado de una consulta sql
     *
     * Para iterar sobre las filas se usa:
     * rs.next() que devuelve false cuando no existe siguiente fila
     *
     * Para iterar sobre las columnas de cada fila se usa:
     * rs.getDatatype(posColumna) Mas rapido
     * rs.getDatatype(nombreColumna) Mas generico
     */
    ResultSet rs = null;

    public DBManager() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(ficheroProperties));
            String DRIVER = prop.getProperty("driver");
            String URL = prop.getProperty("url");
            String USER = prop.getProperty("user");
            String PWD = prop.getProperty("password");
            Class.forName(DRIVER);
            con = DriverManager.getConnection(URL, USER, PWD);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void ex() {
        ps.setInt(1, 10);
        ps.executeUpdate();
    }
}
