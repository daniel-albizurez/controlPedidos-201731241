/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import reportes.VistaReporte;

/**
 *
 * @author DANIEL
 */
public class ControladorTabla {
    VistaReporte vista = new VistaReporte();

    public ControladorTabla(String[] columnas, ArrayList<String[]> filas) {
        vista.jTblReporte.setModel(llenar(columnas, filas));
        vista.jTblReporte.setAutoCreateRowSorter(true);
        vista.setVisible(true);
    }
    
    public  void filtrar(String  busqueda, int ... columns){
       ((TableRowSorter<TableModel>) vista.jTblReporte.getRowSorter()).setRowFilter(RowFilter.regexFilter(busqueda,columns));
    }
    
    public static DefaultTableModel llenar(String[] columnas, ArrayList<String[]> filas){
        DefaultTableModel modelo = new DefaultTableModel();
        for (String columna : columnas) {
            modelo.addColumn(columna);
        }
        for (String[] fila : filas) {
            modelo.addRow(fila);
        }
        return modelo;
    }
}
