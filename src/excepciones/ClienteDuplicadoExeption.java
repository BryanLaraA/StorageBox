/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author bryan
 */
public class ClienteDuplicadoExeption extends Exception{
    public ClienteDuplicadoExeption(String identificacion) {
        super("Ya existe un cliente registrado con la identificación:"+identificacion);
    }
}
