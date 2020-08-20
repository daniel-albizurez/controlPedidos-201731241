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

    public DaoClientes(Connection conection) {
        super(conection);
    }

    @Override
    public <T> boolean agregar(T agregar) {
        Cliente nuevo;
        nuevo = (Cliente) agregar;
        String valores = COMILLA + nuevo.getNit() + COMILLA + COMA + 
                COMILLA + nuevo.getNombre() + COMILLA + COMA +
                COMILLA + nuevo.getTelefono() + COMILLA
//                + COMA +
//                COMILLA + nuevo.getDpi() + COMILLA + COMA +
//                COMILLA + nuevo.getDireccion()+ COMILLA + COMA +
//                COMILLA + nuevo.getEmail()+ COMILLA + COMA +
//                nuevo.getCredito()
                ;
        return this.controladorDb.insert(TABLA, NOT_NULL, valores);
    }

    @Override
    public <T> boolean modificar(T modificar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> T seleccionar(String condicion) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <T> boolean eliminar(T eliminar) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String[]> buscarVarios(String campos, String condicion) {
        campos = ("*".equals(campos)) ? ALL:campos;
        return this.controladorDb.selectArray(TABLA, campos, condicion);
    }

}
