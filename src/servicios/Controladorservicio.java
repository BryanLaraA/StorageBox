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

    public boolean actualizar(String nombre,String descripcion,Double precio) {

        Servicio servicio = buscar(nombre);

        if (servicio == null) {
            return false;
        }

        if (descripcion != null
                && !descripcion.trim().isEmpty()) {
            servicio.setDescripcion(descripcion);
        }

        if (precio != null) {
            servicio.setPrecio(precio);
        }

        return true;
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
