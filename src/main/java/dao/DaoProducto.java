/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import modelo.Producto;

/**
 *
 * @author DANIEL
 */
public class DaoProducto extends Dao<Producto> {

    private static final String TABLA = "producto";
    //NOT NULL
    public static final String CODIGO = "codigo";
    public static final String NOMBRE = "nombre";
    public static final String FABRICANTE = "fabricante";
    public static final String PRECIO = "precio";
    public static final String NOT_NULL = CODIGO + COMA + NOMBRE + COMA
            + FABRICANTE + COMA + PRECIO;
    //NULL
    public static final String DESCRIPCION = "descripcion";
    public static final String GARANTIA = "garantia";
    public static final String NULL = DESCRIPCION + COMA + GARANTIA;

    private static final String ALL = NOT_NULL + COMA + NULL;

    public DaoProducto(Connection connection) {
        super(connection);
    }

    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Producto obj) {
        return asignacion(CODIGO, setTexto(obj.getCodigo()));
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String camposObligatorios() {
        return NOT_NULL;
    }

    @Override
    public String insertar(Producto obj) {
        String valores
                = setTexto(obj.getCodigo()) + COMA
                + setTexto(obj.getNombre()) + COMA
                + setTexto(obj.getFabricante()) + COMA
                + obj.getPrecio() + COMA
                + valorPorDefecto(obj.getDescripcion()) + COMA
                + valorPorDefecto(obj.getGarantia());
    return valores ;
}

@Override
public String setCamposYValores(Producto obj) {
        return primaryKey(obj) + COMA
                + asignacion(NOMBRE, setTexto(obj.getNombre())) + COMA
                + asignacion(FABRICANTE, setTexto(obj.getFabricante())) + COMA
                + asignacion(PRECIO, setTexto(""+obj.getPrecio())) + COMA
                + asignacion(DESCRIPCION, valorPorDefecto(obj.getDescripcion())) + COMA
                + asignacion(GARANTIA, valorPorDefecto(obj.getGarantia()))                ;
    }

    @Override
public Producto generarModelo(String[] datos) {
        Producto modelo = new Producto();
        modelo.setCodigo(datos[0]);
        modelo.setNombre(datos[1]);
        modelo.setFabricante(datos[2]);
        modelo.setPrecio(Double.parseDouble(datos[3]));
        modelo.setDescripcion(datos[4]);
        modelo.setGarantia(Integer.parseInt(datos[5]));
        
        return modelo;
    }

    
}
