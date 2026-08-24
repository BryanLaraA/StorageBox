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
      private ArrayList<Cliente> clientes;
 
    public ControladorCliente() {
        clientes = new ArrayList<>();
    }
 
    public void guardar(Cliente cliente) throws ClienteDuplicadoExeption {
        if (cliente == null) {
            return;
        }
        if (existeCorreo(cliente.getCorreoElectronico())) {
            throw new ClienteDuplicadoExeption(cliente.getCorreoElectronico());
        }
        clientes.add(cliente);
    }
 
    private boolean existeCorreo(String correo) {
        if (correo == null) {
            return false;
        }
        for (Cliente cliente : clientes) {
            if (cliente.getCorreoElectronico() != null
                    && cliente.getCorreoElectronico().equalsIgnoreCase(correo.trim())) {
                return true;
            }
        }
        return false;
    }
 
    public Cliente buscar(String nombreCompleto) {
        if (nombreCompleto == null || nombreCompleto.trim().isEmpty()) {
            return null;
        }
        for (Cliente cliente : clientes) {
            if (cliente.getNombre().equalsIgnoreCase(nombreCompleto.trim())) {
                return cliente;
            }
        }
        return null;
    }
    public Cliente buscarPorId(int id) {
        for (Cliente cliente : clientes) {
 
            if (cliente.getIdPersona() == id) {
                return cliente;
            }
        }
        return null;
    }
 
    public boolean actualizar(Cliente clienteActualizado) {
        if (clienteActualizado == null) {
            return false;
        }
        for (int i = 0; i < clientes.size(); i++) {
 
            if (clientes.get(i).getIdPersona() == clienteActualizado.getIdPersona()) {
                clientes.set(i, clienteActualizado);
                return true;
            }
        }
        return false;
    }
 
    public boolean eliminar(int id, ArrayList<Contrato> contratos) throws ClienteConContratoExeption {
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            return false;
        }
        if (tieneContratoVigente(cliente, contratos)) {
            throw new ClienteConContratoExeption(cliente.getNombre());
        }
        clientes.remove(cliente);
        return true;
    }
 
    private boolean tieneContratoVigente(Cliente cliente, ArrayList<Contrato> contratos) {
        if (contratos == null) {
            return false;
        }
        for (Contrato contrato : contratos) {
            if (contrato.getCliente() == cliente
                    && (contrato.getEstado() == EstadoContrato.ACTIVO
                    || contrato.getEstado() == EstadoContrato.PENDIENTE)) {
                return true;
            }
        }
        return false;
    }
 
    public ArrayList<Cliente> getClientes() {
        return clientes;
    }
    
}
