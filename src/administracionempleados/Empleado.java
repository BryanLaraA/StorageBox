/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package administracionempleados;

import personas.Persona;

/**
 *
 * @author lenno
 */
public class Empleado extends Persona {

    private Puesto puesto;

    public Puesto getPuesto() {
        return puesto;
    }

    public void setPuesto(Puesto puesto) {
        this.puesto = puesto;
    }

    public double getSalario() {
        return (puesto == null) ? 0 : puesto.getSalario();
    }

}
