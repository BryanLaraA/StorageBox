package contratos;

import clientes.Cliente;
import espacios.Espacio;
import espacios.TipoEspacio;
import excepciones.EstadoInvalidoException;
import excepciones.FechaInvalidaException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import servicios.Servicio;
 
public class controlContrato {
 
    private LinkedList<Contrato> contratos;
 
    public controlContrato() {
        contratos = new LinkedList<>();
    }
    
    public ArrayList<Espacio> buscarDisponiblesTipo(LinkedList<Espacio> espacios,
            TipoEspacio tipo, LocalDate fechaInicio, LocalDate fechaFin) {
        ArrayList<Espacio> delTipo = new ArrayList<>();
        for (Espacio e : espacios) {
            if (e.getTipo() == tipo) {
                delTipo.add(e);
            }
        }
        ArrayList<Espacio> disponibles = new ArrayList<>();
        for (Espacio espacio : delTipo) {
            if (!fechasValidas(espacio, fechaInicio, fechaFin)) {
                disponibles.add(espacio);
            }
        }
 
        return disponibles;
    }
    
    private boolean fechasValidas(Espacio espacio, LocalDate inicio, LocalDate fin) {
 
        for (Contrato c : contratos) {
            boolean mismoEspacio = c.getEspacio().getNumero() == espacio.getNumero();
            boolean estadoRelevante = c.getEstado() == EstadoContrato.PENDIENTE
                    || c.getEstado() == EstadoContrato.ACTIVO;
            if (mismoEspacio && estadoRelevante) {
                if (!fin.isBefore(c.getFechaInicio()) && !inicio.isAfter(c.getFechaFin())) {
                    return true;
                }
            }
        }
 
        return false;
    }
    
    public Contrato crearContrato(Cliente cliente, Espacio espacio, LocalDate 
            fechaInicio, LocalDate fechaFin, 
            ArrayList<Servicio> serviciosAdicionales) throws FechaInvalidaException {
 
        Contrato.validarFechas(fechaInicio, fechaFin);
        if (fechasValidas(espacio, fechaInicio, fechaFin)) { // true = hay conflicto (nombre engañoso, ya lo vimos)
            throw new FechaInvalidaException("El espacio #" + espacio.getNumero() + " no está disponible en esas fechas.");
        }
        Contrato contrato = new Contrato(cliente, espacio, fechaInicio, fechaFin);
 
        if (serviciosAdicionales != null) {
            for (Servicio s : serviciosAdicionales) {
                contrato.addServicio(s);
            }
        }
 
        contrato.calcularCostos();
        contratos.add(contrato);
 
        return contrato;
    }
    
    public void activarContrato(Contrato contrato) throws EstadoInvalidoException {
        contrato.activar();
        contrato.getEspacio().ocupar();
    }
 
    public void finalizarContrato(Contrato contrato) throws EstadoInvalidoException {
        contrato.finalizar();
        contrato.getEspacio().liberar();
    }
 
    public void cancelarContrato(Contrato contrato) throws EstadoInvalidoException {
        contrato.cancelar();
        contrato.getEspacio().liberar();
    }
 
    public Contrato buscarPorNumero(int numeroContrato) {
        for (Contrato c : contratos) {
            if (c.getNumeroContrato() == numeroContrato) {
                return c;
            }
        }
        return null;
    }
 
    public LinkedList<Contrato> getContratos() {
        return contratos;
    }
    
}