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
 
    public LinkedList<Espacio> getEspacios() {
        return espacios;
    }
 
}
