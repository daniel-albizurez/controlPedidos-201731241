/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorReportes;

import controlador.Controlador;
import controlador.ControladorTabla;
import dao.DaoVistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import reportes.ReporteCompras;

/**
 *
 * @author DANIEL
 */
public class ControladorReporteCompras implements ActionListener {

    private DaoVistas detalleVenta;
    private DaoVistas detallePedido;

    private ReporteCompras vista;
    private JTable tablaCompras;
    private JTable tablaPedidos;
    private JButton buscar;
    private JTextField txtNit;

    private final String vistaVenta = "venta_detallada";
    private final String camposVenta = "venta, nit, cliente, total, producto, precio, cantidad";
    private final String vistaPedido = "pedido_detallado";

    public ControladorReporteCompras(Connection connection) {
        detalleVenta = new DaoVistas(vistaVenta, camposVenta, connection);
        detallePedido = new DaoVistas(vistaPedido, camposVenta.replace("venta", "pedido"), connection);

        vista = new ReporteCompras();
        tablaCompras = vista.jTblCompras;
        tablaPedidos = vista.jTblPedidosCancelados;
        (buscar = vista.jBtnBuscar).addActionListener(this);
        vista.jTxtFiltroPedido.addActionListener(this);
        vista.jTxtFiltroVenta.addActionListener(this);
        txtNit = vista.jTxtNit;
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String mensaje = "";
        if (ev.getSource() == buscar) {
            String nit = txtNit.getText();
            if (!nit.isBlank()) {
                ControladorTabla.llenar(tablaCompras,
                        camposVenta.toUpperCase().split(","),
                        detalleVenta.buscarVarios("*", detalleVenta.asignacion("nit", detalleVenta.setTexto(nit))));
                ControladorTabla.llenar(tablaPedidos,
                        camposVenta.replace("venta", "pedido").toUpperCase().split(","),
                        detallePedido.buscarVarios("*", detallePedido.asignacion("nit", detallePedido.setTexto(nit))));
            } else {
                mensaje = String.format(Controlador.INGRESE_VALOR, "nit");
                ControladorTabla.vaciar(tablaCompras);
                ControladorTabla.vaciar(tablaPedidos);
                vista.jTxtFiltroVenta.setText("Filtrar por codigo de venta");
                vista.jTxtFiltroPedido.setText("Filtrar por codigo de pedido");
            }
        } else if (ev.getSource() == vista.jTxtFiltroPedido) {
            String codigo = vista.jTxtFiltroPedido.getText();

            ControladorTabla.filtrar(tablaPedidos, codigo, 0);

        } else if (ev.getSource() == vista.jTxtFiltroVenta) {
            String codigo = vista.jTxtFiltroVenta.getText();

            ControladorTabla.filtrar(tablaCompras, codigo, 0);

        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }
}
