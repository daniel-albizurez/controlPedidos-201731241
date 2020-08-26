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
import reportes.ReportePedidosEnRuta;

/**
 *
 * @author DANIEL
 */
public class ControladorReportePedidosEnRuta  implements ActionListener {

    private DaoVistas pedidosEnRuta;

    private ReportePedidosEnRuta vista;
    private JTable tablaPedidos;
    private JButton buscar;
    private JTextField txtNit;

    private final String vistaPedido = "pedidos_en_ruta";
    private final String camposVenta = "pedido, fecha_estimada, nit, cliente, total, producto, cantidad";

    public ControladorReportePedidosEnRuta (Connection connection) {
        pedidosEnRuta = new DaoVistas(vistaPedido, camposVenta.replace("venta", "pedido"), connection);

        vista = new ReportePedidosEnRuta();
        tablaPedidos = vista.jTblPedidosCancelados;
        (buscar = vista.jBtnBuscar).addActionListener(this);
        vista.jTxtFiltroPedido.addActionListener(this);
        txtNit = vista.jTxtNit;
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String mensaje = "";
        if (ev.getSource() == buscar) {
            String nit = txtNit.getText();
            if (!nit.isBlank()) {
                ControladorTabla.llenar(tablaPedidos,
                        camposVenta.replace("_", " ").toUpperCase().split(","),
                        pedidosEnRuta.buscarVarios("*", pedidosEnRuta.asignacion("nit", pedidosEnRuta.setTexto(nit))));
            } else {
                mensaje = String.format(Controlador.INGRESE_VALOR, "nit");
                ControladorTabla.vaciar(tablaPedidos);
                vista.jTxtFiltroPedido.setText("Filtrar por codigo de pedido");
            }
        } else if (ev.getSource() == vista.jTxtFiltroPedido) {
            String codigo = vista.jTxtFiltroPedido.getText();

            ControladorTabla.filtrar(tablaPedidos, codigo, 0);

        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }
}
