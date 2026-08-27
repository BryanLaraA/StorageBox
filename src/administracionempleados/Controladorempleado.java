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

    public boolean editar(String nombre, String puesto, Double nuevoSalario) {

        Empleado empleado = buscar(nombre);

        if (empleado == null) {
            return false;
        }

        if (puesto != null && !puesto.trim().isEmpty()) {
            empleado.getPuesto().setNombre(puesto);
        }

        if (nuevoSalario != null) {
            empleado.setSalario(nuevoSalario);
            empleado.getPuesto().setSalario(nuevoSalario);
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
