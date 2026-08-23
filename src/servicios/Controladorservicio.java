/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import java.util.ArrayList;

/**
 *
 * @author lenno
 */
public class Controladorservicio {

    private ArrayList<Servicio> servicios;

    public Controladorservicio() {
        servicios = new ArrayList<>();
    }

    public void guardar(Servicio servicio) {
        if (servicio != null) {
            servicios.add(servicio);
        }
    }

    public Servicio buscar(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        for (Servicio servicio : servicios) {

            if (servicio.getNombre().equalsIgnoreCase(nombre.trim())) {
                return servicio;
            }
        }

        return null;
    }

    public boolean actualizar(Servicio servicioActualizado) {

        if (servicioActualizado == null) {
            return false;
        }

        for (int i = 0; i < servicios.size(); i++) {

            if (servicios.get(i).getNombre().equalsIgnoreCase(servicioActualizado.getNombre())) {
                servicios.set(i, servicioActualizado);
                return true;
            }
        }

        return false;
    }

    public boolean eliminar(int codigo) {

    for (int i = 0; i < servicios.size(); i++) {

        if (servicios.get(i).getCodigo() == codigo) {

            servicios.remove(i);
            return true;
        }
    }

    return false;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }
}