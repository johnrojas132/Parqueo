package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//conecta el programa con la base de datos
public class ConexionBD {

    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String BASE_DATOS = "sistema_parqueo"; 
    private static final String USUARIO = "root";
    private static final String CONTRASENA = "0004";

    //URL con los datos de arriba
    private static final String URL = "jdbc:mysql://" + SERVIDOR + ":" + PUERTO + "/" + BASE_DATOS
            + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    // constructor privado
    private ConexionBD() {
    }

    public static Connection obtenerConexion() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); 
        } catch (ClassNotFoundException e) {
            throw new SQLException("No se encontro el driver", e);
        }
        return DriverManager.getConnection(URL, USUARIO, CONTRASENA); 
    }
}