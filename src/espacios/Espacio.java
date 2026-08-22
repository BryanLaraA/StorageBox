
package espacios;
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

    public void setPrecioMensual(double precioMensual) {
        this.precioMensual = precioMensual;
    }

    public Espacio(int numero, TipoEspacio tipo, double capacidad, double precioMensual, boolean disponible) {
        this.numero = numero;
        this.tipo = tipo;
        this.capacidad = capacidad;
        this.precioMensual = precioMensual;
        this.disponible = disponible;
    }

       
    
    
}
