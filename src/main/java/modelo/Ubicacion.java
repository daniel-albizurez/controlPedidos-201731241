/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author DANIEL
 */
public class Ubicacion {
    private String producto;
    private String tienda;
    private int cantidad;

    public Ubicacion(String producto, String tienda, int cantidad) {
        this.producto = producto;
        this.tienda = tienda;
        this.cantidad = cantidad;
    }

    public Ubicacion() {
    }

    public String getProducto() {
        return producto;
    }
    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getTienda() {
        return tienda;
    }
    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
