package es.studium.Wordle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class WordleBD {
    String driver = "com.mysql.cj.jdbc.Driver"; 
    String url = "jdbc:mysql://localhost:3306/Wordle"; 
    String login = "root"; 
    String password = "Studium2023;"; 

    Connection connection = null; 
    Statement statement = null; 
    ResultSet resultSet = null;

    public WordleBD() {
        connection = this.conexion();
    }

    public Connection conexion() {
        try {
            Class.forName(driver);
            Connection connection = DriverManager.getConnection(url, login, password);
            System.out.println("Conexión establecida correctamente."); 
            return connection;
        } catch (Exception e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage()); 
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet ejecutarConsulta(String consulta) {
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(consulta);
        } catch (Exception e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    public void insertarPuntuacion(String nombre, int puntuacion) {
        String insert = "INSERT INTO puntuacion (nombreJugadorPuntuacion, puntuacionJugador) VALUES ('" + nombre + "', " + puntuacion + ")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(insert);
            System.out.println("Puntuación insertada correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar la puntuación: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
