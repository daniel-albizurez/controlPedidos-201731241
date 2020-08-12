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
public class Tienda {
    
    private String codigo;
    private String nombre;
    private String direccion;
    private int telefono1;
    private int telefono2;
    private String email;
    private String horario;

    public Tienda(String codigo, String nombre, String direccion, int telefono1, int telefono2, String email, String horario) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.email = email;
        this.horario = horario;
    }

    public Tienda(String codigo, String nombre, String direccion, int telefono1) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono1 = telefono1;
    }

    public Tienda() {
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

    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono1() {
        return telefono1;
    }
    public void setTelefono1(int telefono1) {
        this.telefono1 = telefono1;
    }

    public int getTelefono2() {
        return telefono2;
    }
    public void setTelefono2(int telefono2) {
        this.telefono2 = telefono2;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }
    
}
