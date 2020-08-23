/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoProducto;
import dao.DaoTienda;
import dao.DaoUbicacion;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Producto;
import modelo.Tienda;
import modelo.Ubicacion;
import vista.VistaProducto;

/**
 *
 * @author DANIEL
 */
public class ControladorProductos extends Controlador<Producto, VistaProducto, VistaProducto> implements ActionListener, ChangeListener {

    //    private ReporteProductos reporte;
    private DaoUbicacion daoUbicacion;
    private DaoTienda daoTienda;
    private Tienda actual;
    private Ubicacion almacen;
    
    private final String NO_EXISTENCIAS = "No hay existencias para la tienda actual";

    public ControladorProductos(Connection connection, Tienda actual) {
        this.actual = actual;

        dao = new DaoProducto(connection);
//        daoTienda = new DaoTienda(connection);
        daoUbicacion = new DaoUbicacion(connection);

        this.vista = new VistaProducto();
        (agregar = this.vista.jBtnAgregar).addActionListener(this);
        (buscar = this.vista.jBtnBuscar).addActionListener(this);
        (eliminar = this.vista.jBtnEliminar).addActionListener(this);
        (modificar = this.vista.jBtnModificar).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        this.vista.setVisible(true);

        this.vista.jTxtPrecio.setText("0.00");
        this.vista.jSpinCantidad.addChangeListener(this);
        this.vista.jSpinGarantia.addChangeListener(this);
//        this.vista.jComboTiendas.addActionListener(this);
//        this.vista.jComboTiendas.removeAllItems();
//        for (String[] tienda : daoTienda.buscarVarios("codigo, nombre", "")) {
//            this.vista.jComboTiendas.addItem(tienda[0] + "," + tienda[1]);
//        }

        //Reporte
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == this.vista.jBtnAgregar) {
            mensaje = agregar(modelo = construirModelo());
            almacen = new Ubicacion(modelo.getCodigo(),
                    actual.getCodigo(),
                    (Integer) vista.jSpinCantidad.getValue());
            daoUbicacion.agregar(almacen, true);
        } else if (ev.getSource() == this.vista.jBtnBuscar) {
            String codigo = vista.jTxtCodigo.getText();
            if (!codigo.isBlank()) {
                modelo = dao.seleccionar("codigo", codigo);
                if (modelo != null) {
                    mostrarModelo(modelo);
                    almacen = daoUbicacion.seleccionar(new Ubicacion(codigo, actual.getCodigo(), 0)
                    );
                    if (almacen != null){
                        vista.jSpinCantidad.setValue(almacen.getCantidad());
                    } else {
                        mensaje = NO_EXISTENCIAS;
                        almacen = new Ubicacion(codigo, actual.getCodigo(), 0);
                    }
                    mensaje = "";
                    interfazModificar();

//                        this.vista.jComboTiendas.removeAllItems();
//                    for (String[] tienda : daoUbicacion.buscarVarios("tienda", daoUbicacion.asignacion("producto", daoUbicacion.setTexto(codigo)))) {
//                        this.vista.jComboTiendas.addItem(tienda[0]);
//                    }
                } else {
                    mensaje = String.format(NO_EXISTE, dao.tabla(), DaoProducto.CODIGO + " " + codigo);
                }
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoProducto.CODIGO);
            }
        } else if (ev.getSource() == vista.jBtnModificar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                almacen.setCantidad((Integer) vista.jSpinCantidad.getValue());
                mensaje = modificar(construirModelo());
                if(!daoUbicacion.modificar(almacen)){
                    daoUbicacion.agregar(almacen, true);
                }
            } else {
                mensaje = ERROR;
                vista.jTxtCodigo.setText(modelo.getCodigo());
            }
        } else if (ev.getSource() == vista.jBtnEliminar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                mensaje = eliminar(construirModelo());
            } else {
                mensaje = ERROR;
                vista.jTxtCodigo.setText(modelo.getCodigo());

            }
            /*} else if (ev.getSource() == this.vista.jComboTiendas && ev.equals(ev)) {
            if (vista.jComboTiendas.getSelectedItem() != null &&
                    modelo != null && 
                    modelo.getCodigo().equals(vista.jTxtCodigo.getText())
                    ) {
                vista.jSpinCantidad.setValue(Integer.parseInt(
                        daoUbicacion.buscarVarios("cantidad",
                                daoUbicacion.asignacion("tienda",
                                        daoUbicacion.setTexto(vista.jComboTiendas.getSelectedItem().toString()))
                        ).get(0)[0]));

            }*/
        } else {
            super.actionPerformed(ev);
        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }

    @Override
    public Producto construirModelo() {
        Producto p = new Producto();
        p.setCodigo(vista.jTxtCodigo.getText());
        p.setNombre(vista.jTxtNombre.getText());
        p.setFabricante(vista.jTxtFabricante.getText());
        p.setPrecio(Double.parseDouble(vista.jTxtPrecio.getText()));
        p.setDescripcion(vista.jTxtDescripcion.getText());
        p.setGarantia((Integer) vista.jSpinGarantia.getValue());
        return p;
    }

    @Override
    public void mostrarModelo(Producto modelo) {
        vista.jTxtNombre.setText(modelo.getNombre());
        vista.jTxtFabricante.setText(modelo.getFabricante());
        vista.jTxtPrecio.setText("" + modelo.getPrecio());
        vista.jTxtDescripcion.setText(modelo.getDescripcion());
        vista.jSpinGarantia.setValue(modelo.getGarantia());
    }

    @Override
    public void limpiar() {
        this.vista.jTxtCodigo.setText("");
        this.vista.jTxtNombre.setText("");
        this.vista.jTxtFabricante.setText("");
        this.vista.jTxtPrecio.setText("0.00");
        this.vista.jTxtDescripcion.setText("");
        this.vista.jSpinGarantia.setValue(0);
        this.vista.jSpinCantidad.setValue(0);

        this.vista.jTxtCodigo.setEditable(true);
        super.limpiar();
    }

    @Override
    public void stateChanged(ChangeEvent ev) {
        if (ev.getSource() == vista.jSpinGarantia
                || ev.getSource() == vista.jSpinCantidad) {
            JSpinner spinner = ((JSpinner) ev.getSource());
            int valor = Integer.parseInt(spinner.getValue().toString());
//            System.out.println(valor);
            if (valor < 0) {
                spinner.setValue(1);
//                try {
//                    spinner.commitEdit();
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                    spinner.setValue(spinner.getValue());
//                }
            }
        }
    }

    @Override
    public void interfazModificar() {
        vista.jTxtCodigo.setEditable(false);
        super.interfazModificar();
    }
}
