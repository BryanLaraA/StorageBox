/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author Yumor
 */
public class EspacioDuplicadoException extends Exception{

    public EspacioDuplicadoException() {
        super("Ya existe un espacio con este numero ");
    }
    
}
