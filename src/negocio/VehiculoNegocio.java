/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;
import Datos.VehiculoDAO;
import excepciones.NegocioException;
import modelo.Vehiculo;

import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;
/**
 *
 * @author Chess
 */
public class VehiculoNegocio {
     private static final Pattern PATRON_PLACA = Pattern.compile("^[A-Za-z0-9-]{5,10}$");
    private static final Pattern PATRON_TELEFONO = Pattern.compile("^[0-9]{8,15}$");

    private final VehiculoDAO vehiculoDAO;

    public VehiculoNegocio() {
        this.vehiculoDAO = new VehiculoDAO();
    }

    private void validar(Vehiculo vehiculo) throws NegocioException {
        if (vehiculo.getPlaca() == null || vehiculo.getPlaca().trim().isEmpty()) {
            throw new NegocioException("La placa es obligatoria.");
        }
        if (!PATRON_PLACA.matcher(vehiculo.getPlaca().trim()).matches()) {
            throw new NegocioException("La placa no es valida. Use solo letras, numeros y guiones (5 a 10 caracteres).");
        }
        if (vehiculo.getPropietario() == null || vehiculo.getPropietario().trim().isEmpty()) {
            throw new NegocioException("El nombre del propietario es obligatorio.");
        }
        if (vehiculo.getTelefono() == null || vehiculo.getTelefono().trim().isEmpty()) {
            throw new NegocioException("El telefono es obligatorio.");
        }
        if (!PATRON_TELEFONO.matcher(vehiculo.getTelefono().trim()).matches()) {
            throw new NegocioException("El telefono debe contener solo numeros (8 a 15 digitos).");
        }
        if (vehiculo.getNumeroEspacio() <= 0) {
            throw new NegocioException("El numero de espacio debe ser mayor a cero.");
        }
        if (vehiculo.getTipoVehiculo() == null || vehiculo.getTipoVehiculo().trim().isEmpty()) {
            throw new NegocioException("Debe seleccionar el tipo de vehiculo.");
        }
    }

    public void agregar(Vehiculo vehiculo) throws NegocioException {
        validar(vehiculo);
        try {
            String placa = vehiculo.getPlaca().trim().toUpperCase();
            if (vehiculoDAO.buscarPorId(placa) != null) {
                throw new NegocioException("Ya existe un vehiculo registrado con la placa " + placa);
            }
            vehiculo.setPlaca(placa);
            vehiculo.setEstado("Activo");
            vehiculoDAO.agregar(vehiculo);
        } catch (SQLException e) {
            throw new NegocioException("Error al registrar el vehiculo en la base de datos.", e);
        }
    }

    public Vehiculo buscar(String placa) throws NegocioException {
        if (placa == null || placa.trim().isEmpty()) {
            throw new NegocioException("Debe indicar una placa para buscar.");
        }
        try {
            Vehiculo vehiculo = vehiculoDAO.buscarPorId(placa.trim().toUpperCase());
            if (vehiculo == null) {
                throw new NegocioException("No se encontro ningun vehiculo con la placa " + placa);
            }
            return vehiculo;
        } catch (SQLException e) {
            throw new NegocioException("Error al buscar el vehiculo en la base de datos.", e);
        }
    }

    public void actualizar(Vehiculo vehiculo) throws NegocioException {
        validar(vehiculo);
        try {
            String placa = vehiculo.getPlaca().trim().toUpperCase();
            if (vehiculoDAO.buscarPorId(placa) == null) {
                throw new NegocioException("No existe un vehiculo con la placa " + placa);
            }
            vehiculo.setPlaca(placa);
            vehiculoDAO.actualizar(vehiculo);
        } catch (SQLException e) {
            throw new NegocioException("Error al actualizar el vehiculo en la base de datos.", e);
        }
    }

    public void eliminar(String placa) throws NegocioException {
        if (placa == null || placa.trim().isEmpty()) {
            throw new NegocioException("Debe indicar una placa para eliminar.");
        }
        try {
            String placaNormalizada = placa.trim().toUpperCase();
            if (vehiculoDAO.buscarPorId(placaNormalizada) == null) {
                throw new NegocioException("No existe un vehiculo con la placa " + placaNormalizada);
            }
            vehiculoDAO.eliminar(placaNormalizada);
        } catch (SQLException e) {
            throw new NegocioException("Error al eliminar el vehiculo en la base de datos.", e);
        }
    }

    public List<Vehiculo> listar() throws NegocioException {
        try {
            return vehiculoDAO.listar();
        } catch (SQLException e) {
            throw new NegocioException("Error al listar los vehiculos desde la base de datos.", e);
        }
    }
    
}
