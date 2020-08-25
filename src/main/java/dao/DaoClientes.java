/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import modelo.Cliente;

/**
 *
 * @author DANIEL
 */
public class DaoClientes extends Dao<Cliente> {

    private static final String TABLA = "cliente";
    //Not null
    public static final String NIT = "nit";
    public static final String NOMBRE = "nombre";
    public static final String TELEFONO = "telefono";
    public static final String NOT_NULL = NIT + COMA + NOMBRE + COMA + TELEFONO;

    //Null
    public static final String DPI = "dpi";
    public static final String DIRECCION = "direccion";
    public static final String EMAIL = "email";
    public static final String CREDITO = "credito";
    public static final String NULL = DPI + COMA + DIRECCION + COMA + EMAIL + COMA + CREDITO;

    private static final String ALL = NOT_NULL + COMA + NULL;

    public DaoClientes(Connection connection) {
        super(connection);
    }

    @Override
    public String insertar(Cliente obj) {
        String valores
                = setTexto(obj.getNit()) + COMA
                + setTexto(obj.getNombre()) + COMA
                + setTexto(obj.getTelefono()) + COMA
                + valorPorDefecto(obj.getDpi()) + COMA
                + valorPorDefecto(obj.getDireccion()) + COMA
                + valorPorDefecto(obj.getEmail()) + COMA
                + valorPorDefecto(obj.getCredito());
        return valores;
    }

    @Override
    public String setCamposYValores(Cliente obj) {
        return primaryKey(obj) + COMA
                + asignacion(NOMBRE, setTexto(obj.getNombre())) + COMA
                + asignacion(TELEFONO, setTexto(obj.getTelefono())) + COMA
                + asignacion(DPI, valorPorDefecto(obj.getDpi())) + COMA
                + asignacion(DIRECCION, valorPorDefecto(obj.getDireccion())) + COMA
                + asignacion(EMAIL, valorPorDefecto(obj.getEmail())) + COMA
                + asignacion(CREDITO, valorPorDefecto(obj.getCredito()));
    }

    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String camposObligatorios() {
        return NOT_NULL;
    }

    @Override
    public Cliente generarModelo(String[] datos) {
        Cliente modelo = new Cliente();
        modelo.setNit(datos[0]);
        modelo.setNombre(datos[1]);
        modelo.setTelefono(datos[2]);
        modelo.setDpi(datos[3]);
        modelo.setDireccion(datos[4]);
        modelo.setEmail(datos[5]);
        modelo.setCredito(Double.parseDouble(datos[6]));
        return modelo;
    }

    @Override
    public String primaryKey(Cliente obj) {
        return asignacion(NIT, setTexto(obj.getNit()));
    }

}
