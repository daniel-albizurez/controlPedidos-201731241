/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoDetallePedido;
import dao.DaoPedido;
import dao.DaoTiempo;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Detalle;
import modelo.Pedido;
import modelo.Tienda;
import vista.VistaModificacionPedido;

/**
 *
 * @author DANIEL
 */
public class ControladorModificacionPedido extends Controlador<Pedido, VistaModificacionPedido, VistaModificacionPedido> {

    private final String[] COLUMNAS_INFO = {"Tienda Origen", "Cliente", "Anticipo", "Total"};
    private final String[] COLUMNAS_DETALLE = {"Producto", "Cantidad"};

    private Tienda actual;
    private ArrayList<Detalle> detalles = new ArrayList<>();
    private LocalDate current = LocalDate.now();

    private double totalAPagar = 0;

    private DaoTiempo daoTiempo;
    private DaoDetallePedido daoDetalle;

    private JTable tablaInfo;
    private JTable tablaDetalle;

    public ControladorModificacionPedido(Connection connection, Tienda actual) {
        this.actual = actual;

        dao = new DaoPedido(connection);
        daoTiempo = new DaoTiempo(connection);
        daoDetalle = new DaoDetallePedido(connection);

        this.vista = new VistaModificacionPedido();

        (buscar = this.vista.jBtnBuscar).addActionListener(this);
        (modificar = this.vista.jBtnModificar).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        this.vista.jBtnPagar.addActionListener(this);
        this.vista.jCmbEstado.addActionListener(this);

        this.vista.setVisible(true);

        tablaInfo = this.vista.jTblInfo;
        tablaDetalle = this.vista.jTblDetalle;

        ControladorTabla.agregarColumnas(tablaInfo, COLUMNAS_INFO);
        ControladorTabla.agregarColumnas(tablaDetalle, COLUMNAS_DETALLE);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == buscar) {
            String codigo = vista.jTxtCodigo.getText();
            if (!codigo.isBlank() && Integer.parseInt(codigo) > 0) {
                modelo = dao.seleccionar(
                        new String[]{DaoPedido.CODIGO, DaoPedido.TIENDA_DESTINO},
                        new String[]{codigo, actual.getCodigo()}
                );
                if (modelo != null) {
                    mostrarModelo(modelo);
                    interfazModificar();
                } else {
                    mensaje = String.format(NO_EXISTE, dao.tabla(), " ese codigo con la tienda actual como destino");
                }
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoPedido.CODIGO);
            }
        } else if (ev.getSource() == vista.jCmbEstado) {
            if (vista.jCmbEstado.getSelectedItem().equals("En tienda") && modelo != null) {
                if (current.compareTo(LocalDate.parse(modelo.getFechaEnTienda())) < 0) {
                    vista.jTxtFechaEnTienda.setText(current.toString());
                }
                vista.jTxtFechaPago.setText(current.toString());
                vista.jBtnPagar.setEnabled(true);
            } else {
                vista.jBtnPagar.setEnabled(false);
            }
        } else if (ev.getSource() == modificar) {
            modelo = construirModelo(modelo);
            if (dao.modificar(modelo)) {
                mensaje = String.format(MODIFICACION_EXITOSA, dao.tabla(), modelo.getCodigo());
                limpiar();
            } else {
                mensaje = String.format(MODIFICACION_FRACASADA, dao.tabla());
            }
        } else if (ev.getSource() == vista.jBtnPagar) {
            modelo = construirModelo(modelo);
            modelo.setEstado("Pagado");
            modelo.setFechaCancelacion(vista.jTxtFechaPago.getText());
            if (dao.modificar(modelo)) {
                mensaje = String.format(MODIFICACION_EXITOSA, dao.tabla(), modelo.getCodigo()).replace("La modificacion", "El Pago");
                limpiar();
            } else {
                mensaje = String.format(MODIFICACION_FRACASADA, dao.tabla());
            }
        } else if (ev.getSource() == cancelar) {
            limpiar();
        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
            mensaje = "";
        }
    }

    @Override
    public void limpiar() {
        vista.jTxtCodigo.setText("");
        vista.jTxtFechaEnTienda.setText(current.toString());
        vista.jTxtFechaPago.setText(current.toString());
        vista.jTxtCodigo.setEditable(true);
        modificar.setEnabled(false);
        vista.jBtnPagar.setEnabled(false);
        buscar.setEnabled(true);
        vista.jCmbEstado.removeItem("Pagado");
        vista.jCmbEstado.setSelectedIndex(0);
        vista.jCmbEstado.setEnabled(true);
        ControladorTabla.vaciar(tablaInfo);
        ControladorTabla.agregarColumnas(tablaInfo, COLUMNAS_INFO);
        ControladorTabla.vaciar(tablaDetalle);
        ControladorTabla.agregarColumnas(tablaDetalle, COLUMNAS_DETALLE);
        vista.jTxtTotalPagar.setText("0");
        modelo = null;

    }

    public Pedido construirModelo(Pedido p) {
        p.setFechaEnTienda(vista.jTxtFechaEnTienda.getText());
        p.setFechaCancelacion(vista.jTxtFechaPago.getText());
        p.setEstado(vista.jCmbEstado.getSelectedItem().toString());
        return p;
    }

    @Override
    public void mostrarModelo(Pedido modelo) {
        vista.jTxtFechaEnTienda.setText(modelo.getFechaEnTienda());
        vista.jTxtFechaPago.setText(modelo.getFechaCancelacion());
        if (modelo.getEstado().equals("Pagado")) {
            vista.jCmbEstado.addItem(modelo.getEstado());
            vista.jCmbEstado.setEnabled(false);
            vista.jBtnModificar.setEnabled(false);
        }
        vista.jCmbEstado.setSelectedItem(modelo.getEstado());
//        if (current.compareTo(LocalDate.parse(modelo.getFechaEnTienda()))>0) {
//            vista.jCmbEstado.setSelectedItem("Atrasado");
//        }
        vista.jTxtTotalPagar.setText("" + (modelo.getTotal() - modelo.getAnticipo()));
        ControladorTabla.agregarFila(tablaInfo,
                new String[]{modelo.getTiendaOrigen(), modelo.getCliente(), "" + modelo.getAnticipo(), "" + modelo.getTotal()});
        ControladorTabla.llenar(tablaDetalle,
                COLUMNAS_DETALLE,
                (daoDetalle).buscarVarios("*", dao.asignacion(DaoDetallePedido.PEDIDO, modelo.getCodigo() + "")));
    }

    @Override
    public void interfazModificar() {
        vista.jTxtCodigo.setEditable(false);
        modificar.setEnabled(true);
        buscar.setEnabled(false);
    }

    @Override
    public Pedido construirModelo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
