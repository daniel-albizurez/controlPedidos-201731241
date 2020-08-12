/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

/**
 *
 * @author DANIEL
 */
public class ControladorRegistros {
    public static void registrar(String registro){
        String[] datos = registro.split(",");
        
        switch (datos[0]){
            case "PRODUCTO":
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
    }
}
