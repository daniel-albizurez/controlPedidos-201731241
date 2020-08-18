/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import mainTest.VistaReporte;

/**
 *
 * @author DANIEL
 */
public class ControladorTabla {
    
    public static void filtrar(JTable reporte,String  busqueda, int ... columns){
       ((TableRowSorter<TableModel>) reporte.getRowSorter()).setRowFilter(RowFilter.regexFilter(busqueda,columns));
    }
    
    public static void llenar(JTable reporte,String[] columnas, ArrayList<String[]> filas){
        DefaultTableModel modelo = new DefaultTableModel();
        for (String columna : columnas) {
            modelo.addColumn(columna);
        }
        for (String[] fila : filas) {
            modelo.addRow(fila);
        }
        reporte.setModel(modelo); 
    }
}
