/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package excepciones;

/**
 *
 * @author bryan
 */
public class ClienteConContratoExeption extends Exception {
    public ClienteConContratoExeption(String identificacion) {
        super("No se puede eliminar el cliente " + identificacion+ ": tiene contratos Pendientes o Activos asociados.");
    }
}
