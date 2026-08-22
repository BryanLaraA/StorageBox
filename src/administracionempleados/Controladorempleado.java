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

    public void agregar(Empleado empleado) {
        empleados.add(empleado);
    }

    public Empleado buscar(int idEmpleado) {
        for (Empleado empleado : empleados) {
            if (empleado.getdEmpleado() == idEmpleado) {
                return empleado;
            }
        }
        return null;
    }

    public void eliminar(int idEmpleado) {
        Empleado empleado = buscar(idEmpleado);

        if (empleado != null) {
            empleados.remove(empleado);
        }
    }

    public ArrayList<Empleado> getEmpleados() {
        return empleados;
    }
}
