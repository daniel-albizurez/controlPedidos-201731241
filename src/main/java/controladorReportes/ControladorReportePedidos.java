/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controladorReportes;

import controlador.ControladorTabla;
import dao.DaoPedido;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.time.LocalDate;
import javax.swing.JButton;
import javax.swing.JTable;
import modelo.Tienda;
import reportes.ReportePedidosEntrantes;

/**
 *
 * @author DANIEL
 */
public class ControladorReportePedidos implements ActionListener {

    private final String[] COLUMNAS_ENTRANTES = {"Codigo", "Tienda Origen", "Cliente", "Anticipo", "Total", "Fecha Estimada", "Estado"};
    private final String[] COLUMNAS_SALIENTES = {"Codigo", "Tienda Destino", "Cliente", "Anticipo", "Total", "Fecha Estimada", "Estado"};
    private final String campos = DaoPedido.CODIGO + DaoPedido.COMA
            + DaoPedido.TIENDA_ORIGEN + DaoPedido.COMA
            + DaoPedido.CLIENTE + DaoPedido.COMA
            + DaoPedido.ANTICIPO + DaoPedido.COMA
            + DaoPedido.TOTAL + DaoPedido.COMA
            + DaoPedido.FECHA_EN_TIENDA + DaoPedido.COMA
            + DaoPedido.ESTADO;

    private Tienda actual;
    private LocalDate current = LocalDate.now();

    private JTable reporte;

    private JButton enRuta;
    private JButton porRegistrar;
    private JButton atrasados;
    private JButton verEntrantes;
    private JButton verSalientes;

    private ReportePedidosEntrantes vista;

    private DaoPedido dao;

    public ControladorReportePedidos(Connection connection, Tienda actual) {
        dao = new DaoPedido(connection);

        this.actual = actual;

        vista = new ReportePedidosEntrantes();
        (enRuta = vista.jBtnEnRuta).addActionListener(this);
        (porRegistrar = vista.jBtnPorRegistrar).addActionListener(this);
        (atrasados = vista.jBtnAtrasados).addActionListener(this);
        (verEntrantes = vista.jBtnVerEntrantes).addActionListener(this);
        (verSalientes = vista.jBtnVerSalientes).addActionListener(this);

        reporte = vista.jTblPedidos;

        llenarTablaEntrantes(dao.asignacion(DaoPedido.TIENDA_DESTINO, dao.setTexto(actual.getCodigo())));

//        ControladorTabla.llenar(reporte,
//                columnas,
//                dao.buscarVarios(campos, dao.asignacion(DaoPedido.TIENDA_DESTINO, actual.getCodigo())));
        vista.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        if (ev.getSource() == enRuta) {
            llenarTablaEntrantes(dao.asignacion(DaoPedido.ESTADO, dao.setTexto("En ruta"))
                    + DaoPedido.AND
                    + DaoPedido.TIENDA_ORIGEN + "<>" + dao.setTexto(actual.getCodigo())
            );
        } else if (ev.getSource() == porRegistrar) {
            llenarTablaEntrantes("DATEDIFF("
                    + dao.setTexto(current.toString()) + DaoPedido.COMA + DaoPedido.FECHA_EN_TIENDA
                    + ")" + DaoPedido.IGUAL + " 0"
                    + DaoPedido.AND + DaoPedido.ESTADO + "<>" + dao.setTexto("Pagado")
                    + DaoPedido.AND
                    + DaoPedido.TIENDA_ORIGEN + "<>" + dao.setTexto(actual.getCodigo())
            );
        } else if (ev.getSource() == atrasados) {
            llenarTablaEntrantes(dao.asignacion(DaoPedido.ESTADO, dao.setTexto("Atrasado"))
                    + DaoPedido.AND
                    + DaoPedido.TIENDA_ORIGEN + "<>" + dao.setTexto(actual.getCodigo())
            );
        } else if (ev.getSource() == verEntrantes) {
            llenarTablaEntrantes(dao.asignacion(DaoPedido.TIENDA_DESTINO, dao.setTexto(actual.getCodigo())));
        } else if (ev.getSource() == verSalientes) {
            llenarTablaSaliente(dao.asignacion(DaoPedido.TIENDA_ORIGEN, dao.setTexto(actual.getCodigo())));            
        }
    }

    public void llenarTablaEntrantes(String condicion) {
        ControladorTabla.llenar(reporte,
                COLUMNAS_ENTRANTES,
                dao.buscarVarios(campos, condicion)
        );

    }

    public void llenarTablaSaliente(String condicion) {
        ControladorTabla.llenar(reporte,
                COLUMNAS_SALIENTES,
                dao.buscarVarios(campos.replace(DaoPedido.TIENDA_ORIGEN, DaoPedido.TIENDA_DESTINO), condicion)
        );

    }

}
