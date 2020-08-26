/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainTest;

import controlador.ControladorClientes;
import controlador.ControladorModificacionPedido;
import controlador.ControladorPedido;
import controlador.ControladorProductos;
import controlador.ControladorTiempo;
import controlador.ControladorTienda;
import controlador.ControladorVenta;
import controladorReportes.ControladorReporteCompras;
import controladorReportes.ControladorReportePedidos;
import controladorReportes.ControladorReportePedidosEnRuta;
import controladorReportes.ControladorReporteProductosVentas;
import java.sql.Connection;
import java.sql.DriverManager;
import modelo.Tienda;

/**
 *
 * @author DANIEL
 */
public class mainTest {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // ControladorArchivo controlador = new ControladorArchivo();
        String url = "jdbc:mysql://localhost:3306/control_pedidos?useSSL=false";
        String user = "root";
        String password = "root";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
//            ControladorClientes reporte = new ControladorClientes(connection);

            Tienda actual = new Tienda();
            actual.setCodigo("2a");
//            ControladorProductos producto = new ControladorProductos(connection,actual);

//            ControladorTienda tienda = new ControladorTienda(connection);
//            ControladorTiempo tiempo = new ControladorTiempo(connection);
//            ControladorVenta venta = new ControladorVenta(connection, actual);
            

//            ControladorPedido pedido = new ControladorPedido(connection, actual);
        
//            ControladorModificacionPedido entregar = new ControladorModificacionPedido(connection, actual);
       
//            ControladorReportePedidos pedidos = new ControladorReportePedidos(connection, actual);
    
//            ControladorReporteCompras compras = new ControladorReporteCompras(connection);

//            ControladorReportePedidosEnRuta enRuta = new ControladorReportePedidosEnRuta(connection);

//            ControladorReporteProductosVentas productos = new ControladorReporteProductosVentas(connection);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

}
