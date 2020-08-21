/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
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

    public DaoProducto(Connection connection) {
        super(connection);
    }
    
    
    
    @Override
    public <T> boolean agregar(T agregar, boolean noObligatorios) {
        String values;
        Producto nuevo = (Producto) agregar;
        values = nuevo.getCodigo() + COMA +
                COMILLA + nuevo.getNombre() + COMILLA + COMA +
                COMILLA + nuevo.getFabricante() + COMILLA + COMA +
                nuevo.getPrecio() + COMA +
                COMILLA + nuevo.getDescripcion() + COMILLA + COMA +
                nuevo.getGarantia()
                ;
        return true;
    }

    @Override
    public <T> boolean modificar(T modificar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public <T> T seleccionar(String condicion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> boolean eliminar(T eliminar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String[]> buscarVarios(String campos, String condicion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
