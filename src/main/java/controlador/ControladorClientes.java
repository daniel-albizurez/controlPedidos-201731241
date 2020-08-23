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
import javax.swing.JFrame;
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
public class ControladorClientes extends Controlador<Cliente, VistaCliente, ReporteClientes> implements ActionListener {

    public ControladorClientes(Connection connection) {
        this.vista = new VistaCliente();
        this.vista.setVisible(true);

        (agregar = this.vista.jBtnAgregar).addActionListener(this);
        (buscar = this.vista.jBtnBuscar).addActionListener(this);
        (modificar = this.vista.jBtnModificar).addActionListener(this);
        (eliminar = this.vista.jBtnEliminar).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        (verTodos = this.vista.jBtnVerClientes).addActionListener(this);

//        this.vista.jBtnAgregar.addActionListener(this);
//        this.vista.jBtnBuscar.addActionListener(this);
//        this.vista.jBtnModificar.addActionListener(this);
//        this.vista.jBtnEliminar.addActionListener(this);
//        this.vista.jBtnVerClientes.addActionListener(this);
        this.vista.jTxtCredito.setText("0.00");

        reporte = new ReporteClientes();
        (tablaReporte = reporte.jTblClientes).setAutoCreateRowSorter(true);

        reporte.jTxtFiltroNit.addActionListener(this);
        reporte.jTxtFiltroNombre.addActionListener(this);

        this.dao = new DaoClientes(connection);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        mensaje = "";
        //Control eventos interfaz principal
//        String mensaje = "";
        if (ev.getSource() == agregar) {
            mensaje = agregar(construirModelo());
//            if (modelo != null) {
//            mensaje = (dao.agregar(modelo, false))
//                    ? String.format(INSERCION_CORRECTA, dao.tabla(), dao.primaryKey(modelo))
//                    : String.format(INSERCION_INCORRECTA,dao.tabla(), dao.primaryKey(modelo));
//                
//            } else {
//                mensaje = CAMPOS_OBLIGATORIOS;
//            }
        } else if (ev.getSource() == buscar) {
            String nit = vista.jTxtNit.getText();
            if (!nit.isBlank()) {
                modelo = dao.seleccionar(DaoClientes.NIT, nit);

                if (modelo != null) {
                    mostrarModelo(modelo);
                    mensaje = "";
                    interfazModificar();
//                    vista.jTxtNit.setEditable(false);
//
//                    vista.jBtnModificar.setEnabled(true);
//                    vista.jBtnAgregar.setEnabled(false);
//                    vista.jBtnEliminar.setEnabled(true);

                } else {
                    mensaje = String.format(NO_EXISTE, dao.tabla(), DaoClientes.NIT + " " + nit);
                }
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoClientes.NIT);
            }

        } else if (ev.getSource() == modificar) {
            if (modelo.getNit().equals(vista.jTxtNit.getText())) {
//                modelo = construirModelo();
                mensaje = modificar(construirModelo());
//                if (dao.modificar(modelo)) {
//                    mensaje = String.format(MODIFICACION_EXITOSA, dao.tabla(), dao.primaryKey(modelo));
//                            "La modificación del cliente con nit "
//                            + modelo.getNit() + " ha sido exitosa";
//                    limpiar();
//                } else {
//                    mensaje = MODIFICACION_FRACASADA;
//                }
            } else {
                mensaje = ERROR;
                vista.jTxtNit.setText(modelo.getNit());
            }
        } else if (ev.getSource() == eliminar) {
            if (modelo.getNit().equals(vista.jTxtNit.getText())) {
//                modelo = construirModelo();
                mensaje = eliminar(construirModelo());
            } else {
                mensaje = ERROR;
            }
        } else {
            super.actionPerformed(ev);
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
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }

    /**
     *
     * @return
     */
    @Override
    public Cliente construirModelo() {
        Cliente cliente = new Cliente();
        cliente.setNit(vista.jTxtNit.getText());
        cliente.setNombre(vista.jTxtNombre.getText());
        cliente.setTelefono(vista.jTxtTel.getText());
        cliente.setDpi(vista.jTxtDpi.getText());
        cliente.setDireccion(vista.jTxtDireccion.getText());
        cliente.setEmail(vista.jTxtEmail.getText());
        cliente.setCredito(Double.valueOf(vista.jTxtCredito.getText()));

        if (cliente.getNit().isBlank()
                || cliente.getNombre().isBlank()
                || cliente.getTelefono().isBlank()) {
            return null;
        }
        return cliente;
    }

    @Override
    public void interfazModificar() {
        vista.jTxtNit.setEditable(false);
        super.interfazModificar();
    }

    @Override
    public void mostrarModelo(Cliente cliente) {
        vista.jTxtNit.setText(cliente.getNit());
        vista.jTxtNombre.setText(cliente.getNombre());
        vista.jTxtDpi.setText(cliente.getDpi());
        vista.jTxtTel.setText(cliente.getTelefono());
        vista.jTxtDireccion.setText(cliente.getDireccion());
        vista.jTxtEmail.setText(cliente.getEmail());
        vista.jTxtCredito.setText("" + cliente.getCredito());

    }

    @Override
    public void limpiar() {
        super.limpiar();
        this.vista.jTxtNit.setText("");
        this.vista.jTxtNit.setEditable(true);
        this.vista.jTxtNombre.setText("");
        this.vista.jTxtTel.setText("");
        this.vista.jTxtDpi.setText("");
        this.vista.jTxtDireccion.setText("");
        this.vista.jTxtEmail.setText("");
        this.vista.jTxtCredito.setText("0.0");

        this.reporte.jTxtFiltroNit.setText("Ingrese un nit a buscar");
        this.reporte.jTxtFiltroNombre.setText("Ingrese un nombre a buscar");
    }
}
