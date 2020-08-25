/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Venta de la BD
 * @author DANIEL
 */
public class Venta {
    //NOT NULL
    private int codigo;
    private String tienda;
    private String cliente;
    private String fecha;
    private double total;

    public Venta() {
    }

    public Venta(int codigo, String tienda, String cliente, String fecha, double total) {
        this.codigo = codigo;
        this.tienda = tienda;
        this.cliente = cliente;
        this.fecha = fecha;
        this.total = total;
    }

    public String getTienda() {
        return tienda;
    }
    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
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
    
}
