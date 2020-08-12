/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;
import reportes.VistaReporte;

/**
 *
 * @author DANIEL
 */
public class ControladorReporte {
    VistaReporte vista = new VistaReporte();

    public ControladorReporte(String[] columnas, ArrayList<String[]> filas) {
        vista.jTblReporte.setModel(llenar(columnas, filas));
        vista.setVisible(true);
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
