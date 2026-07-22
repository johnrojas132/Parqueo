/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Chess
 */
public class NegocioException extends Exception {
    //Marca cuando el error nace de aqui mismo
      public NegocioException(String mensaje) {
        super(mensaje);
    }
      //Se usa para marcar errores y mostrar mensajes mas claros
    public NegocioException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
    
}
