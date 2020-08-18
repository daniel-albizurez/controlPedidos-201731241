/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 * Clase para almacenar las tuplas recolectadas de la tabla Tiempo de la BD
 * La cual almacena el tiempo de envio entre dos tiendas
 * @author DANIEL
 */
public class Tiempo {
    private String tienda1;
    private String tienda2;
    private int dias;

    public Tiempo(String tienda1, String tienda2, int dias) {
        this.tienda1 = tienda1;
        this.tienda2 = tienda2;
        this.dias = dias;
    }
    
    public Tiempo() {
    }

    public String getTienda1() {
        return tienda1;
    }
    public void setTienda1(String tienda1) {
        this.tienda1 = tienda1;
    }

    public String getTienda2() {
        return tienda2;
    }
    public void setTienda2(String tienda2) {
        this.tienda2 = tienda2;
    }

    public int getDias() {
        return dias;
    }
    public void setDias(int dias) {
        this.dias = dias;
    }

    @Override
    public String toString() {
        return "Tiempo:" + " tienda1 = " + tienda1 + ", tienda2 = " + tienda2 + ", dias = " + dias;
    }
    
}
