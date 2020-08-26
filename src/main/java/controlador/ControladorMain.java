/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import dao.DaoTienda;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import modelo.Tienda;
import vista.VistaMain;

/**
 *
 * @author DANIEL
 */
public class ControladorMain implements ActionListener {

    private VistaMain vista;
    private Connection connection;
    private DaoTienda daoTienda;
    ControladorArchivo controladorArchivo;

    public ControladorMain() {
        String url = "jdbc:mysql://localhost:3306/control_pedidos?useSSL=false";
        String user = "root";
        String password = "root";

        try {
            this.connection = DriverManager.getConnection(url, user, password);
            this.daoTienda = new DaoTienda(connection);
            this.vista = new VistaMain();
            this.vista.jBtnEntrar.addActionListener(this);
            ArrayList<String[]> tiendas = daoTienda.buscarVarios("codigo", "");
            if (!tiendas.isEmpty()) {

                for (String[] tienda : tiendas) {

                    vista.jCmbTiendas.addItem(tienda[0]);
                }
                vista.setVisible(true);
            } else {
                controladorArchivo = new ControladorArchivo(connection);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == vista.jBtnEntrar) {
            Tienda actual = new Tienda();
            actual.setCodigo(vista.jCmbTiendas.getSelectedItem().toString());
            ControladorApp controladorApp = new ControladorApp(connection, actual);
        }
    }

}
