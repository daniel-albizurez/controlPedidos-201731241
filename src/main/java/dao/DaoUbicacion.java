/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import modelo.Ubicacion;

/**
 *
 * @author DANIEL
 */
public class DaoUbicacion extends Dao<Ubicacion> {

    private static final String TABLA = "ubicacion";

    //NOT NULL
    public static final String PRODUCTO = "producto";
    public static final String TIENDA = "tienda";
    public static final String NOT_NULL = PRODUCTO + COMA + TIENDA;
    //NULL
    public static final String CANTIDAD = "cantidad";

    private static final String ALL = NOT_NULL + COMA + CANTIDAD;

    public DaoUbicacion(Connection connection) {
        super(connection);
    }

    @Override
    public String tabla() {
        return TABLA;
    }

    public Ubicacion seleccionar(Ubicacion u) {
        String condicion = primaryKey(u);
        try {
            String datos[] = buscarVarios(todos(), condicion).get(0);
            return generarModelo(datos);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }

    }

    @Override
    public String primaryKey(Ubicacion obj) {
        return asignacion(PRODUCTO, setTexto(obj.getProducto()))
                + AND + asignacion(TIENDA, setTexto(obj.getTienda()));
    }

    @Override
    public String camposObligatorios() {
        return NOT_NULL;
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String insertar(Ubicacion obj, boolean noObligatorios) {
        String valores
                = obj.getProducto() + COMA
                + obj.getTienda();
        if (noObligatorios) {
            valores += COMA + obj.getCantidad();
        }
        return valores;
    }

    @Override
    public String setCamposYValores(Ubicacion obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + asignacion(CANTIDAD, String.valueOf(obj.getCantidad()));
    }

    @Override
    public Ubicacion generarModelo(String[] datos) {
        Ubicacion modelo = new Ubicacion();

        modelo.setProducto(datos[0]);
        modelo.setTienda(datos[1]);
        modelo.setCantidad(Integer.parseInt(datos[2]));

        return modelo;
    }

}
