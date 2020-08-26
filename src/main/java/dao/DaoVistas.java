/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author DANIEL
 */
public class DaoVistas extends Dao<Object> {

    private final String VISTA;
    private final String CAMPOS;

    public DaoVistas(String vista, String campos, Connection connection) {
        super(connection);
        this.VISTA = vista;
        this.CAMPOS = campos;
    }

    @Override
    public String tabla() {
        return VISTA;
    }

    @Override
    public String primaryKey(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String camposObligatorios() {
        return CAMPOS;
    }

    @Override
    public String todos() {
        return CAMPOS;
    }

    @Override
    public String insertar(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String setCamposYValores(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object generarModelo(String[] datos) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
