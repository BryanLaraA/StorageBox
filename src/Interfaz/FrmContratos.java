package Interfaz;

import clientes.Cliente;
import contratos.controlContrato;
import clientes.ControladorCliente;
import contratos.Contrato;
import espacios.AdministradorEspacios;
import espacios.Espacio;
import espacios.TipoEspacio;
import excepciones.EstadoInvalidoException;
import java.awt.event.ItemListener;
import java.beans.PropertyVetoException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import servicios.Controladorservicio;
import servicios.Servicio;
import excepciones.FechaInvalidaException;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */


/**
 *
 * @author andre
 */
public class FrmContratos extends javax.swing.JInternalFrame {

    private controlContrato controlContrato;
    private ControladorCliente controlCliente;
    private AdministradorEspacios controlEspacio;
    private Controladorservicio controlServicio;
    
    private Cliente clienteSeleccionado;
    private Espacio espacioSeleccionado;
    private Contrato contratoCargado;
    private JCheckBox[] checkboxesServicios;
    
    public FrmContratos(controlContrato controlContrato, ControladorCliente controlCliente, 
                AdministradorEspacios controlEspacio, Controladorservicio controlServicio) {
        initComponents();
        this.controlContrato = controlContrato;
        this.controlCliente = controlCliente;
        this.controlEspacio = controlEspacio;
        this.controlServicio = controlServicio;
        cargarCheckboxesServicios();
        txtFechaInicio.addPropertyChangeListener("date", evt -> updateEspacioDisponible());
        txtFechaFin.addPropertyChangeListener("date", evt -> updateEspacioDisponible());
        addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
            @Override
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent e) {
                cargarCheckboxesServicios();
            }
        });
        
        setClosable(true);
        setMaximizable(true);
        setIconifiable(true);
        setResizable(true);
    }
    private void cargarCheckboxesServicios() {
        checkboxesServicios = new JCheckBox[]{
        checkBox1, checkBox2, checkBox3, checkBox4, checkBox5,
        checkBox6, checkBox7, checkBox8, checkBox9
        };
        ArrayList<Servicio> servicios = controlServicio.getServicios();
        for (int i = 0; i < checkboxesServicios.length; i++) {
            if (i < servicios.size()) {
                Servicio s = servicios.get(i);
                checkboxesServicios[i].setText(s.getNombre());
                checkboxesServicios[i].putClientProperty("servicio", s);
                checkboxesServicios[i].setVisible(true);
                checkboxesServicios[i].setSelected(false);
            } else {
                checkboxesServicios[i].setText("");
                checkboxesServicios[i].putClientProperty("servicio", null);
                checkboxesServicios[i].setVisible(false);
            }
            for (ItemListener l : checkboxesServicios[i].getItemListeners()) {
                checkboxesServicios[i].removeItemListener(l); 
            }
            checkboxesServicios[i].addItemListener(e -> recalcularCostos());
        }
    }
    private ArrayList<Servicio> getServiciosSeleccionados() {
        ArrayList<Servicio> seleccionados = new ArrayList<>();
        for (JCheckBox cb : checkboxesServicios) {
            Servicio s = (Servicio) cb.getClientProperty("servicio");
            if (s != null && cb.isSelected()) {
                seleccionados.add(s);
            }
        }
        return seleccionados;
    }
    private void updateEspacioDisponible() {
        TipoEspacio tipo = getTipoSeleccionado();
        Date fi = txtFechaInicio.getDate();
        Date ff = txtFechaFin.getDate();
        if (tipo == null || fi == null || ff == null) {
        return;
        }
        LocalDate fechaInicio = toLocalDate(fi);
        LocalDate fechaFin = toLocalDate(ff);
        ArrayList<Espacio> disponibles = controlContrato.buscarDisponiblesTipo(
                controlEspacio.getEspacios(), tipo, fechaInicio, fechaFin);
        if (disponibles.isEmpty()) {
            espacioSeleccionado = null;
        lblEspacioSeleccionado.setText("Sin espacios disponibles");
        } else {
            espacioSeleccionado = disponibles.get(0);
            lblEspacioSeleccionado.setText(disponibles.size() + " disponibles (asignado #" + espacioSeleccionado.getNumero() + ")");
        }
        recalcularCostos();
    }
    private TipoEspacio getTipoSeleccionado() {
        if (comboxEspacio.getSelectedItem() == null) return null;
        String seleccion = comboxEspacio.getSelectedItem().toString().trim();
        switch (seleccion) {
            case "Pequeño": return TipoEspacio.PEQUEÑO;
            case "Mediano": return TipoEspacio.MEDIANO;
            case "Grande": return TipoEspacio.GRANDE;
            default: return null;
        }
    }
    private LocalDate toLocalDate(java.util.Date fecha) {
        return fecha.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
    }
    private void recalcularCostos() {
    if (espacioSeleccionado == null || txtFechaInicio.getDate() == null || txtFechaFin.getDate() == null) {
        lblSubtotal.setText("0.00");
        lblImpuestos.setText("0.00");
        lblTotal.setText("0.00");
        return;
    }

    double[] costos = Contrato.calcularCostos(espacioSeleccionado,
            toLocalDate(txtFechaInicio.getDate()),
            toLocalDate(txtFechaFin.getDate()),
            getServiciosSeleccionados());

    lblSubtotal.setText(String.format("%,.2f", costos[0]));
    lblImpuestos.setText(String.format("%,.2f", costos[1]));
    lblTotal.setText(String.format("%,.2f", costos[2]));
    }
    public void cargarContrato(Contrato contrato) {
        contratoCargado = contrato;
        clienteSeleccionado = contrato.getCliente();
        espacioSeleccionado = contrato.getEspacio();

        lblClienteSeleccionado.setText(contrato.getCliente().getNombre());
        lblEspacioSeleccionado.setText("Espacio #" + contrato.getEspacio().getNumero());
        txtFechaInicio.setDate(java.sql.Date.valueOf(contrato.getFechaInicio()));
        txtFechaFin.setDate(java.sql.Date.valueOf(contrato.getFechaFin()));

        for (javax.swing.JCheckBox cb : checkboxesServicios) {
            Servicio s = (Servicio) cb.getClientProperty("servicio");
            cb.setSelected(s != null && contrato.getServiciosAdicionales().contains(s));
        }

        lblSubtotal.setText(String.format("%,.2f", contrato.getSubTotal()));
        lblImpuestos.setText(String.format("%,.2f", contrato.getImpuestos()));
        lblTotal.setText(String.format("%,.2f", contrato.getTotal()));
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblCliente = new javax.swing.JLabel();
        lblEspacio = new javax.swing.JLabel();
        comboxEspacio = new javax.swing.JComboBox<>();
        btnBuscarCliente = new javax.swing.JButton();
        lblClienteSeleccionado = new javax.swing.JLabel();
        lblFechaInicio = new javax.swing.JLabel();
        lblFechaFin = new javax.swing.JLabel();
        txtFechaFin = new com.toedter.calendar.JDateChooser();
        txtFechaInicio = new com.toedter.calendar.JDateChooser();
        lblEspacioSeleccionado = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        lblServiciosAdicionales = new javax.swing.JLabel();
        checkBox1 = new javax.swing.JCheckBox();
        lblTituloTotal = new javax.swing.JLabel();
        lblTituloSubtotal = new javax.swing.JLabel();
        lblTituloImpuestos = new javax.swing.JLabel();
        lblSubtotal = new javax.swing.JLabel();
        lblImpuestos = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        checkBox2 = new javax.swing.JCheckBox();
        checkBox3 = new javax.swing.JCheckBox();
        checkBox4 = new javax.swing.JCheckBox();
        checkBox5 = new javax.swing.JCheckBox();
        checkBox6 = new javax.swing.JCheckBox();
        checkBox7 = new javax.swing.JCheckBox();
        checkBox8 = new javax.swing.JCheckBox();
        checkBox9 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        btnCrear = new javax.swing.JButton();
        btnActivar = new javax.swing.JButton();
        btnFinalizar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnBuscarContrato = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        lblContratos = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();

        lblCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblCliente.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCliente.setText("Cliente");

        lblEspacio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblEspacio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEspacio.setText("Espacio");

        comboxEspacio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        comboxEspacio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pequeño ", "Mediano", "Grande" }));
        comboxEspacio.addActionListener(this::comboxEspacioActionPerformed);

        btnBuscarCliente.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscarCliente.setText("Buscar Cliente");
        btnBuscarCliente.addActionListener(this::btnBuscarClienteActionPerformed);

        lblClienteSeleccionado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblClienteSeleccionado.setText("Seleccione un cliente");

        lblFechaInicio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaInicio.setText("Fecha Inicio");

        lblFechaFin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblFechaFin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFechaFin.setText("Fecha Fin");

        txtFechaFin.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        txtFechaInicio.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        lblEspacioSeleccionado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEspacioSeleccionado.setText("Seleccione un espacio");

        txtIdCliente.setText("Digite el ID");
        txtIdCliente.addActionListener(this::txtIdClienteActionPerformed);

        lblServiciosAdicionales.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblServiciosAdicionales.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblServiciosAdicionales.setText("Servicios Adicionales");

        checkBox1.setText("jCheckBox1");

        lblTituloTotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloTotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloTotal.setText("Total");

        lblTituloSubtotal.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloSubtotal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloSubtotal.setText("Subtotal");

        lblTituloImpuestos.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblTituloImpuestos.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTituloImpuestos.setText("Impuestos");

        lblSubtotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSubtotal.setText("0.00");

        lblImpuestos.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblImpuestos.setText("0.00");

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTotal.setText("0.00");

        checkBox2.setText("jCheckBox1");

        checkBox3.setText("jCheckBox1");

        checkBox4.setText("jCheckBox1");

        checkBox5.setText("jCheckBox1");

        checkBox6.setText("jCheckBox1");

        checkBox7.setText("jCheckBox1");

        checkBox8.setText("jCheckBox1");

        checkBox9.setText("jCheckBox1");

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        btnCrear.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCrear.setText("Crear");
        btnCrear.addActionListener(this::btnCrearActionPerformed);

        btnActivar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnActivar.setText("Activar");
        btnActivar.addActionListener(this::btnActivarActionPerformed);

        btnFinalizar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnFinalizar.setText("Finalizar");
        btnFinalizar.addActionListener(this::btnFinalizarActionPerformed);

        btnCancelar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(this::btnCancelarActionPerformed);

        btnBuscarContrato.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnBuscarContrato.setText("Buscar Contrato");
        btnBuscarContrato.addActionListener(this::btnBuscarContratoActionPerformed);

        btnLimpiar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(btnCrear)
                .addGap(18, 18, 18)
                .addComponent(btnActivar)
                .addGap(18, 18, 18)
                .addComponent(btnFinalizar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 104, Short.MAX_VALUE)
                .addComponent(btnBuscarContrato)
                .addGap(18, 18, 18)
                .addComponent(btnLimpiar)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnActivar)
                    .addComponent(btnFinalizar)
                    .addComponent(btnCancelar)
                    .addComponent(btnLimpiar)
                    .addComponent(btnBuscarContrato))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setForeground(new java.awt.Color(204, 255, 255));

        lblContratos.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        lblContratos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblContratos.setText("Contratos");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(324, Short.MAX_VALUE)
                .addComponent(lblContratos, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(315, 315, 315))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblContratos)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setForeground(new java.awt.Color(140, 181, 188));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 478, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel4.setForeground(new java.awt.Color(0, 204, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 288, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 378, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscarCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(166, 166, 166)
                        .addComponent(comboxEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblClienteSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblServiciosAdicionales, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(checkBox3)
                        .addGap(6, 6, 6)
                        .addComponent(checkBox2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(checkBox4)
                        .addGap(6, 6, 6)
                        .addComponent(checkBox5)
                        .addGap(6, 6, 6)
                        .addComponent(checkBox6))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(192, 192, 192)
                        .addComponent(checkBox9))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(lblTituloImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lblTituloTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(lblImpuestos, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(lblEspacioSeleccionado, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(checkBox7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(checkBox1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(checkBox8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(317, 317, 317)
                        .addComponent(lblEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblTituloSubtotal, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(110, 110, 110)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))))
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblCliente)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(btnBuscarCliente))
                            .addComponent(comboxEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6)
                        .addComponent(lblClienteSeleccionado)
                        .addGap(18, 18, 18)
                        .addComponent(lblServiciosAdicionales)
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBox3)
                            .addComponent(checkBox2))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(checkBox4)
                            .addComponent(checkBox5)
                            .addComponent(checkBox6))
                        .addGap(6, 6, 6)
                        .addComponent(checkBox9)
                        .addGap(84, 84, 84)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituloImpuestos)
                            .addComponent(lblTituloTotal))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSubtotal)
                            .addComponent(lblImpuestos)
                            .addComponent(lblTotal)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblEspacioSeleccionado))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(checkBox7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(checkBox1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(222, 222, 222)
                        .addComponent(checkBox8))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(lblEspacio))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(lblTituloSubtotal))
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(240, 240, 240)
                        .addComponent(lblFechaFin)
                        .addGap(5, 5, 5)
                        .addComponent(txtFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(150, 150, 150)
                        .addComponent(txtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(120, 120, 120)
                        .addComponent(lblFechaInicio))
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarClienteActionPerformed
        String texto = txtIdCliente.getText().trim();
        if (texto.isEmpty() || texto.equals("Digite el ID")) {
            JOptionPane.showMessageDialog(this, "Digite la identificación del cliente.");
            return;
        }
        int id;
        try {
            id = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La identificación debe ser numérica.");
            return;
        }
        clienteSeleccionado = null;
        for (Cliente c : controlCliente.getClientes()) {
            if (c.getIdPersona() == id) {
            clienteSeleccionado = c;
                break;
            }
        }
        if (clienteSeleccionado == null) {
            int opcion = JOptionPane.showConfirmDialog(this,
                    "Cliente no registrado. ¿Desea ir a registrar un cliente nuevo?",
                        "Cliente no encontrado", JOptionPane.YES_NO_OPTION);
            if (opcion == JOptionPane.YES_OPTION) {
                FrmCliente menuCliente = new FrmCliente(controlCliente);
                this.getDesktopPane().add(menuCliente);
                menuCliente.setVisible(true);
                try {
                    menuCliente.setSelected(true);
                } catch (java.beans.PropertyVetoException e) {
                }
            }
            lblClienteSeleccionado.setText("Seleccione un cliente");
        } else {
            lblClienteSeleccionado.setText(clienteSeleccionado.getNombre());
        }
    }//GEN-LAST:event_btnBuscarClienteActionPerformed

    private void comboxEspacioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboxEspacioActionPerformed
        updateEspacioDisponible();
    }//GEN-LAST:event_comboxEspacioActionPerformed

    private void btnBuscarContratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarContratoActionPerformed
        FrmBuscarContratos buscar = new FrmBuscarContratos(controlContrato, this);
        this.getDesktopPane().add(buscar);
        buscar.setVisible(true);
        try {
            buscar.setSelected(true);
        } catch (PropertyVetoException e) {
            JOptionPane.showMessageDialog(this, "Contrato no pudo ser seleccionado.");
        }
    }//GEN-LAST:event_btnBuscarContratoActionPerformed

    private void btnActivarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActivarActionPerformed
        if (contratoCargado == null) {
            JOptionPane.showMessageDialog(this, "Primero busque un contrato.");
            return;
        }
        try {
            controlContrato.activarContrato(contratoCargado);
            JOptionPane.showMessageDialog(this, "Contrato #" + contratoCargado.getNumeroContrato() + " activado.");
        } catch (EstadoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnActivarActionPerformed

    private void btnFinalizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFinalizarActionPerformed
        if (contratoCargado == null) {
            JOptionPane.showMessageDialog(this, "Primero busque un contrato.");
            return;
        }
        try {
            controlContrato.finalizarContrato(contratoCargado);
            JOptionPane.showMessageDialog(this, "Contrato #" + contratoCargado.getNumeroContrato() + " Finalizado.");
        } catch (EstadoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnFinalizarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        if (contratoCargado == null) {
            JOptionPane.showMessageDialog(this, "Primero busque un contrato.");
            return;
        }
        try {
            controlContrato.cancelarContrato(contratoCargado);
            JOptionPane.showMessageDialog(this, "Contrato #" + contratoCargado.getNumeroContrato() + " Cancelado.");
        } catch (EstadoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Valide un cliente antes de continuar.");
            return;
        }
        if (espacioSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "No hay espacio disponible para esas fechas.");
            return;
        }
        if (txtFechaInicio.getDate() == null || txtFechaFin.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione las fechas del contrato.");
            return;
        }

        try {
            Contrato nuevo = controlContrato.crearContrato(clienteSeleccionado, espacioSeleccionado,
                    toLocalDate(txtFechaInicio.getDate()),
                    toLocalDate(txtFechaFin.getDate()),
                    getServiciosSeleccionados());

            JOptionPane.showMessageDialog(this, "Contrato #" + nuevo.getNumeroContrato() + " creado.");
            contratoCargado = nuevo; 
        } catch (FechaInvalidaException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCrearActionPerformed

    private void txtIdClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdClienteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtIdClienteActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        contratoCargado = null;
        clienteSeleccionado = null;
        espacioSeleccionado = null;
        txtIdCliente.setText("");
        lblClienteSeleccionado.setText("Seleccione un cliente");
        lblEspacioSeleccionado.setText("Seleccione un espacio");
        txtFechaInicio.setDate(null);
        txtFechaFin.setDate(null);
        for (JCheckBox cb : checkboxesServicios) {
            cb.setSelected(false);
        }
        lblSubtotal.setText("0.00");
        lblImpuestos.setText("0.00");
        lblTotal.setText("0.00");
    }//GEN-LAST:event_btnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActivar;
    private javax.swing.JButton btnBuscarCliente;
    private javax.swing.JButton btnBuscarContrato;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnFinalizar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JCheckBox checkBox1;
    private javax.swing.JCheckBox checkBox2;
    private javax.swing.JCheckBox checkBox3;
    private javax.swing.JCheckBox checkBox4;
    private javax.swing.JCheckBox checkBox5;
    private javax.swing.JCheckBox checkBox6;
    private javax.swing.JCheckBox checkBox7;
    private javax.swing.JCheckBox checkBox8;
    private javax.swing.JCheckBox checkBox9;
    private javax.swing.JComboBox<String> comboxEspacio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblClienteSeleccionado;
    private javax.swing.JLabel lblContratos;
    private javax.swing.JLabel lblEspacio;
    private javax.swing.JLabel lblEspacioSeleccionado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblImpuestos;
    private javax.swing.JLabel lblServiciosAdicionales;
    private javax.swing.JLabel lblSubtotal;
    private javax.swing.JLabel lblTituloImpuestos;
    private javax.swing.JLabel lblTituloSubtotal;
    private javax.swing.JLabel lblTituloTotal;
    private javax.swing.JLabel lblTotal;
    private com.toedter.calendar.JDateChooser txtFechaFin;
    private com.toedter.calendar.JDateChooser txtFechaInicio;
    private javax.swing.JTextField txtIdCliente;
    // End of variables declaration//GEN-END:variables
}
