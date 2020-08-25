/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import dao.Dao;
import dao.DaoClientes;
import dao.DaoDetalleVenta;
import dao.DaoProducto;
import dao.DaoUbicacion;
import dao.DaoVenta;
import java.awt.event.ActionEvent;
import java.lang.reflect.Array;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Cliente;
import modelo.Detalle;
import modelo.Producto;
import modelo.Tienda;
import modelo.Ubicacion;
import modelo.Venta;
import vista.VistaVenta;

/**
 *
 * @author DANIEL
 */
public class ControladorVenta extends Controlador<Venta, VistaVenta, VistaVenta> implements ChangeListener {

    private Tienda actual;
    private Detalle detalle;
    private ArrayList<Detalle> detalles = new ArrayList<>();
    private LocalDate current = LocalDate.now();
    private Cliente cliente;
    private Ubicacion ubicacion;
    private ArrayList<Ubicacion> inventarios = new ArrayList<>();
    private double total = 0;

    private DaoUbicacion daoUbicacion;
    private DaoClientes daoClientes;
    private DaoProducto daoProducto;

    private ControladorClientes controladorCliente;

    private JTable tablaDetalle;

    public ControladorVenta(Connection connection, Tienda actual) {
        this.actual = actual;

        dao = new DaoVenta(connection);
        daoUbicacion = new DaoUbicacion(connection);
        daoClientes = new DaoClientes(connection);
        daoProducto = new DaoProducto(connection);

        vista = new VistaVenta();
        (agregar = this.vista.jBtnRegistrar).addActionListener(this);
        (buscar = this.vista.jBtnBuscarCliente).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        tablaDetalle = vista.jTblProductos;
        vista.jBtnAgregarProducto.addActionListener(this);
        vista.jCmbProductos.addActionListener(this);
        vista.jSpinCantidad.addChangeListener(this);
        vista.setVisible(true);

        productos();

        this.vista.jTxtFecha.setText(current.toString());

        String[] columnas = new String[]{"Codigo", "Nombre", "Precio"};
        ControladorTabla.agregarColumnas(tablaDetalle, columnas);

        numeroVenta();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == buscar) {
            String nit = vista.jTxtCliente.getText();
            if (!nit.isBlank()) {
                cliente = daoClientes.seleccionar(DaoClientes.NIT, nit);
                if (cliente != null) {
                    vista.jLblNombreCliente.setText(cliente.getNombre());
                    mensaje = "";
                    interfazModificar();
                } else {
                    if (JOptionPane.showConfirmDialog(vista, "Desea agregarlo", "Agregar cliente", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                        controladorCliente = new ControladorClientes(dao.getConnection());
                    } else {
                        limpiar();
                        mensaje = String.format(NO_EXISTE, daoClientes.tabla(), DaoClientes.NIT + nit);
                    }
                }
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoClientes.NIT);
            }
        } else if (ev.getSource() == cancelar) {
            limpiar();
        } else if (ev.getSource() == vista.jCmbProductos) {

            if (vista.jCmbProductos.getSelectedItem() != null) {
                Producto producto = daoProducto.seleccionar(DaoProducto.CODIGO, vista.jCmbProductos.getSelectedItem().toString());
                vista.jLblPrecio.setText("" + producto.getPrecio());
            }
        } else if (ev.getSource() == vista.jBtnAgregarProducto) {
            Producto producto = daoProducto.seleccionar(DaoProducto.CODIGO, vista.jCmbProductos.getSelectedItem().toString());
            int cantidad = (Integer) vista.jSpinCantidad.getValue();
            double valor = producto.getPrecio() * cantidad;

            ControladorTabla.agregarFila(tablaDetalle, new String[]{producto.getCodigo(), producto.getNombre(), "" + valor});

            total += valor;
            vista.jTxtTotal.setText(total + "");
            construirUbicacion();
            ubicacion.setCantidad(ubicacion.getCantidad() - cantidad);
            inventarios.add(ubicacion);
            detalles.add(new Detalle(Integer.parseInt(vista.jTxtCodigo.getText()), producto.getCodigo(), cantidad));
            vista.jCmbProductos.removeItem(producto.getCodigo());
        } else if (ev.getSource() == agregar) {
            modelo = construirModelo();
//            mensaje = agregar(modelo);
            if (((DaoVenta) dao).agregar(modelo, detalles)) {
                for (Ubicacion inventario : inventarios) {
                    daoUbicacion.modificar(ubicacion);
                }
                limpiar();
                numeroVenta();
            }

        }
        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }

    }

    @Override
    public Venta construirModelo() {
        Venta v = new Venta();
        v.setCodigo(Integer.parseInt(vista.jTxtCodigo.getText()));
        v.setTienda(actual.getCodigo());
        v.setCliente(vista.jTxtCliente.getText());
        v.setFecha(vista.jTxtFecha.getText());
        v.setTotal(Double.parseDouble(vista.jTxtTotal.getText()));
        return v;
    }

    @Override
    public void mostrarModelo(Venta modelo) {
        vista.jTxtCodigo.setText("" + modelo.getCodigo());
        vista.jTxtCliente.setText(modelo.getCliente());
        vista.jTxtFecha.setText(modelo.getFecha());
        vista.jTxtTotal.setText("" + modelo.getTotal());
    }

    @Override
    public void interfazModificar() {
        agregar.setEnabled(true);
        cancelar.setEnabled(true);
        buscar.setEnabled(false);
        vista.jTxtCliente.setEditable(false);
    }

    @Override
    public void limpiar() {
        vista.jLblNombreCliente.setText("Nombre");
        vista.jLblPrecio.setText("Precio");
        agregar.setEnabled(false);
        cancelar.setEnabled(false);
        buscar.setEnabled(true);
        vista.jTxtCliente.setEditable(true);
        ControladorTabla.vaciar(tablaDetalle);
        ControladorTabla.agregarColumnas(tablaDetalle, new String[]{"Codigo", "Nombre", "Precio"});
        productos();
        vista.jCmbProductos.setSelectedIndex(0);
        total = 0;
        vista.jTxtTotal.setText(total + "");
        detalles.clear();
    }

    public void numeroVenta() {
        List<Integer> codigos = new ArrayList<>();

        for (String[] venta : dao.buscarVarios(DaoVenta.CODIGO, "")) {
            codigos.add(Integer.parseInt(venta[0]));
        }

        Collections.sort(codigos);
        int numeroVenta = 1;
        if (codigos.size() > 0) {
            numeroVenta = codigos.get(codigos.size() - 1) + 1;
        }
        vista.jTxtCodigo.setText(numeroVenta + "");
    }

    public void productos() {
        vista.jCmbProductos.removeAllItems();
        ArrayList<String[]> codigosProductos = daoUbicacion.buscarVarios(
                DaoUbicacion.PRODUCTO, dao.asignacion(DaoUbicacion.TIENDA, actual.getCodigo())
                + Dao.AND + DaoUbicacion.CANTIDAD + ">0"
        );
        ArrayList<String> productos = new ArrayList<>();
        for (String[] codigoProducto : codigosProductos) {
            productos.add(
                    daoProducto.buscarVarios(
                            DaoProducto.CODIGO,
                            daoProducto.asignacion(DaoProducto.CODIGO, daoProducto.setTexto(codigoProducto[0]))
                    ).get(0)[0]
            );
        }
        for (String producto : productos) {
            vista.jCmbProductos.addItem(producto);
        }
    }

    @Override
    public void stateChanged(ChangeEvent ev) {
        if (ev.getSource() == vista.jSpinCantidad) {
            int valor = Integer.parseInt(vista.jSpinCantidad.getValue().toString());
            construirUbicacion();
            int limite = ubicacion.getCantidad();

            if (valor <= 0) {
                vista.jSpinCantidad.setValue(1);
            } else if (valor > limite) {
                vista.jSpinCantidad.setValue(limite);
            }
        }
    }

    public void construirUbicacion() {
        ubicacion = new Ubicacion(
                vista.jCmbProductos.getSelectedItem().toString(),
                actual.getCodigo(),
                0
        );
        ubicacion = daoUbicacion.seleccionar(ubicacion);

    }
}
