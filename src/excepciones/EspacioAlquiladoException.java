/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Yumor
 */
public class EspacioAlquiladoException extends Exception{

    public EspacioAlquiladoException() {
        super("Espacio no disponible, ya esta alquilado");
    }
    
}
