/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorReportes;

import controlador.ControladorTabla;
import dao.DaoVistas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.JTextField;
import reportes.ReporteProductosVentas;

/**
 *
 * @author DANIEL
 */
public class ControladorReporteProductosVentas implements ActionListener {

    private DaoVistas daoTop;
    private DaoVistas daoPorTienda;
    private DaoVistas daoNunca;

    private ReporteProductosVentas vista;
    private JTable reporte;
    private JButton top;
    private JButton porTienda;
    private JButton nunca;
    private JFormattedTextField fecha1;
    private JFormattedTextField fecha2;
    private JTextField filtroCodigo;

    private final String VISTA_TOP = "mas_vendidos";
    private final String CAMPOS_TOP = "cantidad, nombre_producto, fecha";
    private final String VISTA_NUNCA = "no_vendidos";
    private final String CAMPOS_NUNCA = "codigo, nombre_producto, codigo_tienda, nombre, cantidad";
    private final String VISTA_POR_TIENDA = "mas_vendidos_tienda";
    private final String CAMPOS_POR_TIENDA = CAMPOS_TOP + ", tienda, nombre";

    private final String WHERE = " fecha BETWEEN '%s' AND '%s'";
    private final String LIMIT = "LIMIT 10";

    public ControladorReporteProductosVentas(Connection connection) {
        daoTop = new DaoVistas(VISTA_TOP, CAMPOS_TOP, connection);
        daoPorTienda = new DaoVistas(VISTA_POR_TIENDA, CAMPOS_POR_TIENDA, connection);
        daoNunca = new DaoVistas(VISTA_NUNCA, CAMPOS_NUNCA, connection);

        vista = new ReporteProductosVentas();
        reporte = vista.jTblProductos;
        (top = vista.jBtnTop10).addActionListener(this);
        (nunca = vista.jBtnNunca).addActionListener(this);
        (porTienda = vista.jBtnPorTienda).addActionListener(this);
        (filtroCodigo = vista.jTxtFiltroTienda).addActionListener(this);
        fecha1 = vista.jFormattedTextField1;
        fecha2 = vista.jFormattedTextField2;
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
            fecha1.setEditable(true);
            fecha2.setEditable(true);
        if (ev.getSource() == top) {
            ControladorTabla.llenar(reporte,
                    CAMPOS_TOP.replace("_", " ").toUpperCase().split(","),
                    daoTop.buscarVarios(CAMPOS_TOP, getFechas() + LIMIT));
            filtroCodigo.setEditable(false);
        } else if (ev.getSource() == nunca) {
            ControladorTabla.llenar(reporte,
                    CAMPOS_NUNCA.replace("_", " ").toUpperCase().split(","),
                    daoNunca.buscarVarios(CAMPOS_NUNCA, ""));
            filtroCodigo.setEditable(false);
            fecha1.setEditable(false);
            fecha2.setEditable(false);

        } else if (ev.getSource() == porTienda) {
            ControladorTabla.llenar(reporte,
                    CAMPOS_POR_TIENDA.replace("_", " ").toUpperCase().split(","),
                    daoPorTienda.buscarVarios(CAMPOS_POR_TIENDA, getFechas()));
            filtroCodigo.setEditable(true);
        } else if (ev.getSource() == filtroCodigo) {
            String codigo = filtroCodigo.getText();

            ControladorTabla.filtrar(reporte, codigo, 0);
        }
        fecha1.setText("");
        fecha2.setText("");
    }

    public String getFechas() {
        String fechaInicio = fecha1.getText();
        String fechaFin = fecha2.getText();

        if (!fechaInicio.isBlank() && !fechaFin.isBlank()) {
            return String.format(WHERE, fechaInicio, fechaFin);
        }
        return "1=1 ";
    }

}
