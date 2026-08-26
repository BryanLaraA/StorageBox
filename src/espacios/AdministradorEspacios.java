package espacios;

import excepciones.EspacioAlquiladoException;
import excepciones.EspacioDuplicadoException;
import java.util.LinkedList;

public class AdministradorEspacios {

    private final LinkedList<Espacio> espacios = new LinkedList<>();

    public void agregar(Espacio espacio) throws EspacioDuplicadoException {
        if (espacio == null) {
            return;
        }
        if (existeNumero(espacio.getNumero())) {
            throw new EspacioDuplicadoException();
        }
        espacios.add(espacio);
    }

    public boolean actualizar(int numero, TipoEspacio tipo, double precioMensual) {
        Espacio espacio = buscarNumero(numero);
        if (espacio == null) {
            return false;
        }
        espacio.actualizarDatos(tipo, precioMensual);
        return true;
    }

    public boolean eliminar(int numero) throws EspacioAlquiladoException {
        Espacio espacio = buscarNumero(numero);
        if (espacio == null) {
            return false;
        }
        if (!espacio.isDisponible()) {
            throw new EspacioAlquiladoException();
        }
        return espacios.remove(espacio);
    }

    public boolean ocupar(int numero) {
        Espacio espacio = buscarNumero(numero);
        if (espacio == null || !espacio.isDisponible()) {
            return false;
        }
        espacio.ocupar();
        return true;
    }

    public boolean liberar(int numero) {
        Espacio espacio = buscarNumero(numero);
        if (espacio == null) {
            return false;
        }
        espacio.liberar();
        return true;
    }

    public Espacio buscarNumero(int numero) {
        for (Espacio espacio : espacios) {
            if (espacio.getNumero() == numero) {
                return espacio;
            }
        }
        return null;
    }

    private boolean existeNumero(int numero) {
        return buscarNumero(numero) != null;
    }

    public LinkedList<Espacio> filtrar(Integer numero, TipoEspacio tipo, Boolean disponible,
            Double precioMin, Double precioMax) {

        LinkedList<Espacio> resultado = new LinkedList<>();

        for (Espacio espacio : espacios) {
            boolean cumple = (numero == null || espacio.getNumero() == numero)
                    && (tipo == null || espacio.getTipo() == tipo)
                    && (disponible == null || espacio.isDisponible() == disponible)
                    && (precioMin == null || espacio.getPrecioMensual() >= precioMin)
                    && (precioMax == null || espacio.getPrecioMensual() <= precioMax);

            if (cumple) {
                resultado.add(espacio);
            }
        }
        return resultado;
    }

    public LinkedList<Espacio> getEspacios() {
        return espacios;
    }
}