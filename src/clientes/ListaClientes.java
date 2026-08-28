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
public class ListaClientes {
    private ArrayList<Cliente> clientes;

    public ListaClientes() {
        clientes = new ArrayList<>();
    }

    public ArrayList<Cliente> getCliente() {
        return clientes;
    }

    public boolean existeId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdPersona() == id) {
                return true;
            }
        }
        return false;
    }

    public void agregar(Cliente cliente) throws ClienteDuplicadoExeption {
        if (existeId(cliente.getIdPersona())) {
            throw new ClienteDuplicadoExeption(String.valueOf(cliente.getIdPersona()));
        }
        clientes.add(cliente);
    }

    public Cliente buscarPorId(int id) {
        for (Cliente cliente : clientes) {
            if (cliente.getIdPersona() == id) {
                return cliente;
            }
        }
        return null;
    }

    public boolean actualizar(int id, String nombre, String telefono, String correo) {
        Cliente cliente = buscarPorId(id);
        if (cliente == null) {
            return false;
        }
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setCorreoElectronico(correo);
        return true;
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
    
    public ArrayList<Cliente> filtrar(Integer id, String nombre) {
        ArrayList<Cliente> resultado = new ArrayList<>();
        String nombreFiltro = (nombre == null) ? "" : nombre.trim().toLowerCase();

        for (Cliente cliente : clientes) {
            boolean cumpleId = (id == null) || cliente.getIdPersona() == id;
            boolean cumpleNombre = nombreFiltro.isEmpty()
                    || cliente.getNombre().toLowerCase().contains(nombreFiltro);

            if (cumpleId && cumpleNombre) {
                resultado.add(cliente);
            }
        }
        return resultado;
    }
}
