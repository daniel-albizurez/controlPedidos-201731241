/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTiempo;
import dao.DaoTienda;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import modelo.Tiempo;
import modelo.Tienda;
import vista.VistaTiempo;

/**
 *
 * @author DANIEL
 */
public class ControladorTiempo extends Controlador<Tiempo, VistaTiempo, VistaTiempo> implements ChangeListener{

    private JComboBox comboTienda1;
    private JComboBox comboTienda2;
    private DaoTienda daoTienda;
    
    private final String DIFERENTES_TIENDAS = "Por favor seleccione tiendas diferentes";
    
    public ControladorTiempo(Connection connection) {
        dao = new DaoTiempo(connection);
        daoTienda = new DaoTienda(connection);
        
        vista = new VistaTiempo();
        (agregar = vista.jBtnAgregar).addActionListener(this);
        (buscar = vista.jBtnBuscar).addActionListener(this);
        (modificar = vista.jBtnModificar).addActionListener(this);
        (eliminar = vista.jBtnEliminar).addActionListener(this);
        (cancelar = vista.jBtnCancelar).addActionListener(this);
        vista.setVisible(true);
        
        (comboTienda1 = vista.jComboTienda1).addActionListener(this);
        (comboTienda2 = vista.jComboTienda2).addActionListener(this);
        
        comboTienda1.removeAllItems();
        comboTienda2.removeAllItems();
        
        for (String[] tienda : daoTienda.buscarVarios("codigo", "")) {
            comboTienda1.addItem(tienda[0]);
            comboTienda2.addItem(tienda[0]);
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev){
        if (ev.getSource() == agregar) {
            modelo = construirModelo();
            mensaje = (!modelo.getTienda1().equals(modelo.getTienda2())) ?
                    agregar(modelo) : DIFERENTES_TIENDAS;
        } else if (ev.getSource() == buscar) {
            modelo = construirModelo();
            if (!modelo.getTienda1().equals(modelo.getTienda2())) {
                modelo = ((DaoTiempo) dao).seleccionar(modelo);
                if (modelo != null) {
                    mostrarModelo(modelo);
                    mensaje = "";
                    interfazModificar();
                } else {
                    mensaje = String.format(NO_EXISTE, dao.tabla(), " para las tiendas seleccionadas");
                }
            } else {
                mensaje = DIFERENTES_TIENDAS;
            }
        } else if (ev.getSource() == modificar) {
            if (modelo.getTienda1().equals(comboTienda1.getSelectedItem())
                    && modelo.getTienda2().equals(comboTienda2.getSelectedItem())
                    ) {
                mensaje = modificar(construirModelo());
            } else {
                mensaje = ERROR;
                comboTienda1.setSelectedItem(modelo.getTienda1());
                comboTienda2.setSelectedItem(modelo.getTienda2());
            }
        } else if (ev.getSource() == eliminar) {
            if (modelo.getTienda1().equals(comboTienda1.getSelectedItem())
                    && modelo.getTienda2().equals(comboTienda2.getSelectedItem())
                    ) {
                mensaje = eliminar(construirModelo());
            } else {
                mensaje = ERROR;
                comboTienda1.setSelectedItem(modelo.getTienda1());
                comboTienda2.setSelectedItem(modelo.getTienda2());
            }
        } else if (ev.getSource() instanceof JComboBox ) {
            JComboBox combo = (JComboBox) ev.getSource();
            JLabel label = (combo.equals(comboTienda1)) ?
                    vista.jLblTienda1 : vista.jLblTienda2;
            Tienda t;
            if ( combo.getSelectedItem() != null) {
                t = daoTienda.seleccionar(DaoTienda.CODIGO, combo.getSelectedItem().toString());
                if (t != null){
                    label.setText(t.getNombre());
                    mensaje = "";
                } else {
                    mensaje = ERROR;
                }
            }
        } else {
            super.actionPerformed(ev);
        }
    if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }
    
    @Override
    public Tiempo construirModelo() {
        Tiempo t = new Tiempo();
        t.setTienda1(comboTienda1.getSelectedItem().toString());
        t.setTienda2(comboTienda2.getSelectedItem().toString());
        t.setDias((Integer)vista.jSpinDias.getValue());
        return t;
    }

    @Override
    public void mostrarModelo(Tiempo modelo) {
        comboTienda1.setSelectedItem(modelo);
        comboTienda2.setSelectedItem(modelo);
        vista.jSpinDias.setValue(modelo.getDias());
    }

    @Override
    public void interfazModificar(){
        comboTienda1.setEnabled(false);
        comboTienda2.setEnabled(false);
        
        super.interfazModificar();
    }
    
    @Override
    public void limpiar(){
        super.limpiar();
        comboTienda2.setSelectedIndex(0);
        comboTienda1.setSelectedIndex(0);
        comboTienda1.setEnabled(true);
        comboTienda2.setEnabled(true);
        vista.jSpinDias.setValue(0);
    }
    
    @Override
    public void stateChanged(ChangeEvent arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
