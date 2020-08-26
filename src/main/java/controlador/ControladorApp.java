/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import controladorReportes.ControladorReporteCompras;
import controladorReportes.ControladorReportePedidos;
import controladorReportes.ControladorReportePedidosEnRuta;
import controladorReportes.ControladorReporteProductosVentas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import modelo.Tienda;
import vista.App;

/**
 *
 * @author DANIEL
 */

public class ControladorApp implements ActionListener{

    private App vista;
    private Connection connection;
    private Tienda actual;
    
    public ControladorApp(Connection connection, Tienda actual) {
        vista = new App();
        this.connection = connection;
        this.actual = actual;
        
        vista.jBtnClientes.addActionListener(this);
        vista.jBtnPedido.addActionListener(this);
        vista.jBtnProductos.addActionListener(this);
        vista.jBtnReportes.addActionListener(this);
        vista.jBtnTiempo.addActionListener(this);
        vista.jBtnTienda.addActionListener(this);
        vista.jBtnVenta.addActionListener(this);
        vista.jBtnActualizar.addActionListener(this);
        
        vista.setVisible(true);
        
        
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == vista.jBtnClientes) {
            ControladorClientes cliente = new ControladorClientes(connection);
        } else if (ev.getSource() == vista.jBtnPedido) {
            ControladorPedido pedido = new ControladorPedido(connection, actual);
            
        } else if (ev.getSource() == vista.jBtnActualizar) {
            ControladorModificacionPedido actualizarPedidos = new ControladorModificacionPedido(connection, actual);
        } else if (ev.getSource() == vista.jBtnProductos) {
            ControladorProductos producto = new ControladorProductos(connection, actual);
        } else if (ev.getSource() == vista.jBtnTiempo) {
            ControladorTiempo tiempo = new ControladorTiempo(connection);
            
        } else if (ev.getSource() == vista.jBtnTienda) {
            ControladorTienda tienda = new ControladorTienda(connection);
        } else if (ev.getSource() == vista.jBtnVenta) {
            ControladorVenta venta = new ControladorVenta(connection, actual);
        } else if (ev.getSource() == vista.jBtnReportes) {
            ControladorReporteCompras compras = new ControladorReporteCompras(connection);
            ControladorReportePedidos pedidos = new ControladorReportePedidos(connection, actual);
            ControladorReportePedidosEnRuta enRuta = new ControladorReportePedidosEnRuta(connection);
            ControladorReporteProductosVentas ventas = new ControladorReporteProductosVentas(connection);
        }
    }
    
}
