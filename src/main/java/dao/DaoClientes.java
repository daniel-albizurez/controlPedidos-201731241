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

    private final String TABLA = "cliente";
    //Not null
    private final String NIT = "nit";
    private final String NOMBRE = "nombre";
    private final String TELEFONO = "telefono";
    private final String NOT_NULL = NIT + COMA + NOMBRE + COMA + TELEFONO;

    //Null
    private final String DPI = "dpi";
    private final String DIRECCION = "direccion";
    private final String EMAIL = "email";
    private final String CREDITO = "credito";
    private final String NULL = DPI + COMA + DIRECCION + COMA + EMAIL + COMA + CREDITO;

    public final String ALL = NOT_NULL + COMA + NULL;

    public DaoClientes(Connection connection) {
        super(connection);
    }

    @Override
    public String insertar(Cliente obj, boolean noObligatorios){
        String valores = 
                setTexto(obj.getNit()) + COMA
                + setTexto(obj.getNombre()) + COMA
                + setTexto(obj.getTelefono());

        if (noObligatorios) {
            valores += COMA
                    + setTexto(obj.getDpi()) + COMA
                    + setTexto(obj.getDireccion()) + COMA
                    + setTexto(obj.getEmail()) + COMA
                    + obj.getCredito();
        }
        return valores;
    }
    
    @Override
    public String setCamposYValores(Cliente obj){
        return primaryKey(obj) + COMA
                + asignacion(NOMBRE, setTexto(obj.getNombre())) + COMA
                + asignacion(TELEFONO, setTexto(obj.getTelefono())) + COMA
                + asignacion(DPI, setTexto(obj.getDpi())) + COMA
                + asignacion(DIRECCION, setTexto(obj.getDireccion())) + COMA
                + asignacion(EMAIL, setTexto(obj.getEmail())) + COMA
                + asignacion(CREDITO, ""+obj.getCredito());
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
    public String camposObligatorios(){
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
        return asignacion(NIT,setTexto(obj.getNit()));
    }

}
