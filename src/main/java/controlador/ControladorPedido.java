/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.Dao;
import dao.DaoClientes;
import dao.DaoPedido;
import dao.DaoProducto;
import dao.DaoTienda;
import dao.DaoUbicacion;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Cliente;
import modelo.Detalle;
import modelo.Pedido;
import modelo.Producto;
import modelo.Tienda;
import modelo.Ubicacion;
import reportes.ReporteExistencias;
import vista.VistaPedidos;

/**
 *
 * @author DANIEL
 */
public class ControladorPedido extends Controlador<Pedido, VistaPedidos, ReporteExistencias> implements ChangeListener {

    private Tienda actual;
    private Detalle detalle;
    private ArrayList<Detalle> detalles = new ArrayList<>();
    private LocalDate current = LocalDate.now();
    private Cliente cliente;
    private Ubicacion ubicacion;
    private ArrayList<Ubicacion> inventarios = new ArrayList<>();
    
    private double total = 0;
    private double anticipo = 0;

    private DaoUbicacion daoUbicacion;
    private DaoClientes daoClientes;
    private DaoProducto daoProducto;

    private ControladorClientes controladorCliente;

    private JTable tablaDetalle;

    public ControladorPedido(Connection connection, Tienda actual) {
        this.actual = actual;

        dao = new DaoPedido(connection);
        daoUbicacion = new DaoUbicacion(connection);
        daoClientes = new DaoClientes(connection);
        daoProducto = new DaoProducto(connection);

        vista = new VistaPedidos();
        (agregar = this.vista.jBtnRegistrar).addActionListener(this);
        (buscar = this.vista.jBtnBuscarCliente).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        vista.jBtnSeleccionarTienda.addActionListener(this);
        vista.jBtnAgregarProducto.addActionListener(this);
        vista.jCmbProductos.addActionListener(this);
        vista.jSpinCantidad.addChangeListener(this);
        vista.setVisible(true);
        tablaDetalle = vista.jTblDetalle;

        ControladorTabla.agregarColumnas(tablaDetalle, new String[]{"Codigo", "Nombre", "Precio"});

        reporte = new ReporteExistencias();
        tablaReporte = reporte.jTblExistencias;
        mostrarExistencias();

        this.vista.jTxtFecha.setText(current.toString());

        numeroPedido();
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == vista.jBtnSeleccionarTienda) {
            String codigoTienda = vista.jTxtTiendaOrigen.getText();
            if (!codigoTienda.isBlank()) {
                productos(codigoTienda);
                if (!vista.jTxtCliente.getText().isBlank()) {
                    interfazModificar();
                }
                mensaje = "";
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoTienda.CODIGO);
            }

        } else if (ev.getSource() == buscar) {
            String nit = vista.jTxtCliente.getText();
            if (!nit.isBlank()) {
                cliente = daoClientes.seleccionar(DaoClientes.NIT, nit);
                if (cliente != null) {
                    vista.jLblNombreCliente.setText(cliente.getNombre());
                    mensaje = "";
                    if (!vista.jTxtTiendaOrigen.getText().isBlank()) {
                        interfazModificar();
                    }
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
            anticipo = total*25/100;
            vista.jTxtTotal.setText(total + "");
            vista.jTxtAnticipo.setText(anticipo + "");
            construirUbicacion();
            ubicacion.setCantidad(ubicacion.getCantidad() - cantidad);
            inventarios.add(ubicacion);
            detalles.add(new Detalle(Integer.parseInt(vista.jTxtCodigo.getText()), producto.getCodigo(), cantidad));
            vista.jCmbProductos.removeItem(producto.getCodigo());

        } else if (ev.getSource() == agregar) {
            modelo = construirModelo();

            if (((DaoPedido)dao).agregar(modelo, detalles)) {
                for (Ubicacion inventario : inventarios) {
                    daoUbicacion.modificar(ubicacion);
                }
                limpiar();
                numeroPedido();
            }
        }
    }

    public void numeroPedido() {
        List<Integer> codigos = new ArrayList<>();

        for (String[] pedido : dao.buscarVarios(DaoPedido.CODIGO, "")) {
            codigos.add(Integer.parseInt(pedido[0]));
        }

        Collections.sort(codigos);
        int numeroVenta = 1;
        if (codigos.size() > 0) {
            numeroVenta = codigos.get(codigos.size() - 1) + 1;
        }
        vista.jTxtCodigo.setText(numeroVenta + "");
    }

    public void productos(String codigoTienda) {
        vista.jCmbProductos.removeAllItems();
        ArrayList<String[]> codigosProductos = daoUbicacion.buscarVarios(
                DaoUbicacion.PRODUCTO, dao.asignacion(DaoUbicacion.TIENDA, codigoTienda)
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
    public Pedido construirModelo() {
        Pedido p = new Pedido();

        p.setCodigo(Integer.parseInt(vista.jTxtCodigo.getText()));
        p.setTiendaOrigen(vista.jTxtTiendaOrigen.getText());
        p.setTiendaDestino(actual.getCodigo());
        p.setFechaPedido(vista.jTxtFecha.getText());
        p.setCliente(vista.jTxtCliente.getText());
        p.setTotal(Double.parseDouble(vista.jTxtTotal.getText()));
        p.setAnticipo(Double.parseDouble(vista.jTxtAnticipo.getText()));
        p.setFechaCancelacion("");
        p.setFechaEnTienda("");
        p.setEstado("");

        return p;
    }

    @Override
    public void mostrarModelo(Pedido modelo) {
        vista.jTxtCodigo.setText("" + modelo.getCodigo());
        vista.jTxtTiendaOrigen.setText(modelo.getTiendaOrigen());
        vista.jTxtFecha.setText(modelo.getFechaPedido());
        vista.jTxtCliente.setText(modelo.getCliente());
        vista.jTxtTotal.setText("" + modelo.getTotal());
        vista.jTxtAnticipo.setText("" + modelo.getAnticipo());
    }

    @Override
    public void interfazModificar() {
        agregar.setEnabled(true);
        cancelar.setEnabled(true);
        buscar.setEnabled(false);
        vista.jBtnSeleccionarTienda.setEnabled(false);
        vista.jTxtCliente.setEditable(false);
        vista.jTxtTiendaOrigen.setEditable(false);
    }

    @Override
    public void limpiar() {
        vista.jLblNombreCliente.setText("Nombre");
        vista.jLblPrecio.setText("Precio");
        agregar.setEnabled(false);
        cancelar.setEnabled(false);
        buscar.setEnabled(true);
        vista.jBtnSeleccionarTienda.setEnabled(true);
        vista.jTxtTiendaOrigen.setEditable(true);
        vista.jTxtTiendaOrigen.setText("");
        vista.jTxtCliente.setEditable(true);
        vista.jTxtCliente.setText("");
        mostrarExistencias();
        vista.jCmbProductos.removeAllItems();
        total = 0;
        vista.jTxtTotal.setText(total + "");
        ControladorTabla.vaciar(tablaDetalle);
        ControladorTabla.agregarColumnas(tablaDetalle, new String[]{"Codigo", "Nombre", "Precio"});
        detalles.clear();
    }

    public void mostrarExistencias() {
        ArrayList<String[]> filas = daoUbicacion.buscarVarios(
                "*",
                DaoUbicacion.TIENDA + "<>" + daoUbicacion.setTexto(actual.getCodigo())
        );

        for (int i = 0; i < filas.size(); i++) {
            String[] datos = filas.get(i);
            String nombre = daoProducto.seleccionar(DaoProducto.CODIGO, datos[0]).getNombre();
            filas.set(i, new String[]{datos[1], datos[0], nombre, datos[2]});
        }

        ControladorTabla.llenar(tablaReporte,
                new String[]{"Tienda", "Producto", "Nombre", "Cantidad"},
                filas);
        reporte.setVisible(true);

    }

    @Override
    public void stateChanged(ChangeEvent ev) {
        if (ev.getSource() == vista.jSpinCantidad) {
            int valor = Integer.parseInt(vista.jSpinCantidad.getValue().toString());
            construirUbicacion();
            if (ubicacion != null) {
                int limite = ubicacion.getCantidad();

                if (valor <= 0) {
                    vista.jSpinCantidad.setValue(1);
                } else if (valor > limite) {
                    vista.jSpinCantidad.setValue(limite);
                }

            }
        }
    }

    public void construirUbicacion() {
        String producto = "";
        String tiendaOrigen = vista.jTxtTiendaOrigen.getText();
        if (vista.jCmbProductos.getSelectedItem() != null
                && !tiendaOrigen.isBlank()) {
            producto = vista.jCmbProductos.getSelectedItem().toString();
            ubicacion = new Ubicacion(
                    producto,
                    tiendaOrigen,
                    0
            );
            ubicacion = daoUbicacion.seleccionar(ubicacion);
        } else {
            ubicacion = null;
        }

    }

}
