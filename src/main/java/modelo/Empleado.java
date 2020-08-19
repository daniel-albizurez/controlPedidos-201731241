/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Empleado de la BD
 * @author DANIEL
 */
public class Empleado {
    //NOT NULL
    private int codigo;
    private String dpi;
    private String nombre;
    private String telefono;
    private String direccion;
    private String email;
    //NULL
    private String nit;

    public Empleado(int codigo, String dpi, String nombre, String nit, String telefono, String direccion, String email) {
        this.codigo = codigo;
        this.dpi = dpi;
        this.nombre = nombre;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
        this.email = email;
    }

    public Empleado(int codigo, String dpi, String nombre, String telefono) {
        this.codigo = codigo;
        this.dpi = dpi;
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public Empleado() {
    }

    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDpi() {
        return dpi;
    }
    public void setDpi(String dpi) {
        this.dpi = dpi;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
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

    @Override
    public String toString() {
        return "Empleado:" + " codigo = " + codigo + ", dpi = " + dpi + ", nombre = " + nombre + ", nit = " + nit + ", telefono = " + telefono + ", direccion = " + direccion + ", email = " + email;
    }
    
}
