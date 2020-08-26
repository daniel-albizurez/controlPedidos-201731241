/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTienda;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import modelo.Tienda;
import vista.VistaTienda;

/**
 *
 * @author DANIEL
 */
public class ControladorTienda extends Controlador<Tienda, VistaTienda, VistaTienda> {

    private final String TIEMPO_INCORRECTO = "Ingrese valores correcto de tiempo";

    private JTextField horaFin;
    private JTextField horaInicio;

    public ControladorTienda(Connection connection) {
        dao = new DaoTienda(connection);

        this.vista = new VistaTienda();
        (agregar = this.vista.jBtnAgregar).addActionListener(this);
        (buscar = this.vista.jBtnBuscar).addActionListener(this);
        (eliminar = this.vista.jBtnEliminar).addActionListener(this);
        (modificar = this.vista.jBtnModificar).addActionListener(this);
        (cancelar = this.vista.jBtnCancelar).addActionListener(this);
        this.vista.setVisible(true);

        (horaFin = this.vista.jTxtHorarioFin).addActionListener(this);
        (horaInicio = this.vista.jTxtHorarioInicio).addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        mensaje = "";
        if (ev.getSource() == agregar) {
            if (revisaHora(horaFin)&& revisaHora(horaInicio)) {
                mensaje = agregar(construirModelo());
            }
        } else if (ev.getSource() == buscar) {
            String codigo = vista.jTxtCodigo.getText();
            if (!codigo.isBlank()) {
                modelo = dao.seleccionar(DaoTienda.CODIGO, codigo);
                if (modelo != null) {
                    mostrarModelo(modelo);
                    mensaje = "";
                    interfazModificar();
                } else {
                    mensaje = String.format(NO_EXISTE, dao.tabla(), DaoTienda.CODIGO + " " + codigo);
                }
            } else {
                mensaje = String.format(INGRESE_VALOR, DaoTienda.CODIGO);
            }
        } else if (ev.getSource() == modificar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                if (revisaHora(horaFin) && revisaHora(horaInicio)) {
                    mensaje = modificar(construirModelo());
                } else {
                    mensaje = TIEMPO_INCORRECTO;
                }
            } else {
                mensaje = ERROR;
                vista.jTxtCodigo.setText(modelo.getCodigo());
            }
        } else if (ev.getSource() == eliminar) {
            if (modelo.getCodigo().equals(vista.jTxtCodigo.getText())) {
                mensaje = eliminar(construirModelo());
            } else {
                mensaje = ERROR;
            }
        } else {
            super.actionPerformed(ev);
        }

        if (ev.getSource() == horaInicio
                || ev.getSource() == horaFin) {
            revisaHora((JTextField) ev.getSource());
        }

        if (!mensaje.isBlank()) {
            JOptionPane.showMessageDialog(vista, mensaje);
        }
    }

    @Override
    public Tienda construirModelo() {
        Tienda t = new Tienda();
        t.setCodigo(vista.jTxtCodigo.getText());
        t.setNombre(vista.jTxtNombre.getText());
        t.setDireccion(vista.jTxtDireccion.getText());
        t.setTelefono1(vista.jTxtTel1.getText());
        t.setTelefono2(vista.jTxtTel2.getText());
        t.setEmail(vista.jTxtEmail.getText());
        String horario = vista.jTxtHorarioInicio.getText()
                + "-" + vista.jTxtHorarioFin.getText();
        t.setHorario(horario);
        return t;
    }

    @Override
    public void mostrarModelo(Tienda modelo) {
        vista.jTxtCodigo.setText(modelo.getCodigo());
        vista.jTxtNombre.setText(modelo.getNombre());
        vista.jTxtDireccion.setText(modelo.getDireccion());
        vista.jTxtTel1.setText(modelo.getTelefono1());
        vista.jTxtTel2.setText(modelo.getTelefono2());
        vista.jTxtEmail.setText(modelo.getEmail());
        try {
            String[] horario = modelo.getHorario().split("-");
            vista.jTxtHorarioInicio.setText(horario[0]);
            vista.jTxtHorarioFin.setText(horario[1]);

        } catch (IndexOutOfBoundsException e) {
            vista.jTxtHorarioFin.setText("18:00");

        }
    }

    @Override
    public void interfazModificar() {
        vista.jTxtCodigo.setEditable(false);
        super.interfazModificar();
    }

    @Override
    public void limpiar() {
        super.limpiar();
        this.vista.jTxtCodigo.setText("");
        this.vista.jTxtCodigo.setEditable(true);
        this.vista.jTxtNombre.setText("");
        this.vista.jTxtDireccion.setText("");
        this.vista.jTxtTel1.setText("0");
        this.vista.jTxtTel2.setText("0");
        this.vista.jTxtEmail.setText("0");
        this.vista.jTxtHorarioInicio.setText("10:00");
        this.vista.jTxtHorarioFin.setText("18:00");
    }

    public Boolean revisaHora(JTextField jTxtHora) {
        String tiempo = jTxtHora.getText();
        if (tiempo.matches("\\d\\d:\\d\\d")) {
            try {
                int hora = Integer.parseInt(tiempo.substring(0, 2));
                int minutos = Integer.parseInt(tiempo.substring(3, 5));
                if (hora < 24 && hora > 0
                        && minutos >= 0 && minutos < 60) {
                    return true;
                }
            } catch (NumberFormatException e) {
            }
        }
        jTxtHora.setText("10:00");
        return false;

    }
}
