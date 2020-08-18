/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de las tablas
 * DetallePedido y DetalleVenta de la BD, las cuales manejan los mismo tipos de datos
 * @author DANIEL
 */
public class Detalle {
        //NOT NULL
    private int referencia; //El codigo del pedido o venta al que referencia este detalle
    private String articulo;
    private int cantidad;

    public Detalle() {
    }

    public Detalle(int pedido, String articulo, int cantidad) {
        this.referencia = pedido;
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    public int getReferencia() {
        return referencia;
    }

    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

}
