package com.tarea4.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PATRÓN DE DISEÑO: SINGLETON
 * Garantiza una única instancia de conexión a la base de datos
 * durante toda la ejecución de la aplicación.
 *
 * Ajusta URL, USUARIO y PASSWORD según tu configuración de MySQL.
 */
public class ConexionDB {

    private static ConexionDB instancia;
    private Connection conexion;

    private static final String URL = "jdbc:mysql://localhost:3306/tarea4_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // Constructor privado: nadie fuera de esta clase puede instanciarla
    private ConexionDB() {
        conectar();
    }

    private void conectar() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Error al conectar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static ConexionDB getInstancia() {
        if (instancia == null) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConexion() {
        try {
            if (conexion == null || conexion.isClosed()) {
                conectar();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conexion;
    }
}
