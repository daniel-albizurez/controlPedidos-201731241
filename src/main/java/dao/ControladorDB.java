/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author DANIEL
 */
public class ControladorDB {

    private final Connection connection;
    //TODO: Cambiar Insert para recibir que campos se van a insertar
    private final String INSERT = "INSERT INTO %s %s VALUES (%s)";
    private final String SELECT = "SELECT %s FROM %s";
    private final String UPDATE = "UPDATE %s SET %s";
    private final String DELETE = "DELETE FROM %s";
    private final String WHERE = " WHERE %s";
    private String sql;

    public ControladorDB(Connection connection) {
        this.connection = connection;
        try {
            System.out.println(connection.isClosed());
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Método para limpiar un texto y evitar la inyección SQL
     * @param text el texto a limpiar
     * @return el texto limpio
     */
    private String clean(String text){
        text = text.replace(";", "");
        return text;
    }

/**
 * Método para agregar una nueva tupla a la base de datos en
 * @param fields los campos a agregar
 * @param table la tabla específica
 * @param values los valores de la tupla 
 * @return True si es exitosa, False de lo contrario
 */
    public boolean insert(String table,String fields, String values) {
        /*
        Se le da formato a la constante INSERT utilizando los parametros que
        representan la tabla y los valores a ingresar
        */
        if (fields.isBlank()) {fields = "";} else {fields = "(" + fields +")";}
        sql = String.format(INSERT, table, fields, values);
        sql = clean(sql);
        try {
            //Se genera un PreparedStatement específico para insertar un nuevo registro
            PreparedStatement preparedInsert = connection.prepareStatement(sql);
            //Se envía el nuevo registro a la BD
            preparedInsert.executeUpdate();
//            System.out.println("Correcto " + sql);
        } catch (SQLException e) {
            System.out.println(/*"Incorrecto " + sql
            +*/ e.getMessage());
//            e.printStackTrace();
            return false;
        }
        return true;
    }
/**
 * Método para buscar y recuperar tuplas de la base de datos
 * @param table la tabla de la que se obtienen los resultados
 * @param fields los campos que se desean obtener
 * @param conditions la condición que define qué tuplas recuperar
 * @return 
 */    
    private ResultSet select(String table, String fields, String conditions){
        /*
        Se le da formato a la constante SELET utilizando los parametros que
        representan la tabla y los valores a buscar
        Utiltizando la constante WHERE cuando sea necesaria
        */
        ResultSet result = null;
        if (!conditions.isBlank()) {
            sql = String.format(SELECT + WHERE, fields, table, conditions);
        }else {
            sql = String.format(SELECT, fields, table);
        }
        sql = clean(sql);
        try {
            //Se genera un PreparedStatement específico para seleccionar los registros
            PreparedStatement preparedSelect = connection.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Si la consulta es exitosa se reciben las tuplas recuperadas
            if (preparedSelect.execute()) result = preparedSelect.executeQuery();
            //De lo contratio se retorna un valor nulo
//            System.out.println("Correcto " + sql);
            return result; 
        } catch (SQLException ex) {
            System.out.println("Incorecto " + sql);
//           ex.printStackTrace();
            System.out.println(ex.getMessage());
        } 
        return null;
    }
/**
     * Método para buscar y recuperar tuplas de la base de datos en forma de arreglos
     * @param table La tabla de la que se obtienen los resultados
     * @param fields los campos que se desean obtener
     * @param conditions la condición que define qué se desea recuperar
     * @return ArrayList que contiene los vectores obtenidos de la busquéda
     */
    public ArrayList<String[]> selectArray(String table, String fields, String conditions){
        //Utilizando el método select se realiza y recupera la consulta en forma de ResultSet
        ResultSet result = select(table, fields, conditions);
        ArrayList<String[]> resultado = new ArrayList<>();
        int size = 0;
        int columns = 0;
        try {
            //Si el ResultSet recuperado es válido se convierte cada fila en un vecto
            if (result != null) {
                //Nos movemos al final del resultset para poder conocer el tamaño del ResultSet
                result.last();
                size = result.getRow();
                //A partir de la cantidad de campos solicitados se toma el número de columnas
                columns = fields.split(",").length;
                //Regresamos el cursos antes del inicio del ResultSet para poder recorrerlo en su totalidad
                result.beforeFirst();
                //Mientras el ResultSet tenga una fila siguiente se procede a convertirla en un vector
                  while (result.next()) {
                     //Se genera un vector del tamaño de los campos requeridos
                    String[] valores = new String[columns];
                    //Se llena el vector con los objetos recuperados de la BD
                    for (int j = 1; j <= columns; j++) {
                        valores[j-1] = String.valueOf(result.getObject(j));
                        //Los valores booleanos están representados de manera númerica en los modelos
                        if (String.valueOf(result.getObject(j)).equals("true")) {
                            valores[j-1] = "1";
                        } else if (String.valueOf(result.getObject(j)).equals("false")) {
                            valores[j-1] = "0";
                        }
                    }
                    resultado.add(valores);
                }
            
            }
            
            return resultado; 
        } catch (SQLException ex) {
//           ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
        return null;
    }
/**
 * Método utilizado para actualizar una tupla en la base de datos
 * @param table la tabla específica
 * @param fields los campos a actualizar
 * @param condition la condición que define la actualización
 * @return True si la operación es exitosa, False de lo contrario
 */
    public boolean update(String table,String fields, String condition){
        /*
        Se le da formato a la constante UPDATE utilizando los parametros que
        representan la tabla y los valores a actualizar
        Agregando la constante WHERE cuando una condición sea necesaria
        */
        if  (condition != null){
            sql = String.format(UPDATE + WHERE, table, fields, condition);
        } else {
            sql = String.format(UPDATE, table, fields);
        }
        sql = clean(sql);
        try {
            //Se genera un PreparedStatement específico para actualizar un registro existente
              PreparedStatement preparedInsert = connection.prepareStatement(sql);
              //Se actualiza el registro en la BD
              preparedInsert.executeUpdate();
//              System.out.println("Correcto " + sql);
          } catch (Exception e) {
              System.out.println("Incorrecto " + sql);
//              e.printStackTrace();
                System.out.println(e.getMessage());
              return false;
        } 
        return true;
        
    }
/**
 * Método utilizado para eliminar una tupla de la base de datos
 * @param table la tabla específica
 * @param condition la condición que define qué tupla eliminar
 * @return True si la operación es exitosa, False de lo contrario
 */
    public boolean delete(String table, String condition){
        /*
        Se le da formato a la constante DELETE utilizando el parametro que
        representa la tabla
        Si no hay una condicion dada se detiene el proceso
        */
        if (condition == null) return false;
        sql = String.format(DELETE + WHERE, table, condition);
        sql = clean(sql);
        try {
            //Se genera un PreparedStatement específico para eliminar un registro existente
            PreparedStatement preparedDelete = connection.prepareStatement(sql);
              //Se elimina el registro en la BD
            preparedDelete.executeUpdate();
//            System.out.println("Correcto " + sql);
        } catch (Exception e) {
//            System.out.println("Incorrecto " + sql);
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
}
