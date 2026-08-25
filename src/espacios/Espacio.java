package espacios;
import espacios.TipoEspacio;

public class Espacio {
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

    public Espacio(int numero, TipoEspacio tipo, double capacidad, double precioMensual) {
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
