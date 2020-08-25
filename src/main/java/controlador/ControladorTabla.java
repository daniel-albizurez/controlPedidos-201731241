/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author DANIEL
 */
public class ControladorTabla {

    public static void filtrar(JTable tabla, String busqueda, int... columns) {
        busqueda = (busqueda.isBlank()) ? busqueda : String.format("^%s$", busqueda);
        if (tabla.getRowSorter() != null) {
            ((TableRowSorter<TableModel>) tabla.getRowSorter()).setRowFilter(RowFilter.regexFilter(busqueda, columns));
        }
    }

    public static void llenar(JTable tabla, String[] columnas, ArrayList<String[]> filas) {
        DefaultTableModel modelo = new DefaultTableModel();
        tabla.setModel(modelo);
        agregarColumnas(tabla, columnas);
        for (String[] fila : filas) {
            agregarFila(tabla, fila);
        }
    }

    public static void vaciar(JTable tabla) {
        tabla.setModel(new DefaultTableModel());
    }

    public static void agregarFila(JTable tabla, String[] fila) {
        ((DefaultTableModel) tabla.getModel()).addRow(fila);
    }

    public static void agregarColumnas(JTable tabla, String[] columnas) {
        for (String columna : columnas) {
            ((DefaultTableModel) tabla.getModel()).addColumn(columna);
        }
    }
}
