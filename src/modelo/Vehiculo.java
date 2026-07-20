package modelo;

import java.sql.Timestamp;

// guarda los datos de un vehiculo
public class Vehiculo {

    private int id;
    private String placa;
    private String propietario;
    private String tipoVehiculo;
    private int numeroEspacio;
    private Timestamp horaEntrada;
    private String estado;

    // constructor vacio
    public Vehiculo() {
    }

    //constructor con todos los datos
    public Vehiculo(int id, String placa, String propietario, String tipoVehiculo,
                     int numeroEspacio, Timestamp horaEntrada, String estado) {
        this.id = id;
        this.placa = placa;
        this.propietario = propietario;
        this.tipoVehiculo = tipoVehiculo;
        this.numeroEspacio = numeroEspacio;
        this.horaEntrada = horaEntrada;
        this.estado = estado;
    }

    //getters y setters, para leer y modificar cada dato

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getPropietario() {
        return propietario;
    }

    public void setPropietario(String propietario) {
        this.propietario = propietario;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    public int getNumeroEspacio() {
        return numeroEspacio;
    }

    public void setNumeroEspacio(int numeroEspacio) {
        this.numeroEspacio = numeroEspacio;
    }

    public Timestamp getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Timestamp horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}