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
    protected String nombre;
    protected String apellido;
    protected String telefono;

    public Persona() {
    }

    public Persona(int ID, String nombre, String apellido) {
        this.ID = ID;
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public int getIdPersona() {
        return ID;
    }

    public void setIdPersona(int idPersona) {
        this.ID = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }  

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    
}
