/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;

/**
 * Clase que almacena constantes  que utilizan todos los Dao por igual
 * Todas las clases que heredan de esta clase contienen los metodos Agregar,
 * Modificar, Eliminar, Buscar especificos para cada entidad de la base de datos
 * A la vez contienen las constantes específicas a la Entidad y sus campos
 * @author DANIEL
 */
public abstract class Dao{
    public final static String SET = "SET ";
    public final static String IGUAL = " = ";
    public final static String COMILLA = "\'";
    public final static String COMA = ",";
    public final static String AND = "AND";
    
    /**
     * Método utilizado para almacenar insertar una 
     * tupla en la base de datos
     * @param <T> Tipo de entidad a insertar
     * @param agregar El objeto con los datos a agregar en la BD
     * @return Verdadero si no se encuentran errores, falso de lo contrario
     */
    public abstract <T> boolean agregar(T agregar);
    
    public abstract <T> boolean modificar(T modificar);
   
    public abstract <T> T seleccionar(String condicion);
   
    public abstract <T> boolean eliminar(T eliminar);
    
}
