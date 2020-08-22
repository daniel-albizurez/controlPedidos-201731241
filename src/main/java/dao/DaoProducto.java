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

    private final String TABLA = "producto";
    //NOT NULL
    private final String CODIGO = "codigo";
    private final String NOMBRE = "nombre";
    private final String FABRICANTE = "fabricante";
    private final String PRECIO = "precio";
    private final String NOT_NULL = CODIGO + COMA + NOMBRE + COMA +
            FABRICANTE+ COMA + PRECIO;
    //NULL
    private final String DESCRIPCION = "descripcion";
    private final String GARANTIA = "garantia";
    private final String NULL = DESCRIPCION + COMA + GARANTIA;

    private final String ALL = NOT_NULL + COMA + NULL;
    
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
    public String insertar(Producto obj, boolean noObligatorios) {
        String valores =
                setTexto(obj.getCodigo()) + COMA
                + setTexto(obj.getNombre()) + COMA
                + setTexto(obj.getFabricante()) + COMA
                + obj.getPrecio()
                ;
        if (noObligatorios) {
            valores +=
                    setTexto(obj.getDescripcion()) + COMA
                    + obj.getGarantia()
                    ;
        }
        return valores;
    }

    @Override
    public String setCamposYValores(Producto obj) {
        return primaryKey(obj) + COMA
                + asignacion(NOMBRE, setTexto(obj.getNombre())) + COMA
                + asignacion(FABRICANTE, setTexto(obj.getFabricante())) + COMA
                + asignacion(PRECIO, setTexto(""+obj.getPrecio())) + COMA
                + asignacion(DESCRIPCION, setTexto(obj.getDescripcion())) + COMA
                + asignacion(GARANTIA, setTexto(""+obj.getGarantia()))                ;
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
