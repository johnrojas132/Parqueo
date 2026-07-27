package Datos;

import modelo.Vehiculo;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author efer0
 */

public class PruebaVehiculoDAO {
    public static void main(String[] args) {
    // Crea el objeto para acceder a la base de datos
    VehiculoDAO dao = new VehiculoDAO();

    // Placa que se usará en las pruebas
    String placaPrueba = "PRB1234";

    try {
        // Prueba de inserción
        System.out.println("===== 1) AGREGAR =====");

        // Crea un vehículo de prueba
        Vehiculo nuevo = new Vehiculo(placaPrueba, "Fer - Prueba", "88887777", 99, "Carro");

        // Guarda el vehículo
        dao.agregar(nuevo);

        // Muestra el ID generado
        System.out.println("Vehiculo insertado con id generado: " + nuevo.getId());

        // Consulta sugerida en SQL Server
        System.out.println("-> Revise en SSMS: SELECT * FROM Vehiculo WHERE placa = '" + placaPrueba + "'");

        // Prueba de listado
        System.out.println("\n===== 2) LISTAR =====");

        // Obtiene todos los vehículos
        List<Vehiculo> todos = dao.listar();

        // Muestra la cantidad
        System.out.println("Total de vehiculos en la tabla: " + todos.size());

        // Recorre la lista
        for (Vehiculo v : todos) {
            imprimir(v);
        }

        // Prueba de búsqueda
        System.out.println("\n===== 3) BUSCAR POR PLACA =====");

        // Busca el vehículo
        Vehiculo encontrado = dao.buscarPorId(placaPrueba);

        // Verifica si existe
        if (encontrado == null) {
            System.out.println("ERROR: no se encontro el vehiculo de prueba. Deteniendo pruebas.");
            return;
        }

        // Muestra el vehículo encontrado
        System.out.println("Vehiculo encontrado:");
        imprimir(encontrado);

        // Prueba de actualización
        System.out.println("\n===== 4) ACTUALIZAR =====");

        // Cambia los datos
        encontrado.setPropietario("Integrante 2 - Prueba (editado)");
        encontrado.setTelefono("88880000");
        encontrado.setNumeroEspacio(100);
        encontrado.setTipoVehiculo("Moto");

        // Guarda los cambios
        dao.actualizar(encontrado);

        // Verifica la actualización
        System.out.println("Vehiculo actualizado. Verificando en base de datos...");
        imprimir(dao.buscarPorId(placaPrueba));

        // Consulta sugerida en SQL Server
        System.out.println("-> Revise en SSMS que propietario, telefono, numeroEspacio y tipoVehiculo cambiaron.");

        // Prueba de eliminación
        System.out.println("\n===== 5) ELIMINAR =====");

        // Elimina el vehículo
        dao.eliminar(placaPrueba);

        // Verifica que ya no exista
        Vehiculo verificacion = dao.buscarPorId(placaPrueba);

        // Muestra el resultado
        System.out.println(verificacion == null
                ? "Vehiculo eliminado correctamente (ya no aparece en la busqueda)."
                : "ERROR: el vehiculo todavia existe.");

        // Consulta sugerida en SQL Server
        System.out.println("-> Revise en SSMS que el registro con placa " + placaPrueba + " ya no existe.");

        // Fin de las pruebas
        System.out.println("\n===== PRUEBAS FINALIZADAS SIN ERRORES =====");

    } catch (SQLException e) {
        // Muestra el error de la base de datos
        System.out.println("Ocurrio un error de base de datos durante las pruebas:");
        e.printStackTrace();
    }
}

// Imprime los datos del vehículo
private static void imprimir(Vehiculo v) {
    // Verifica si es nulo
    if (v == null) {
        System.out.println("  (null)");
        return;
    }

    // Muestra la información
    System.out.printf("  id=%d | placa=%s | propietario=%s | telefono=%s | espacio=%d | tipo=%s | entrada=%s | estado=%s%n",
            v.getId(), v.getPlaca(), v.getPropietario(), v.getTelefono(),
            v.getNumeroEspacio(), v.getTipoVehiculo(), v.getHoraEntrada(), v.getEstado());
    }
}
