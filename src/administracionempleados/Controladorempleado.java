/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administracionempleados;

import java.util.ArrayList;

/**
 *
 * @author lenno
 */
public class Controladorempleado {

    private ArrayList<Empleado> empleados;

    public Controladorempleado() {
        empleados = new ArrayList<>();
    }

    public void guardar(Empleado empleado) {
        empleados.add(empleado);
    }

    public Empleado buscar(String nombre) {
        for (Empleado empleado : empleados) {
            if (empleado.getNombre().equalsIgnoreCase(nombre)) {
                return empleado;
            }
        }
        return null;
    }

    public boolean editar(String nombreActual, String nuevoNombre, String nuevoTelefono, Puesto nuevoPuesto) {

        Empleado empleado = buscar(nombreActual);
        if (empleado == null) {
            return false;
        }

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            empleado.setNombre(nuevoNombre.trim());
        }

        if (nuevoTelefono != null && !nuevoTelefono.trim().isEmpty()) {
            empleado.setTelefono(nuevoTelefono.trim());
        }

        if (nuevoPuesto != null) {
            empleado.setPuesto(nuevoPuesto);
        }

        return true;
    }

    public boolean eliminar(String nombre) {
        Empleado empleado = buscar(nombre);

        if (empleado != null) {
            empleados.remove(empleado);
            return true;
        }

        return false;
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }

}
