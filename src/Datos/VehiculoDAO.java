package Datos;

import conexion.ConexionBD;
import modelo.Vehiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author efer0
 */

public class VehiculoDAO {
    // Agrega un vehículo a la base de datos
public void agregar(Vehiculo vehiculo) throws SQLException {
    // Consulta de inserción
    String sql = "INSERT INTO Parqueo (placa, propietario, telefono, tipoVehiculo, numeroEspacio, estado) "
            + "VALUES (?, ?, ?, ?, ?, ?)";

    // Abre la conexión y prepara la consulta
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

        // Asigna los valores
        ps.setString(1, vehiculo.getPlaca());
        ps.setString(2, vehiculo.getPropietario());
        ps.setString(3, vehiculo.getTelefono());
        ps.setString(4, vehiculo.getTipoVehiculo());
        ps.setInt(5, vehiculo.getNumeroEspacio());
        ps.setString(6, vehiculo.getEstado() == null ? "Activo" : vehiculo.getEstado());

        // Ejecuta la inserción
        ps.executeUpdate();

        // Obtiene el ID generado
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                vehiculo.setId(rs.getInt(1));
            }
        }
    }
}

// Lista todos los vehículos
public List<Vehiculo> listar() throws SQLException {
    // Consulta de listado
    String sql = "SELECT id, placa, propietario, telefono, tipoVehiculo, numeroEspacio, horaEntrada, estado "
            + "FROM Parqueo ORDER BY id DESC";

    // Crea la lista
    List<Vehiculo> lista = new ArrayList<>();

    // Abre la conexión
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        // Recorre los resultados
        while (rs.next()) {
            lista.add(mapearVehiculo(rs));
        }
    }

    // Devuelve la lista
    return lista;
}

// Busca un vehículo por placa
public Vehiculo buscarPorId(String placa) throws SQLException {
    // Consulta de búsqueda
    String sql = "SELECT id, placa, propietario, telefono, tipoVehiculo, numeroEspacio, horaEntrada, estado "
            + "FROM Parqueo WHERE placa = ?";

    // Abre la conexión
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Asigna la placa
        ps.setString(1, placa);

        // Ejecuta la consulta
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapearVehiculo(rs);
            }
        }
    }

    // No encontrado
    return null;
}

// Busca un vehículo por ID
public Vehiculo buscarPorId(int id) throws SQLException {
    // Consulta de búsqueda
    String sql = "SELECT id, placa, propietario, telefono, tipoVehiculo, numeroEspacio, horaEntrada, estado "
            + "FROM Parqueo WHERE id = ?";

    // Abre la conexión
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Asigna el ID
        ps.setInt(1, id);

        // Ejecuta la consulta
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return mapearVehiculo(rs);
            }
        }
    }

    // No encontrado
    return null;
}

// Actualiza un vehículo
public void actualizar(Vehiculo vehiculo) throws SQLException {
    // Consulta de actualización
    String sql = "UPDATE Parqueo SET propietario = ?, telefono = ?, numeroEspacio = ?, tipoVehiculo = ? "
            + "WHERE placa = ?";

    // Abre la conexión
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Asigna los nuevos valores
        ps.setString(1, vehiculo.getPropietario());
        ps.setString(2, vehiculo.getTelefono());
        ps.setInt(3, vehiculo.getNumeroEspacio());
        ps.setString(4, vehiculo.getTipoVehiculo());
        ps.setString(5, vehiculo.getPlaca());

        // Ejecuta la actualización
        ps.executeUpdate();
    }
}

// Elimina un vehículo
public void eliminar(String placa) throws SQLException {
    // Consulta de eliminación
    String sql = "DELETE FROM Parqueo WHERE placa = ?";

    // Abre la conexión
    try (Connection cn = ConexionBD.obtenerConexion();
         PreparedStatement ps = cn.prepareStatement(sql)) {

        // Asigna la placa
        ps.setString(1, placa);

        // Ejecuta la eliminación
        ps.executeUpdate();
    }
}

// Convierte un registro en un objeto Vehiculo
private Vehiculo mapearVehiculo(ResultSet rs) throws SQLException {
    // Crea el objeto
    Vehiculo v = new Vehiculo();

    // Asigna los datos
    v.setId(rs.getInt("id"));
    v.setPlaca(rs.getString("placa"));
    v.setPropietario(rs.getString("propietario"));
    v.setTelefono(rs.getString("telefono"));
    v.setNumeroEspacio(rs.getInt("numeroEspacio"));
    v.setTipoVehiculo(rs.getString("tipoVehiculo"));
    v.setHoraEntrada(rs.getTimestamp("horaEntrada"));
    v.setEstado(rs.getString("estado"));

    // Devuelve el objeto
    return v;
    }
}
