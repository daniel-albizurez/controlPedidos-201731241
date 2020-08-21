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
    
    public final String TABLA = "ubicacion";
    
    //NOT NULL
    public final String PRODUCTO = "producto";
    public final String TIENDA = "tienda";
    public final String NOT_NULL = PRODUCTO + COMA + TIENDA + COMA;
    //NULL
    public final String CANTIDAD = "cantidad";
    
    public final String ALL = NOT_NULL + COMA + CANTIDAD;

    public DaoUbicacion(Connection connection) {
        super(connection);
    }
    
    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Ubicacion obj) {
        return asignacion(PRODUCTO, setTexto(obj.getProducto())) +
                AND + asignacion(TIENDA, setTexto(obj.getTienda()));
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
        String valores = 
                obj.getProducto() + COMA
                + obj.getTienda()
                ;
        if (noObligatorios) {
            valores += COMA + obj.getCantidad();
        }
        return valores;
    }

    @Override
    public String setCamposYValores(Ubicacion obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + obj.getCantidad();
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
