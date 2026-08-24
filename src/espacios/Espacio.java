package espacios;

import espacios.TipoEspacio;
import excepciones.EspacioAlquiladoException;
import excepciones.EspacioDuplicadoException;
import java.util.LinkedList;

public class Espacio {
    private LinkedList<Espacio> espacios;
    private int numero; 
    private TipoEspacio tipo; 
    private double capacidad; 
    private double precioMensual; 
    private boolean disponible; 
    

    public int getNumero() {
        return numero;
    }

    public TipoEspacio getTipo() {
        return tipo;
    }

    public double getCapacidad() {
        return capacidad;
    }

    public double getPrecioMensual() {
        return precioMensual;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setTipo(TipoEspacio tipo) {
        this.tipo = tipo;
    }

    public void setCapacidad(double capacidad) {
        this.capacidad = capacidad;
    }

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public Espacio(LinkedList<Espacio> espacios, int numero, TipoEspacio tipo, double capacidad, double precioMensual) {
        this.espacios = new LinkedList<>();
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioMensual = precioMensual;
        this.disponible = true;
    }

   

    public void asignarEspacio(){
        switch (tipo){
            case PEQUENO:
               capacidad = 5;
               precioMensual = 25000;
               break; 
            case MEDIANO:
               capacidad = 10;
               precioMensual = 45000;
               break; 
            case GRANDE:
               capacidad = 20;
               precioMensual = 70000;
               break; 
               
            
        }
    }   
    
  public void cambiarTipo(TipoEspacio nuevoTipo){
       this.tipo =nuevoTipo; 
        asignarEspacio();
    }
    
  public void ocupar(){
        this.disponible = false; 
    }
    
  public void cambiardisponible(){
        this.disponible = true; 
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



    @Override
    public String toString() {
        return "Espacio{" + "numero=" +
                numero + ", tipo=" + 
                tipo + ", capacidad=" + 
                capacidad + ", precioMensual=" + 
                precioMensual + ", disponible=" + 
                disponible + '}';
    }
    
}
