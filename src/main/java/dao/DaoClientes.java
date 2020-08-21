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
public class DaoClientes extends Dao {

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
    public <T> boolean agregar(T agregar, boolean noObligatorios) {
        Cliente nuevo = (Cliente) agregar;
        String campos = (noObligatorios) ? ALL : NOT_NULL;
        String valores
                = String.format(TEXTO, nuevo.getNit()) + COMA
                + String.format(TEXTO, nuevo.getNombre()) + COMA
                + String.format(TEXTO, nuevo.getTelefono());

        if (noObligatorios) {
            valores += COMA
                    + String.format(TEXTO, nuevo.getDpi()) + COMA
                    + String.format(TEXTO, nuevo.getDireccion()) + COMA
                    + String.format(TEXTO, nuevo.getEmail()) + COMA
                    + nuevo.getCredito();
        }
        return this.controladorDb.insert(TABLA, campos, valores);
    }

    @Override
    public <T> boolean modificar(T modificar) {
        Cliente modificado = (Cliente) modificar;
        String pk = String.format(ASIGNACION,
                NIT,
                String.format(TEXTO, modificado.getNit()));
        String valores = pk + COMA
                + String.format(ASIGNACION, NOMBRE, String.format(TEXTO, modificado.getNombre())) + COMA
                + String.format(ASIGNACION, TELEFONO, String.format(TEXTO, modificado.getTelefono())) + COMA
                + String.format(ASIGNACION, DPI, String.format(TEXTO, modificado.getDpi())) + COMA
                + String.format(ASIGNACION, DIRECCION, String.format(TEXTO, modificado.getDireccion())) + COMA
                + String.format(ASIGNACION, EMAIL, String.format(TEXTO, modificado.getEmail())) + COMA
                + String.format(ASIGNACION, CREDITO, modificado.getCredito());
        return controladorDb.update(TABLA, valores, pk);
    }

    public Cliente seleccionar(String campoCondicion, String valorCondicion) {
        String condicion = String.format(ASIGNACION,
                campoCondicion.replace(IGUAL, ""),
                valorCondicion.replace(IGUAL, ""));
        Cliente recuperado = new Cliente();
        try {
            String datos[] = buscarVarios(ALL, condicion).get(0);
            recuperado.setNit(datos[0]);
            recuperado.setNombre(datos[1]);
            recuperado.setTelefono(datos[2]);
            recuperado.setDpi(datos[3]);
            recuperado.setDireccion(datos[4]);
            recuperado.setEmail(datos[5]);
            recuperado.setCredito(Double.parseDouble(datos[6]));
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

        return recuperado;
    }

    @Override
    public <T> boolean eliminar(T eliminar) {
        Cliente eliminado = (Cliente) eliminar;
        return controladorDb.delete(TABLA, String.format(ASIGNACION, NIT, eliminado.getNit()));
    }

    @Override
    public ArrayList<String[]> buscarVarios(String campos, String condicion) {
        campos = ("*".equals(campos)) ? ALL : campos;
        return this.controladorDb.selectArray(TABLA, campos, condicion);
    }

}
