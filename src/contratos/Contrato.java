/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contratos;

import clientes.Cliente;
import espacios.Espacio;
import java.awt.List;
import java.time.LocalDate;
import servicios.Servicio;

/**
 *
 * @author bryan
 */
public class Contrato {
    private int numeroContrato;
    private Cliente cliente;
    private Espacio espacio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoContrato estado;
    private List serviciosAdicionales;
    private double subTotal;
    private double impuestos;
    private double total;

    public Contrato(Cliente cliente, Espacio espacio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.cliente = cliente;
        this.espacio = espacio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }
    
    
}
