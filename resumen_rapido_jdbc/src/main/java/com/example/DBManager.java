package com.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBManager {
    private static final String ficheroProperties = "conexion.properties";
    private Connection con = null;
    public DBManager() {
        Properties prop = new Properties();
        try {
            prop.load(new FileInputStream(ficheroProperties));
            String DRIVER = prop.getProperty("driver");
            String URL = prop.getProperty("url");
            String USER =  prop.getProperty("user");
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

    /*
        Si el metodo es generico usamos Statement, solo se puede hacer generico para selects
    */
    public void getAll(){
        ResultSet rs = null;
        Statement s = null;
        try {
            s = con.createStatement();
            rs = s.executeQuery("Select...");

        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void get(){
        ResultSet rs = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("Select...");
            ps.setInt(1, 00);
            rs = ps.executeQuery();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void add(Object o){
        Integer filasCambiadas = null;
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement("INSERT...");
            ps.setString(1, "data 1");
            filasCambiadas = ps.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
