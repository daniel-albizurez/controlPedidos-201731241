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
        Boolean exitoso = true;
        try {
            switch (datos[0] + datos.length) {
                case "PRODUCTO7":
                    Producto nuevoProducto = new Producto(datos[3],
                            datos[1], datos[2], Double.parseDouble(datos[5]));
                    Ubicacion nuevaUbicacion = new Ubicacion(nuevoProducto.getCodigo(),
                            datos[6], Integer.parseInt(datos[4]));
                    break;
                case "CLIENTE5":
                    Cliente nuevoCliente = new Cliente(datos[2], datos[1], datos[3], Double.parseDouble(datos[4])); 
                    break;
                case "EMPLEADO5":
                    Empleado nuevoEmpleado = new Empleado(Integer.parseInt(datos[2]), datos[4], datos[1], datos[3]);
                    break;
                case "PEDIDO":
                    break;
                case "TIENDA5":
                    Tienda nuevaTienda = new Tienda(datos[3], datos[1], datos[2], datos[4]);
                    break;
                case "TIEMPO4":
                    Tiempo nuevoTiempo = new Tiempo(datos[1], datos[2], Integer.parseInt(datos[3]));
                    break;
                default:
                    exitoso = false;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            exitoso = false;
        }
        return exitoso;
    }
}
