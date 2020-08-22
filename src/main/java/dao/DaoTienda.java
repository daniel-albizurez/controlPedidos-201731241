/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import modelo.Tienda;

/**
 *
 * @author DANIEL
 */
public class DaoTienda extends Dao<Tienda> {

    public final String TABLA = "tienda";
    
    //NOT NULL
    public final String CODIGO = "codigo";
    public final String NOMBRE = "nombre";
    public final String DIRECCION = "direccion";
    public final String TELEFONO1 = "telefono1";
    public final String NOT_NULL = CODIGO + COMA + NOMBRE + COMA + DIRECCION 
            + COMA + TELEFONO1;
    
    //NULL
    public final String TELEFONO2 = "telefono2";
    public final String EMAIL = "email";
    public final String HORARIO = "horario";
    public final String NULL = TELEFONO2 + COMA + EMAIL + COMA
            + HORARIO;
    
    public final String ALL = NOT_NULL + COMA + NULL;
    
    public DaoTienda(Connection connection) {
        super(connection);
    }
    
    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Tienda obj) {
        return asignacion(CODIGO, setTexto(obj.getCodigo()));
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
    public String insertar(Tienda obj, boolean noObligatorios) {
        String valores =
                setTexto(obj.getCodigo()) + COMA
                + setTexto(obj.getNombre()) + COMA
                + setTexto(obj.getDireccion()) + COMA
                + setTexto(obj.getTelefono1()) + COMA
                ;
        if (noObligatorios) {
            valores +=
                    setTexto(obj.getTelefono2()) + COMA
                    + setTexto(obj.getEmail()) + COMA
                    + setTexto(obj.getHorario())
                    ;
        }
        return valores;
    }

    @Override
    public String setCamposYValores(Tienda obj) {
        return primaryKey(obj) + COMA 
                + asignacion(NOMBRE, setTexto(obj.getNombre())) + COMA
                + asignacion(DIRECCION, setTexto(obj.getDireccion())) + COMA
                + asignacion(TELEFONO1, setTexto(obj.getTelefono1())) + COMA
                + asignacion(TELEFONO2, setTexto(obj.getTelefono2())) + COMA
                + asignacion(EMAIL, setTexto(obj.getEmail())) + COMA
                + asignacion(HORARIO, setTexto(obj.getHorario())) + COMA
                ;
    }

    @Override
    public Tienda generarModelo(String[] datos) {
        Tienda modelo = new Tienda();
        
        modelo.setCodigo(datos[0]);
        modelo.setNombre(datos[1]);
        modelo.setDireccion(datos[2]);
        modelo.setTelefono1(datos[3]);
        modelo.setTelefono2(datos[4]);
        modelo.setEmail(datos[5]);
        modelo.setHorario(datos[6]);
        
        return modelo;
    }
    
}