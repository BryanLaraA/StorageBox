/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package contratos;

import clientes.Cliente;
import espacios.Espacio;
import excepciones.EstadoInvalidoException;
import excepciones.FechaInvalidaException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import servicios.Servicio;

/**
 *
 * @author bryan
 */
public class Contrato {
    private static final double IMPUESTO = 0.13;
    private static int contadorContratos = 1;
    private int numeroContrato;
    private Cliente cliente;
    private Espacio espacio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private EstadoContrato estado;
    private ArrayList<Servicio> serviciosAdicionales;
    private double subTotal;
    private double impuestos;
    private double total;

    public Contrato(Cliente cliente, Espacio espacio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.numeroContrato = contadorContratos++;
        this.cliente = cliente;
        this.espacio = espacio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = EstadoContrato.PENDIENTE;
        this.serviciosAdicionales = new ArrayList<>();
    }

    public static void validarFechas(LocalDate inicio, LocalDate fin) throws FechaInvalidaException {
        if (inicio == null || fin == null) {
            throw new FechaInvalidaException("Las fechas no pueden estar vacías.");
        }
        if (fin.isBefore(inicio)) {
            throw new FechaInvalidaException("La fecha final no puede ser anterior a la fecha de inicio.");
        }
        if (fin.isEqual(inicio)) {
            throw new FechaInvalidaException("El período del contrato debe ser de al menos un día.");
        }
    }
    public static double[] calcularCostosPreview(Espacio espacio, LocalDate fechaInicio,
            LocalDate fechaFin, ArrayList<Servicio> servicios) {
        long dias = ChronoUnit.DAYS.between(fechaInicio, fechaFin);
        int periodos = dias <= 0 ? 1 : (int) Math.ceil(dias / 30.0);
        double costoEspacio = espacio.getPrecioMensual() * periodos;
        double costoServicios = 0;
        if (servicios != null) {
            for (Servicio s : servicios) {
                costoServicios += s.getPrecio();
            }
        }
        double subtotal =  costoEspacio + costoServicios;
        double impuestos = subtotal*IMPUESTO;
        double totalConImpuesto = subtotal+impuestos;
        return new double[]{subtotal, impuestos, totalConImpuesto};
    }

    public long calcularDias() {
        return ChronoUnit.DAYS.between(fechaInicio, fechaFin);
    }

    public int calcularPeriodos() {
        long dias = calcularDias();
        if (dias <= 0) {
            return 1;
        }
        return (int) Math.ceil(dias / 30.0);
    }

    private double calcularCostoConImpuesto() {
        double costoEspacio = espacio.getPrecioMensual() * calcularPeriodos();
        double costoServicios = 0;
        for (Servicio s : serviciosAdicionales) {
            costoServicios += s.getPrecio();
        }
        return costoEspacio + costoServicios;
    }

    public void calcularCostos() {
        double totalConImpuesto = calcularCostoConImpuesto();
        this.subTotal = totalConImpuesto / (1 + IMPUESTO);
        this.impuestos = totalConImpuesto - this.subTotal;
        this.total = totalConImpuesto;
    }

    public boolean addServicio(Servicio servicio) {
        if (servicio != null && !serviciosAdicionales.contains(servicio)) {
            serviciosAdicionales.add(servicio);
            return true;
        }
        return false;
    }

    public void removeServicio(Servicio servicio) {
        serviciosAdicionales.remove(servicio);
    }

    public void activar() throws EstadoInvalidoException {
        if (estado != EstadoContrato.PENDIENTE) {
            throw new EstadoInvalidoException("Solo se puede activar un contrato que esté pendiente.");
        }
        this.estado = EstadoContrato.ACTIVO;
    }

    public void finalizar() throws EstadoInvalidoException {
        if (estado != EstadoContrato.ACTIVO) {
            throw new EstadoInvalidoException("Solo se puede finalizar un contrato que esté activo.");
        }
        this.estado = EstadoContrato.FINALIZADO;
    }

    public void cancelar() throws EstadoInvalidoException {
        if (estado != EstadoContrato.PENDIENTE) {
            throw new EstadoInvalidoException("Solo se puede cancelar un contrato que esté pendiente.");
        }
        this.estado = EstadoContrato.CANCELADO;
    }

    public int getNumeroContrato() {
        return numeroContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoContrato getEstado() {
        return estado;
    }

    public ArrayList<Servicio> getServiciosAdicionales() {
        return serviciosAdicionales;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public double getImpuestos() {
        return impuestos;
    }

    public double getTotal() {
        return total;
    }
}