/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoClientes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import modelo.Cliente;
import reportes.ReporteClientes;
import vista.VistaCliente;

/**
 * Clase encargada de reaccionar a los eventos generados por la interfaz para
 * agregar, modificar, eliminar y reportar Clientes
 *
 * @author DANIEL
 */
public class ControladorClientes implements ActionListener {

    private VistaCliente vista;
    private ReporteClientes reporte;
    private DaoClientes dao;
    private Cliente modelo;
    
    public ControladorClientes(Connection connection) {
        this.vista = new VistaCliente();
        this.vista.setVisible(true);
        this.vista.jBtnAgregar.addActionListener(this);
        this.vista.jBtnBuscar.addActionListener(this);
        this.vista.jBtnModificar.addActionListener(this);
        this.vista.jBtnEliminar.addActionListener(this);
        this.vista.jBtnVerClientes.addActionListener(this);

        reporte = new ReporteClientes();
        reporte.jTblClientes.setAutoCreateRowSorter(true);
        reporte.jTxtFiltroNit.addActionListener(this);
        reporte.jTxtFiltroNombre.addActionListener(this);

        this.dao = new DaoClientes(connection);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        //Control eventos interfaz principal
        //TODO: Terminar Clientes
        if (ev.getSource() == vista.jBtnAgregar) {
            modelo = new Cliente();
            modelo.setNit(vista.jTxtNit.getText());
            modelo.setNombre(vista.jTxtNombre.getText());
            modelo.setTelefono(vista.jTxtTel.getText());
            modelo.setDpi(vista.jTxtDpi.getText());
            modelo.setDireccion(vista.jTxtDireccion.getText());
            modelo.setEmail(vista.jTxtEmail.getText());
            modelo.setCredito(Double.valueOf(vista.jTxtCredito.getText()));
            
            dao.agregar(modelo);
            
        } else if (ev.getSource() == vista.jBtnVerClientes) {
            reporte.setVisible(true);
            ControladorTabla.llenar(reporte.jTblClientes,
                    dao.ALL.split(","),
                    dao.buscarVarios("*", "")
                    );
        }

        //Control eventos reporte
        //TODO: Filtrar sobre datos ya filtrados
        if (reporte != null) {
            if (ev.getSource() == reporte.jTxtFiltroNit) {
                ControladorTabla.filtrar(reporte.jTblClientes,
                        reporte.jTxtFiltroNit.getText(),
                        0);
                        reporte.jTxtFiltroNombre.setText("");
            } else if (ev.getSource() == reporte.jTxtFiltroNombre) {
                ControladorTabla.filtrar(reporte.jTblClientes,
                        reporte.jTxtFiltroNombre.getText(),
                        1);
                        reporte.jTxtFiltroNit.setText("");
            }

        }
    }

}
