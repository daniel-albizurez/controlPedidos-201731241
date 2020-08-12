/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import modelo.Producto;

/**
 *
 * @author DANIEL
 */
public class DaoProducto extends Dao {

    private final String TABLA = "producto";
    private final String CODIGO = "codigo";
    private final String NOMBRE = "nombre";
    private final String FABRICANTE = "fabricante";
    private final String PRECIO = "precio";
    private final String DESCRIPCION = "descripcion";
    private final String GARANTIA = "garantia";
    
    
    @Override
    public <T> boolean agregar(T agregar) {
        String values;
        Producto nuevo = (Producto) agregar;
        values = nuevo.getCodigo() + COMA +
                COMILLA + nuevo.getNombre() + COMILLA + COMA +
                COMILLA + nuevo.getFabricante() + COMILLA + COMA +
                nuevo.getPrecio() + COMA +
                COMILLA + nuevo.getDescripcion() + COMILLA + COMA +
                nuevo.getGarantia()
                ;
        return ControladorDB.insert(TABLA, values);
    }

    
}
