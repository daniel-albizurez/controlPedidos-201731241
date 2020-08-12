/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import modelo.*;

/**
 *
 * @author DANIEL
 */
public class ControladorRegistros {

    public static boolean registrar(String registro) {
        String[] datos = registro.split(",");
        try {
            switch (datos[0]) {
                case "PRODUCTO":
                    Producto nuevo = new Producto(datos[3],
                            datos[1], datos[2], Double.parseDouble(datos[5]));
                    Ubicacion nueva = new Ubicacion(nuevo.getCodigo(),
                            datos[6], Integer.parseInt(datos[4]));
                    break;
                case "CLIENTE":
                    break;
                case "EMPLEADO":
                    break;
                case "PEDIDO":
                    break;
                case "TIENDA":
                    break;
                case "TIEMPO":
                    break;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
