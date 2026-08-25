/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientes;

import contratos.Contrato;
import contratos.EstadoContrato;
import excepciones.ClienteConContratoExeption;
import excepciones.ClienteDuplicadoExeption;
import java.util.ArrayList;

/**
 *
 * @author bryan
 */
public class ControladorCliente {
      
    private ListaClientes lista;

    public ControladorCliente() {
        lista = new ListaClientes();
    }

    public void guardar(Cliente cliente) throws ClienteDuplicadoExeption {
        lista.agregar(cliente);
    }

    public boolean actualizar(int id, String nombre, String telefono, String correo) {
        return lista.actualizar(id, nombre, telefono, correo);
    }

    public Cliente buscarPorId(int id) {
        return lista.buscarPorId(id);
    }

    public ArrayList<Cliente> getClientes() {
        return lista.getCliente();
    }

    public boolean eliminar(int id, ArrayList<Contrato> contratos) throws ClienteConContratoExeption {
        return lista.eliminar(id, contratos);
    }
}
