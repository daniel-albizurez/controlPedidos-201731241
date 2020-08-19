/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mainTest.ConexionBBDD;

/**
 *
 * @author DANIEL
 */
public class ControladorDB {

    private static ConexionBBDD conexion = new ConexionBBDD();
    //TODO: Cambiar Insert para recibir que campos se van a insertar
    private static final String INSERT = "INSERT INTO %s VALUES (%s)";
    private static final String SELECT = "SELECT %s FROM %s";
    private static final String UPDATE = "UPDATE %s SET %s";
    private static final String DELETE = "DELETE FROM %s";
    private static final String WHERE = " WHERE %s";
    private static String sql;
    
    /**
     * Método para realizar la conexión o desconexión a la Base de Datos 
     * @param desconectar Parámetro indicando al método si se desea terminar la conexión
     */
    private static void conectar(boolean desconectar) {
        if (desconectar) {
            conexion.cerrarConexion();
        } else {
            conexion.abrirConexion();
        }
    }
    /**
     * Método para limpiar un texto y evitar la inyección SQL
     * @param text el texto a limpiar
     * @return el texto limpio
     */
    private static String clean(String text){
        text = text.replace(";", "");
        return text;
    }

/**
 * Método para agregar una nueva tupla a la base de datos en
 * @param table la tabla específica
 * @param values los valores de la tupla 
 * @return True si es exitosa, False de lo contrario
 */
    public static boolean insert(String table, String values) {
        /*
        Se le da formato a la constante INSERT utilizando los parametros que
        representan la tabla y los valores a ingresar
        */
        sql = String.format(INSERT, table, values);
        sql = clean(sql);
        try {
            conectar(false);
            //Se genera un PreparedStatement específico para insertar un nuevo registro
            PreparedStatement preparedInsert = conexion.getConexion().prepareStatement(sql);
            //Se envía el nuevo registro a la BD
            preparedInsert.executeUpdate();
            System.out.println("Correcto " + sql);
        } catch (Exception e) {
            System.out.println("Incorrecto " + sql);
            e.printStackTrace();
            return false;
        } finally {
            conectar(true);
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
    private static ResultSet select(String table, String fields, String conditions){
        /*
        Se le da formato a la constante SELET utilizando los parametros que
        representan la tabla y los valores a buscar
        Utiltizando la constante WHERE cuando sea necesaria
        */
        ResultSet result = null;
        if (conditions != null) {
            sql = String.format(SELECT + WHERE, fields, table, conditions);
        }else {
            sql = String.format(SELECT, fields, table);
        }
        sql = clean(sql);
        try {
            conectar(false);
            //Se genera un PreparedStatement específico para seleccionar los registros
            PreparedStatement preparedSelect = conexion.getConexion().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //Si la consulta es exitosa se reciben las tuplas recuperadas
            if (preparedSelect.execute()) result = preparedSelect.executeQuery();
            //De lo contratio se retorna un valor nulo
            System.out.println("Correcto " + sql);
            return result; 
        } catch (SQLException ex) {
            System.out.println("Incorecto " + sql);
           ex.printStackTrace();
        } finally {
//           conectar(true);
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
    public static ArrayList<String[]> selectArray(String table, String fields, String conditions){
        //Utilizando el método select se realiza y recupera la consulta en forma de ResultSet
        ResultSet result = select(table, fields, conditions);
        ArrayList<String[]> resultado = new ArrayList<>();
        int size = 0;
        int columns = 0;
        try {
//            conectar(false);
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
           ex.printStackTrace();
        } finally {
           conectar(true);
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
    public static boolean update(String table,String fields, String condition){
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
            conectar(false);
            //Se genera un PreparedStatement específico para actualizar un registro existente
              PreparedStatement preparedInsert = conexion.getConexion().prepareStatement(sql);
              //Se actualiza el registro en la BD
              preparedInsert.executeUpdate();
              System.out.println("Correcto " + sql);
          } catch (Exception e) {
              System.out.println("Incorrecto " + sql);
              e.printStackTrace();
              return false;
        } finally {
            conectar(true);
        } 
        return true;
        
    }
/**
 * Método utilizado para eliminar una tupla de la base de datos
 * @param table la tabla específica
 * @param condition la condición que define qué tupla eliminar
 * @return True si la operación es exitosa, False de lo contrario
 */
    public static boolean delete(String table, String condition){
        /*
        Se le da formato a la constante DELETE utilizando el parametro que
        representa la tabla
        Si no hay una condicion dada se detiene el proceso
        */
        if (condition == null) return false;
        sql = String.format(DELETE + WHERE, table, condition);
        sql = clean(sql);
        try {
            conectar(false);
            //Se genera un PreparedStatement específico para eliminar un registro existente
            PreparedStatement preparedDelete = conexion.getConexion().prepareStatement(sql);
              //Se elimina el registro en la BD
            preparedDelete.executeUpdate();
            System.out.println("Correcto " + sql);
        } catch (Exception e) {
            System.out.println("Incorrecto " + sql);
            System.out.println(e.toString());
            return false;
        } finally {
            conectar(true);
        }
        return true;
    }
}
