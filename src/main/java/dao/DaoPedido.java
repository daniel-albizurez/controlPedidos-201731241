/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.ArrayList;
import modelo.Detalle;
import modelo.Pedido;

/**
 *
 * @author DANIEL
 */
public class DaoPedido extends Dao<Pedido> {
    
    public static final String TABLA = "pedido";

    public static final String CODIGO = "codigo";
    public static final String TIENDA_ORIGEN = "tienda_origen";
    public static final String TIENDA_DESTINO = "tienda_destino";
    public static final String FECHA_PEDIDO = "fecha_pedido";
    public static final String CLIENTE = "cliente";
    public static final String TOTAL = "total";
    public static final String ANTICIPO = "anticipo";
    public static final String FECHA_CANCELACION = "fecha_cancelacion";
    public static final String FECHA_EN_TIENDA = "fecha_en_tienda";
    public static final String ESTADO = "estado";

    public static final String ALL = CODIGO + COMA + TIENDA_ORIGEN + COMA
            + TIENDA_DESTINO + COMA + FECHA_PEDIDO + COMA + CLIENTE + COMA 
            + ANTICIPO + COMA + TOTAL + COMA + FECHA_CANCELACION + COMA
            + FECHA_EN_TIENDA + COMA + ESTADO;

    private DaoDetallePedido daoDetalle;
    
    public DaoPedido(Connection connection) {
        super(connection);
        daoDetalle = new DaoDetallePedido(connection);
    }

    public boolean agregar(Pedido pedido, ArrayList<Detalle> detalles){
        boolean exitoso = super.agregar(pedido, true);
        for (Detalle detalle : detalles) {
            exitoso &= daoDetalle.agregar(detalle, true);
        }
        if (!exitoso) {
            eliminar(pedido);
        }
        return exitoso;
    }
    
    @Override
    public String tabla() {
        return TABLA;
    }

    @Override
    public String primaryKey(Pedido obj) {
        return asignacion(CODIGO, "" +
                obj.getCodigo())
                ;
    }

    @Override
    public String camposObligatorios() {
        return ALL;
    }

    @Override
    public String todos() {
        return ALL;
    }

    @Override
    public String insertar(Pedido obj, boolean noObligatorios) {
        String valores =
                obj.getCodigo() + COMA
                + setTexto(obj.getTiendaOrigen()) + COMA
                + setTexto(obj.getTiendaDestino()) + COMA
                + setTexto(obj.getFechaPedido()) + COMA
                + valorPorDefecto(obj.getCliente()) + COMA
                + obj.getAnticipo() + COMA
                + obj.getTotal() + COMA
                + valorPorDefecto(obj.getFechaCancelacion()) + COMA
                + valorPorDefecto(obj.getFechaEnTienda()) + COMA
                + valorPorDefecto(obj.getEstado())
                ;
        return valores;
    }

    @Override
    public String setCamposYValores(Pedido obj) {
        return primaryKey(obj).replace(AND, COMA) + COMA
                + asignacion(TIENDA_ORIGEN, setTexto(obj.getTiendaOrigen())) + COMA
                + asignacion(TIENDA_DESTINO, setTexto(obj.getTiendaDestino())) + COMA
                + asignacion(FECHA_PEDIDO, setTexto(obj.getFechaPedido())) + COMA
                + asignacion(CLIENTE, valorPorDefecto(obj.getCliente())) + COMA
                + asignacion(ANTICIPO, obj.getAnticipo() + "") + COMA
                + asignacion(TOTAL, obj.getTotal() + "") + COMA
                + asignacion(FECHA_CANCELACION, valorPorDefecto(obj.getFechaCancelacion())) + COMA
                + asignacion(FECHA_EN_TIENDA, valorPorDefecto(obj.getFechaEnTienda())) + COMA
                + asignacion(ESTADO, valorPorDefecto(obj.getEstado()))
                ;
    }

    @Override
    public Pedido generarModelo(String[] datos) {
        Pedido modelo = new Pedido();
        
        modelo.setCodigo(Integer.parseInt(datos[0]));
        modelo.setTiendaOrigen(datos[1]);
        modelo.setTiendaDestino(datos[2]);
        modelo.setFechaPedido(datos[3]);
        modelo.setCliente(datos[4]);
        modelo.setAnticipo(Double.parseDouble(datos[5]));
        modelo.setTotal(Double.parseDouble(datos[6]));
        modelo.setFechaCancelacion(datos[7]);
        modelo.setFechaEnTienda(datos[8]);
        modelo.setEstado(datos[9]);
        
        return modelo;
    }
    
}
