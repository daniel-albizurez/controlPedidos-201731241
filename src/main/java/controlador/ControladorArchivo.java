/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import vista.VistaCargaArchivo;

/**
 *
 * @author DANIEL
 */
public class ControladorArchivo implements ActionListener {

    private VistaCargaArchivo vista;
    Connection connection;

    public ControladorArchivo(Connection connection) {
        this.vista = new VistaCargaArchivo();
        this.vista.jBtnCargar.addActionListener(this);
        this.vista.setVisible(true);
        this.connection = connection;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource().equals(this.vista.jBtnCargar)) {
            leer();
        }
    }

    public void leer() {

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(this.vista);
        List<String> registros = new ArrayList<>();
        try ( BufferedReader lector = new BufferedReader(
                new FileReader(chooser.getSelectedFile())
        )) {
            String registro;
            while ((registro = lector.readLine()) != null) {
                registros.add(registro);
            }
        } catch (Exception e) {
            System.out.println("Error al leer el archivo");
            System.out.println(e.getMessage());;
        }
        for (String registro : registros) {
            if (!ControladorRegistros.registrar(registro,connection)) {
                this.vista.jTxtIgnorados.append(registro + "\n");
            }
        }
    }

}
