package main;

import conexion.ConexionBD;
import java.sql.Connection;
import java.sql.SQLException;

// Clase para probar la conexion antes de hacer la interfaz
public class Main {

    public static void main(String[] args) {
        try (Connection con = ConexionBD.obtenerConexion()) { // se cierra sola al terminar
            if (con.isValid(2)) {
                System.out.println("Conexion exitosa a la base de datos sistema_parqueo.");
            } else {
                System.out.println("La conexion se abrio pero no es valida.");
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos:");
            e.printStackTrace(); //muestra el error
        }
    }
}