package com.developer.jauregui.authentication.properties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GetConexion {

    private static final String CURRENT_CONEXDB_KEY = ConexionBD.class.getCanonicalName();
    private static ConexionBD conexion;

    public GetConexion(){
    }

    public static Connection getConexionDB() throws SQLException {
        return DriverManager.getConnection(conexion.get("JDBC_URL").toString(),conexion.get("JDBC_USER").toString(),
                conexion.get("JDBC_PASS").toString());
    }
    public static void setConexionDB(ConexionBD con){
        conexion = con;
    }
}
