/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clientes;

import java.time.LocalDate;
import java.time.Period;
import personas.Persona;

/**
 *
 * @author bryan
 */
public class Cliente extends Persona{
    private LocalDate fechaNacimiento;
    private String correoElectronico;
 
    public Cliente() {
        super();
    }

    public Cliente(LocalDate fechaNacimiento, String correoElectronico, int ID, String nombreCompleto, String apellido) {
        super(ID, nombreCompleto, apellido);
        this.fechaNacimiento = fechaNacimiento;
        this.correoElectronico = correoElectronico;
    }
 
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
 
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
 
    public String getCorreoElectronico() {
        return correoElectronico;
    }
 
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }
 
    public int calcularEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }

    @Override
    public String toString() {
        return "Cliente{" + "fechaNacimiento=" + fechaNacimiento + ", correoElectronico=" + correoElectronico + '}';
    }
    
    
   
}
