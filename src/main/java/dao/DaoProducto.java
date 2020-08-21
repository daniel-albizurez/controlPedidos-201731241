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
public class DaoProducto extends Dao<Producto> {

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
    public String tabla() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String todos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Producto generarModelo(String[] datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String primaryKey(Producto obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String insertar(Producto obj, boolean noObligatorios) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String camposObligatorios() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setCamposYValores(Producto obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
