/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servicios;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author lenno
 */
public class Controladorservicio {

    private ArrayList<Servicio> servicios;
    private HashMap<String, Servicio> indiceServicios;
    private int siguiente;

    
    public Controladorservicio() {
        servicios = new ArrayList<>();
        indiceServicios = new HashMap<>();
        siguiente = 1;
    }

    public void guardar(Servicio servicio) {
        if (servicio != null) {
            servicio.setCodigo(siguiente);
            siguiente++;
            servicios.add(servicio);
            indiceServicios.put(servicio.getNombre().toLowerCase(), servicio);
        }
    }

    public Servicio buscar(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }
        return indiceServicios.get(nombre.trim().toLowerCase());
    }

    public boolean actualizar(String nombre, String descripcion, Double precio) {
        Servicio servicio = buscar(nombre);
        if (servicio == null) {
            return false;
        }
        if (descripcion != null && !descripcion.trim().isEmpty()) {
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
                Servicio eliminado = servicios.remove(i);
                indiceServicios.remove(eliminado.getNombre().toLowerCase());
                return true;
            }
        }
        return false;
    }

    public ArrayList<Servicio> getServicios() {
        return servicios;
    }

    public ArrayList<Servicio> filtrar(String nombre, Double precioMin, Double precioMax) {
        ArrayList<Servicio> resultado = new ArrayList<>();
        String nombreFiltro = (nombre == null) ? "" : nombre.trim().toLowerCase();

        for (Servicio servicio : servicios) {
            if (!nombreFiltro.isEmpty() && !servicio.getNombre().toLowerCase().contains(nombreFiltro)) {
                continue;
            }
            if (precioMin != null && precioMin >= 0 && servicio.getPrecio() < precioMin) {
                continue;
            }
            if (precioMax != null && precioMax >= 0 && servicio.getPrecio() > precioMax) {
                continue;
            }
            resultado.add(servicio);
        }
        return resultado;
    }
}
