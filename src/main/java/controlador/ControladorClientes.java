/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import reportes.ReporteClientes;
import vista.VistaCliente;

/**
 * Clase encargada de reaccionar a los eventos generados por la interfaz para 
 * agregar, modificar, eliminar y reportar Clientes
 * 
 * @author DANIEL
 */
public class ControladorClientes implements ActionListener{
    
    private VistaCliente vista;
    private ReporteClientes reporte;

    public ControladorClientes() {
        this.vista = new VistaCliente();
        this.vista.setVisible(true);
        this.vista.jBtnAgregar.addActionListener(this);
        this.vista.jBtnBuscar.addActionListener(this);
        this.vista.jBtnModificar.addActionListener(this);
        this.vista.jBtnEliminar.addActionListener(this);
        this.vista.jBtnVerClientes.addActionListener(this);
        
        reporte = new ReporteClientes();
        reporte.jTblClientes.setAutoCreateRowSorter(true);
        reporte.jTxtFiltroNit.addActionListener(this);
        reporte.jTxtFiltroNombre.addActionListener(this);
            
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent ev) {
        //Control eventos interfaz principal
        if (ev.getSource() == vista.jBtnVerClientes) {
            reporte.setVisible(true);
            ControladorTabla.llenar(reporte.jTblClientes,
                    /*Dao.TODOS.split(,)*/new String[] {"Nombre","Fabricante", "Garantia"},
                    /*Dao.select(*)*/
            new ArrayList<> (Arrays.asList(
                new String[] {"Batarang", "ToyMaker", "4"},
                new String[] {"Beast", "Tyco", "5"},
                new String[] {"Foquito", "PeraLoca"},
                new String[] {"Hueso", "AnimalPlanet", "6"}
                )
            ));
        }
        
        //Control eventos reporte
        //TODO: Filtrar sobre datos ya filtrados
        if (ev.getSource() == reporte.jTxtFiltroNit) {
            ControladorTabla.filtrar(reporte.jTblClientes,
                    reporte.jTxtFiltroNit.getText(),
                    1);
        } else if (ev.getSource() == reporte.jTxtFiltroNombre) {
            ControladorTabla.filtrar(reporte.jTblClientes,
                    reporte.jTxtFiltroNombre.getText(),
                    0);
        }
    }
    
}
