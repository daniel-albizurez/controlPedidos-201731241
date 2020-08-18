/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Pedido de la BD
 * @author DANIEL
 */
public class Pedido {
    //NOT NULL
    private int codigo;
    private String tiendaOrigen;
    private String tiendaDestino;
    private String fechaPedido;
    private String cliente;
    private double total;
    private double anticipo;
    //NULL
    private String fechaCancelacion;
    private String fechaEnTienda;
    private String estado;

    public Pedido() {
    }

    public Pedido(int codigo, String tiendaOrigen, String tiendaDestino, String fechaPedido, String cliente, double total, double anticipo) {
        this.codigo = codigo;
        this.tiendaOrigen = tiendaOrigen;
        this.tiendaDestino = tiendaDestino;
        this.fechaPedido = fechaPedido;
        this.cliente = cliente;
        this.total = total;
        this.anticipo = anticipo;
    }

    public Pedido(int codigo, String tiendaOrigen, String tiendaDestino, String fechaPedido, String cliente, double total, double anticipo, String fechaCancelacion, String fechaEnTienda, String estado) {
        this.codigo = codigo;
        this.tiendaOrigen = tiendaOrigen;
        this.tiendaDestino = tiendaDestino;
        this.fechaPedido = fechaPedido;
        this.cliente = cliente;
        this.total = total;
        this.anticipo = anticipo;
        this.fechaCancelacion = fechaCancelacion;
        this.fechaEnTienda = fechaEnTienda;
        this.estado = estado;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getTiendaOrigen() {
        return tiendaOrigen;
    }

    public void setTiendaOrigen(String tiendaOrigen) {
        this.tiendaOrigen = tiendaOrigen;
    }

    public String getTiendaDestino() {
        return tiendaDestino;
    }

    public void setTiendaDestino(String tiendaDestino) {
        this.tiendaDestino = tiendaDestino;
    }

    public String getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(String fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(double anticipo) {
        this.anticipo = anticipo;
    }

    public String getFechaCancelacion() {
        return fechaCancelacion;
    }

    public void setFechaCancelacion(String fechaCancelacion) {
        this.fechaCancelacion = fechaCancelacion;
    }

    public String getFechaEnTienda() {
        return fechaEnTienda;
    }

    public void setFechaEnTienda(String fechaEnTienda) {
        this.fechaEnTienda = fechaEnTienda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
}
