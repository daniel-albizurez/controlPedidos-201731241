/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import modelo.Detalle;
import modelo.Venta;

/**
 *
 * @author DANIEL
 */
public class DaoVenta extends Dao<Venta> {

    public static final String TABLA = "venta";

    public static final String CODIGO = "codigo";
    public static final String TIENDA = "tienda";
    public static final String CLIENTE = "cliente";
    public static final String FECHA = "fecha";
    public static final String TOTAL = "total";

    public static final String ALL = CODIGO + COMA + TIENDA + COMA
            + CLIENTE + COMA + FECHA + COMA + TOTAL;

    private DaoDetalleVenta daoDetalle;
    
    public DaoVenta(Connection connection) {
        super(connection);
         daoDetalle = new DaoDetalleVenta(connection);
    }
    
    public boolean agregar(Venta venta, ArrayList<Detalle> detalles){
        boolean exitoso = super.agregar(venta, true);
        for (Detalle detalle : detalles) {
            exitoso &= daoDetalle.agregar(detalle, true);
        }
        if (!exitoso) {
            eliminar(venta);
        }
        return exitoso;
    }
    
    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Venta obj) {
        return asignacion(CODIGO, "" + obj.getCodigo());
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
    public String insertar(Venta obj, boolean noObligatorios) {
        String valores
                = obj.getCodigo() + COMA
                + setTexto(obj.getTienda()) + COMA
                + valorPorDefecto(obj.getCliente()) + COMA
                + setTexto(obj.getFecha()) + COMA
                + obj.getTotal();
        return valores;
    }

    @Override
    public String setCamposYValores(Venta obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + asignacion(TIENDA,setTexto(obj.getTienda())) + COMA
                + asignacion(CLIENTE, valorPorDefecto(obj.getCliente())) + COMA
                + asignacion(FECHA, setTexto(obj.getFecha())) + COMA
                + asignacion(TOTAL, ""+obj.getTotal())
                ;
    }
    
    @Override
    public Venta generarModelo(String[] datos) {
        Venta modelo = new Venta();
        
        modelo.setCodigo(Integer.parseInt(datos[0]));
        modelo.setTienda(datos[1]);
        modelo.setCliente(datos[2]);
        modelo.setFecha(datos[3]);
        modelo.setTotal(Double.parseDouble(datos[4]));
        
        return modelo;
    }

}
