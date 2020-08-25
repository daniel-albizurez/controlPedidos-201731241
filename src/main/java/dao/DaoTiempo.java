/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import modelo.Tiempo;

/**
 *
 * @author DANIEL
 */
public class DaoTiempo extends Dao<Tiempo>{
    
    public static final String TABLA = "tiempo";
    
    public static final String TIENDA1 = "tienda1";
    public static final String TIENDA2 = "tienda2";
    public static final String DIAS = "dias";
    
    public static final String ALL = TIENDA1 + COMA + TIENDA2 + COMA + DIAS;

    public DaoTiempo(Connection connection) {
        super(connection);
    }

    public Tiempo seleccionar(Tiempo t){
        String condicion = primaryKey(t);
        try {
            String[] datos = buscarVarios(todos(), condicion).get(0);
            return generarModelo(datos);
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Tiempo obj) {
        return asignacion(TIENDA1, setTexto(obj.getTienda1()))
                + AND + asignacion(TIENDA2, setTexto(obj.getTienda2()));
    }

    @Override
    public String camposObligatorios() {
        return todos();
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String insertar(Tiempo obj) {
        String valores =
                setTexto(obj.getTienda1()) + COMA
                + setTexto(obj.getTienda2()) + COMA
                + valorPorDefecto(obj.getDias())
                ;
        return valores;
    }

    @Override
    public String setCamposYValores(Tiempo obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + asignacion(DIAS, (obj.getDias() <= 0)
                    ? DEFAULT : String.valueOf(obj.getDias())
                )
                ;
    }

    @Override
    public Tiempo generarModelo(String[] datos) {
        return new Tiempo(
                datos[0],
                datos[1],
                Integer.parseInt(datos[2])
        );
    }
    
}
