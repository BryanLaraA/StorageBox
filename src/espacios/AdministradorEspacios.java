package espacios;
import espacios.Espacio;
import espacios.TipoEspacio;
import excepciones.EspacioAlquiladoException;
import excepciones.EspacioDuplicadoException;
import java.util.LinkedList;


public class AdministradorEspacios {

     private LinkedList<Espacio> espacios;
 
    public AdministradorEspacios() {
        espacios = new LinkedList<>();
    }
 
    public void agregar(Espacio espacio) throws EspacioDuplicadoException {
        if (espacio == null) {
            return;
        }
        if (existeNumero(espacio.getNumero())) {
            throw new EspacioDuplicadoException();
        }
        espacios.add(espacio);
    }
 
    private boolean existeNumero(int numero) {
        for (Espacio espacio : espacios) {
            if (espacio.getNumero() == numero) {
                return true;
            }
        }
        return false;
    }
 
    public Espacio buscarNumero(int numero) {
        for (Espacio espacio : espacios) {
 
            if (espacio.getNumero() == numero) {
                return espacio;
            }
        }
        return null;
    }
 
    public boolean actualizar(Espacio espacioActualizado) {
        if (espacioActualizado == null) {
            return false;
        }
        Espacio existente = buscarNumero(espacioActualizado.getNumero());
        if (existente == null) {
            return false;
        }
        existente.setTipo(espacioActualizado.getTipo());
        existente.setCapacidad(espacioActualizado.getCapacidad());
        existente.setPrecioMensual(espacioActualizado.getPrecioMensual());
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
        espacios.remove(espacio);
        return true;
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
        espacio.cambiardisponible();
        return true;
    }
    
    public LinkedList<Espacio> buscarTipo(TipoEspacio tipo) {

    LinkedList<Espacio> resultado = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.getTipo() == tipo) {
            resultado.add(espacio);
        }
    }

    return resultado;
}
 
    public LinkedList<Espacio> buscarDisponibilidad(boolean disponible) {

    LinkedList<Espacio> resultado = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.isDisponible() == disponible) {
            resultado.add(espacio);
        }
    }

    return resultado;
}
    
    public LinkedList<Espacio> buscarPorPrecio(double minimo, double maximo) {
    LinkedList<Espacio> resultado = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.getPrecioMensual() >= minimo &&
            espacio.getPrecioMensual() <= maximo) {
            resultado.add(espacio);
        }
    }

    return resultado;
}
    
    public LinkedList<Espacio> filtrar(
        Integer numero,
        TipoEspacio tipo,
        Boolean disponible,
        Double precioMin,
        Double precioMax) {

    LinkedList<Espacio> resultado = new LinkedList<>();

    for (Espacio espacio : espacios) {

        boolean cumple = true;

        if (numero != null &&
                espacio.getNumero() != numero) {
            cumple = false;
        }

        if (tipo != null &&
                espacio.getTipo() != tipo) {
            cumple = false;
        }

        if (disponible != null &&
                espacio.isDisponible() != disponible) {
            cumple = false;
        }

        if (precioMin != null &&
                espacio.getPrecioMensual() < precioMin) {
            cumple = false;
        }

        if (precioMax != null &&
                espacio.getPrecioMensual() > precioMax) {
            cumple = false;
        }

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
