/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

/**
 *
 * @author DANIEL
 */
public class Tiempo {
    private int tienda1;
    private int tienda2;
    private int dias;

    public Tiempo(int tienda1, int tienda2, int dias) {
        this.tienda1 = tienda1;
        this.tienda2 = tienda2;
        this.dias = dias;
    }
    
    public Tiempo() {
    }

    public int getTienda1() {
        return tienda1;
    }
    public void setTienda1(int tienda1) {
        this.tienda1 = tienda1;
    }

    public int getTienda2() {
        return tienda2;
    }
    public void setTienda2(int tienda2) {
        this.tienda2 = tienda2;
    }

    public int getDias() {
        return dias;
    }
    public void setDias(int dias) {
        this.dias = dias;
    }
    
}
