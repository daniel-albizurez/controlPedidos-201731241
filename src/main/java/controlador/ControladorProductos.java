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
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Producto;
import vista.VistaProducto;

/**
 *
 * @author DANIEL
 */
public class ControladorProductos implements ActionListener, ChangeListener {

    private VistaProducto vista;
//    private ReporteProductos reporte;
    private DaoProducto daoProducto;
    private DaoUbicacion daoUbicacion;
    private DaoTienda daoTienda;
    private Producto modelo;

    public ControladorProductos(Connection connection) {
        daoProducto = new DaoProducto(connection);
        daoTienda = new DaoTienda(connection);
        daoUbicacion = new DaoUbicacion(connection);

        this.vista = new VistaProducto();
        this.vista.jBtnAgregar.addActionListener(this);
        this.vista.jBtnBuscar.addActionListener(this);
        this.vista.jBtnEliminar.addActionListener(this);
        this.vista.jBtnModificar.addActionListener(this);
        this.vista.setVisible(true);

        this.vista.jTxtPrecio.setText("0.00");
        this.vista.jSpinCantidad.addChangeListener(this);
        this.vista.jSpinGarantia.addChangeListener(this);
        this.vista.jComboTiendas.addActionListener(this);
        this.vista.jComboTiendas.removeAllItems();
        for (String[] tienda : daoTienda.buscarVarios("codigo, nombre", "")) {
            this.vista.jComboTiendas.addItem(tienda[0] + "," + tienda[1]);
        }

        //Reporte
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        String mensaje = "";
        if (ev.getSource() == this.vista.jBtnAgregar) {
            modelo = construirModelo();

            mensaje = (daoProducto.agregar(modelo, false))
                    ? String.format("El producto %S fue exitosamente ingresado", modelo.getCodigo())
                    : String.format("El producto con codigo %s ya existe", modelo.getCodigo());
        } else if (ev.getSource() == this.vista.jBtnBuscar) {
            String codigo = vista.jTxtCodigo.getText();
            if (!codigo.isBlank()) {
                modelo = daoProducto.seleccionar("codigo", codigo);

                if (modelo != null) {
                    mostrarModelo(modelo);
                    mensaje = "";

                    vista.jTxtCodigo.setEditable(false);

                    vista.jBtnAgregar.setEnabled(false);
                    vista.jBtnModificar.setEnabled(true);
                    vista.jBtnEliminar.setEnabled(true);

                        this.vista.jComboTiendas.removeAllItems();
                    
                    for (String[] tienda : daoUbicacion.buscarVarios("tienda", daoUbicacion.asignacion("producto", daoUbicacion.setTexto(codigo)))) {
                        this.vista.jComboTiendas.addItem(tienda[0]);
                    }

                } else {
                    mensaje = "No existe un producto con el codigo " + codigo;
                }
            } else {
                mensaje = "Por favor ingrese un codigo";
            }
        } else if (ev.getSource() == vista.jBtnModificar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                modelo = construirModelo();
                if (daoProducto.modificar(modelo)) {
                    mensaje = "La modificación del producto " + modelo.getCodigo()
                            + " ha sido exitosa";
                    limpiar();
                } else {
                    mensaje = "No se ha podido modificar el producto";
                }
            } else {
                mensaje = "Ha ocurrido un error, por favor vuelva a intentarlo";
                vista.jTxtCodigo.setText(modelo.getCodigo());

            }
        } else if (ev.getSource() == vista.jBtnEliminar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                modelo = construirModelo();
                if (daoProducto.eliminar(modelo)) {
                    mensaje = "Producto eliminado exitosamente";
                    limpiar();
                } else {
                    mensaje = "No se ha podido eliminar el producto";
                }
            } else {
                mensaje = "Ha ocurrido un error, por favor vuelva a intentarlo";
                vista.jTxtCodigo.setText(modelo.getCodigo());

            }
        } else if (ev.getSource() == this.vista.jComboTiendas && ev.equals(ev)) {
            //TODO: Select join TIENDA segun PRODUCTO
            if (vista.jComboTiendas.getSelectedItem() != null &&
                    modelo != null && 
                    modelo.getCodigo().equals(vista.jTxtCodigo.getText())
                    ) {
                vista.jSpinCantidad.setValue(Integer.parseInt(
                        daoUbicacion.buscarVarios("cantidad",
                                daoUbicacion.asignacion("tienda",
                                        daoUbicacion.setTexto(vista.jComboTiendas.getSelectedItem().toString()))
                        ).get(0)[0]));

            }
        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }

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

    public void mostrarModelo(Producto modelo) {
        vista.jTxtNombre.setText(modelo.getNombre());
        vista.jTxtFabricante.setText(modelo.getFabricante());
        vista.jTxtPrecio.setText("" + modelo.getPrecio());
        vista.jTxtDescripcion.setText(modelo.getDescripcion());
        vista.jSpinGarantia.setValue(modelo.getGarantia());
    }

    public void limpiar() {
        this.vista.jTxtCodigo.setText("");
        this.vista.jTxtNombre.setText("");
        this.vista.jTxtFabricante.setText("");
        this.vista.jTxtPrecio.setText("0.00");
        this.vista.jTxtDescripcion.setText("");
        this.vista.jSpinGarantia.setValue(0);
        this.vista.jSpinCantidad.setValue(0);

        this.vista.jTxtCodigo.setEditable(true);
    }

    @Override
    public void stateChanged(ChangeEvent ev) {
        if (ev.getSource() == vista.jSpinGarantia
                || ev.getSource() == vista.jSpinCantidad) {
            JSpinner spinner = ((JSpinner) ev.getSource());
            int valor = Integer.parseInt(spinner.getValue().toString());
            if (valor < 0) {
                spinner.setValue(1);
            }
        }
    }
}
