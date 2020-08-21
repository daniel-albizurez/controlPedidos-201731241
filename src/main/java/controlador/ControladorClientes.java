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
import javax.swing.JOptionPane;
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
    Cliente modelo;

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
        String mensaje = "";
        if (ev.getSource() == vista.jBtnAgregar) {
            modelo = construirModelo();

            mensaje = (dao.agregar(modelo, false))
                    ? "Se ha ingresado el cliente con nit " + modelo.getNit()
                    : "El cliente con nit " + modelo.getNit() + " ya existe";
        } else if (ev.getSource() == vista.jBtnBuscar) {
            String nit = vista.jTxtNit.getText();
            if (!nit.isBlank()) {
                modelo = dao.seleccionar("NIT", nit);

                if (modelo != null) {
                    mostrarModelo(modelo);
                    mensaje = "";

                    vista.jTxtNit.setEditable(false);

                    vista.jBtnModificar.setEnabled(true);
                    vista.jBtnAgregar.setEnabled(false);
                    vista.jBtnEliminar.setEnabled(true);

                } else {
                    mensaje = "No existe un cliente con el nit " + nit;
                }

            }

        } else if (ev.getSource() == vista.jBtnModificar) {
            if (modelo.getNit().equals(vista.jTxtNit.getText())) {
                modelo = construirModelo();
                mensaje = (dao.modificar(modelo)) 
                        ? "La modificación del cliente con nit " + 
                        modelo.getNit() + " ha sido exitosa"
                        : "No se ha podido modificar el cliente";
            }
        } else if (ev.getSource() == vista.jBtnEliminar) {
            if (modelo.getNit().equals(vista.jTxtNit.getText())) {
                modelo = construirModelo();
                mensaje = (dao.eliminar(modelo))
                        ? "Cliente eliminado exitosamente"
                        : "No se ha podido eliminar el cliente";
            }
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
        if (!mensaje.isBlank()) JOptionPane.showMessageDialog(vista, mensaje);
    }

    public Cliente construirModelo() {
        Cliente cliente = new Cliente();
        cliente.setNit(vista.jTxtNit.getText());
        cliente.setNombre(vista.jTxtNombre.getText());
        cliente.setTelefono(vista.jTxtTel.getText());
        cliente.setDpi(vista.jTxtDpi.getText());
        cliente.setDireccion(vista.jTxtDireccion.getText());
        cliente.setEmail(vista.jTxtEmail.getText());
        cliente.setCredito(Double.valueOf(vista.jTxtCredito.getText()));
        return cliente;
    }

    public void mostrarModelo(Cliente cliente) {
        vista.jTxtNit.setText(cliente.getNit());
        vista.jTxtNombre.setText(cliente.getNombre());
        vista.jTxtDpi.setText(cliente.getDpi());
        vista.jTxtTel.setText(cliente.getTelefono());
        vista.jTxtDireccion.setText(cliente.getDireccion());
        vista.jTxtEmail.setText(cliente.getEmail());
        vista.jTxtCredito.setText("" + cliente.getCredito());

    }
}
