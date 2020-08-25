/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import modelo.Detalle;

/**
 *
 * @author DANIEL
 */
public class DaoDetalleVenta extends Dao<Detalle> {

    public static final String TABLA = "detalle_venta";

    public static final String VENTA = "venta";
    public static final String PRODUCTO = "producto";
    public static final String CANTIDAD = "cantidad";

    public static final String ALL = VENTA + COMA + PRODUCTO + COMA + CANTIDAD;

    public DaoDetalleVenta(Connection connection) {
        super(connection);
    }

    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Detalle obj) {
        return asignacion(VENTA, "" + obj.getReferencia())
                + AND + asignacion(PRODUCTO, setTexto(obj.getProducto()));
    }

    @Override
    public String camposObligatorios() {
        return ALL;
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String insertar(Detalle obj) {
        String valores =
                obj.getReferencia() + COMA
                + setTexto(obj.getProducto()) + COMA
                + obj.getCantidad();
        return valores;
    }

    @Override
    public String setCamposYValores(Detalle obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + asignacion(CANTIDAD, "" + obj.getCantidad());
    }

    @Override
    public Detalle generarModelo(String[] datos) {
        Detalle modelo = new Detalle();
        
        modelo.setReferencia(Integer.parseInt(datos[0]));
        modelo.setProducto(datos[1]);
        modelo.setCantidad(Integer.parseInt(datos[2]));
        
        return modelo;
    }

}
