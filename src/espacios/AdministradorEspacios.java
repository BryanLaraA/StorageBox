package espacios;
import excepciones.EspacioDuplicadoException;
import excepciones.EspacioAlquiladoException;
import java.util.LinkedList;

public class AdministradorEspacios {
    private LinkedList<Espacio> espacios;

    public AdministradorEspacios() {
        this.espacios = new LinkedList<>();
    }
    
public void agregarEspacio(Espacio nuevoEspacio) throws EspacioDuplicadoException {
    for (Espacio espacio : espacios){
        
        if (espacio.getNumero() == nuevoEspacio.getNumero()) {
         throw new EspacioDuplicadoException();
        }
    }
    espacios.add(nuevoEspacio);
}  
 
public Espacio buscarNumero(int numero){
    for (Espacio espacio : espacios){
        if (espacio.getNumero() == numero){
            return espacio; 
        }
    }
    return null; 
}

public boolean actualizarEspacio(int numero,TipoEspacio tipo, double capacidad, double precio){
    Espacio espacio = buscarNumero(numero); 
    if (espacio == null) {
        return false;  
    }
    espacio.setTipo(tipo);
    espacio.setCapacidad(capacidad);
    espacio.setPrecioMensual(precio);
    
    return true; 
}

public void eliminarEspacio(int numero)throws EspacioAlquiladoException{
    Espacio espacio = buscarNumero(numero);
    
    if (espacio != null){
        if (!espacio.isDisponible()) {
            throw new EspacioAlquiladoException(); 
            
        }
    }
    espacios.remove(espacio);
}

public LinkedList<Espacio> filtrarTipo(TipoEspacio tipo) {
    LinkedList<Espacio> resultados = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.getTipo() == tipo) {
            resultados.add(espacio);
        }

    }

    return resultados;
} 

public LinkedList<Espacio> filtrarDisponibilidad(boolean disponible) {

    LinkedList<Espacio> resultados = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.isDisponible() == disponible) {
            resultados.add(espacio);
        }

    }

    return resultados;
}

public LinkedList<Espacio> filtrarRangoPrecio(double minimo, double maximo) {

    LinkedList<Espacio> resultados = new LinkedList<>();

    for (Espacio espacio : espacios) {

        if (espacio.getPrecioMensual() >= minimo &&
            espacio.getPrecioMensual() <= maximo) {

            resultados.add(espacio);
        }

    }

    return resultados;
}

public LinkedList<Espacio> obtEspacios() {
    return espacios;
}
}

