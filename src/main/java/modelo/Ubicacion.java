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
    private int producto;
    private int tienda;
    private int cantidad;

    public Ubicacion(int producto, int tienda, int cantidad) {
        this.producto = producto;
        this.tienda = tienda;
        this.cantidad = cantidad;
    }

    public Ubicacion() {
    }

    public int getProducto() {
        return producto;
    }
    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getTienda() {
        return tienda;
    }
    public void setTienda(int tienda) {
        this.tienda = tienda;
    }

    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
}
