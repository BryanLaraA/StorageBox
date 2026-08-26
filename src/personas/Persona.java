/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package personas;

/**
 *
 * @author bryan
 */
public abstract class Persona {
    protected int ID;
    protected String nombreCompleto;
    protected String Puesto;
    protected String telefono;

    public Persona() {
    }

    public Persona(int ID, String nombreCompleto) {
        this.ID = ID;
        this.nombreCompleto = nombreCompleto;
    }

    public int getIdPersona() {
        return ID;
    }

    public void setIdPersona(int idPersona) {
        this.ID = idPersona;
    }

    public String getNombre() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return Puesto;
    }

    public void setNombre(String nombre) {
        this.nombreCompleto = nombre;
    } 

    public void setTelefono(String telefono) {
        this.Puesto = telefono;
    }

    @Override
    public String toString() {
        return "Persona{" + "ID=" + ID + ", nombreCompleto=" + nombreCompleto + ", telefono=" + Puesto + '}';
    }
    
    
}
