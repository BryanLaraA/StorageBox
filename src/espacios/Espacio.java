package espacios;

public class Espacio {

    private int numero;
    private TipoEspacio tipo;
    private double capacidad;
    private double precioMensual;
    private boolean disponible;

    public Espacio(int numero, TipoEspacio tipo) {
        this.numero = numero;
        this.disponible = true;
        cambiarTipo(tipo);
    }

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
    
    public void actualizarDatos(TipoEspacio nuevoTipo, double nuevoPrecioMensual) {
        cambiarTipo(nuevoTipo);
        this.precioMensual = nuevoPrecioMensual;
    }

    private void cambiarTipo(TipoEspacio nuevoTipo) {
        this.tipo = nuevoTipo;
        switch (tipo) {
            case PEQUEÑO:
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

    
    public void ocupar() {
        this.disponible = false;
    }

    public void liberar() {
        this.disponible = true;
    }

    @Override
    public String toString() {
        return "Espacio{" + "numero=" + numero 
                + ", tipo=" + tipo
                + ", capacidad=" + capacidad 
                + ", precioMensual=" + precioMensual
                + ", disponible=" + disponible + '}';
    }
}