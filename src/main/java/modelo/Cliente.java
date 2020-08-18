/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Cliente de la BD
 * @author DANIEL
 */
public class Cliente {
    private String nit;
    private String nombre;
    private String dpi;
    private String telefono;
    private String direccion;
    private String email;
    private double credito;

    public Cliente() {
    }

    public Cliente(String nit, String nombre, String dpi, String telefono, String direccion, String email, double credito) {
        this.nit = nit;
        this.nombre = nombre;
        this.dpi = dpi;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
        this.credito = credito;
    }

    public Cliente(String nit, String nombre, String telefono, double credito) {
        this.nit = nit;
        this.nombre = nombre;
        this.telefono = telefono;
        this.credito = credito;
    }
            
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDpi() {
        return dpi;
    }
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public double getCredito() {
        return credito;
    }
    public void setCredito(double credito) {
        this.credito = credito;
    }

    @Override
    public String toString() {
        return "Cliente:" + " nit = " + nit + ", nombre = " + nombre + ", dpi = " + dpi + ", telefono = " + telefono + ", direccion = " + direccion + ", email = " + email + ", credito = " + credito;
    }
}
