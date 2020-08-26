/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.Dao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;

/**
 *
 * @author DANIEL
 */
public abstract class Controlador<M, V extends JFrame, R extends JFrame> implements ActionListener {

    protected V vista;
    protected JButton agregar;
    protected JButton buscar;
    protected JButton modificar;
    protected JButton eliminar;
    protected JButton verTodos;
    protected JButton cancelar;

    protected R reporte;
    protected JTable tablaReporte;

    protected Dao<M> dao;

    protected M modelo;

    protected String mensaje = "";

    public static final String INSERCION_CORRECTA = "Se ha ingresado el %s con %s exitosamente";
    public static final String INSERCION_INCORRECTA = "El %s con %s ya existe";
    public static final String NO_EXISTE = "No existe el %s con %s";
    public static  final String INGRESE_VALOR = "Por favor ingrese un %s";
    public static final String ERROR = "Ha ocurrido un error, por favor vuelva a intentarlo";
    public static final String MODIFICACION_EXITOSA = "La modificación de %s con %s ha sido exitosa";
    public static final String MODIFICACION_FRACASADA = "No se ha podido modificar el %s";
    public static final String ELIMINACION_EXITOSA = "%s eliminado exitosamente";
    public static final String ELIMINACION_FRACASADA = "No se ha podido eliminar el %s";
    public static final String CAMPOS_OBLIGATORIOS = "Por favor llene los campos obligatorios";
    public static final String NO_BORRAR = "Por favor no borre los campos obligatorios";

    public abstract M construirModelo();

    public abstract void mostrarModelo(M modelo);

    public void interfazModificar(){
        modificar.setEnabled(true);
        eliminar.setEnabled(true);
        cancelar.setEnabled(true);
        agregar.setEnabled(false);
    }

    public void limpiar() {
        agregar.setEnabled(true);
        modificar.setEnabled(false);
        eliminar.setEnabled(false);
        cancelar.setEnabled(false);
        modelo = null;
    }

    public String agregar(M modelo) {
        if (modelo != null) {
            return (dao.agregar(modelo))
                    ? String.format(INSERCION_CORRECTA, dao.tabla(), dao.primaryKey(modelo))
                    : String.format(INSERCION_INCORRECTA, dao.tabla(), dao.primaryKey(modelo));
        } else {
            return CAMPOS_OBLIGATORIOS;
        }
    }

    public String modificar(M modelo) {
        if (modelo == null) {
            return CAMPOS_OBLIGATORIOS;
        } else if (dao.modificar(modelo)) {
            limpiar();
            return String.format(MODIFICACION_EXITOSA, dao.tabla(), dao.primaryKey(modelo));
        } else {
            return MODIFICACION_FRACASADA;
        }
    }

    public String eliminar(M modelo) {
        if (modelo == null) {
            return NO_BORRAR;
        } else if (dao.eliminar(modelo)) {
            limpiar();
            return String.format(ELIMINACION_EXITOSA, dao.tabla());
        } else {
            return String.format(ELIMINACION_FRACASADA, dao.tabla());
        }
    }

    public void cancelarModificacion() {
        limpiar();
    }

    public void verTodos() {
        if (reporte != null) {
            reporte.setVisible(true);
            ControladorTabla.llenar(tablaReporte,
                    dao.todos().split(","),
                    dao.buscarVarios("*", "")
            );
        }
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == verTodos) {
            verTodos();
        } else if(ev.getSource() == cancelar) {
            cancelarModificacion();
        }
    }
}
