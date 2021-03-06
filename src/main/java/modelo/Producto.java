/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Producto de la BD
 * @author DANIEL
 */
public class Producto {
    
    private String codigo;
    private String nombre;
    private String fabricante;
    private double precio;
    private String descripcion;
    private int garantia;

    public Producto(String codigo, String nombre, String fabricante, double precio, String descripcion, int garantia) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.precio = precio;
        this.descripcion = descripcion;
        this.garantia = garantia;
    }

    public Producto(String codigo, String nombre, String fabricante, double precio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.fabricante = fabricante;
        this.precio = precio;
    }

    public Producto() {
    }

    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFabricante() {
        return fabricante;
    }
    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public double getPrecio() {
        return precio;
    }
    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getGarantia() {
        return garantia;
    }
    public void setGarantia(int garantia) {
        this.garantia = garantia;
    }

    @Override
    public String toString() {
        return "Producto:" + " codigo = " + codigo + ", nombre = " + nombre + ", fabricante = " + fabricante + ", precio = " + precio + ", descripcion = " + descripcion + ", garantia = " + garantia;
    }
    
}
