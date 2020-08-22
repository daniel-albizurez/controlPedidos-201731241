/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * Clase que almacena constantes que utilizan todos los Dao por igual Todas las
 * clases que heredan de esta clase contienen los metodos Agregar, Modificar,
 * Eliminar, Buscar especificos para cada entidad de la base de datos A la vez
 * contienen las constantes específicas a la Entidad y sus campos
 *
 * @author DANIEL
 */
public abstract class Dao<T> {

    public final static String SET = "SET ";
    public final static String IGUAL = " = ";
    public final static String COMILLA = "\'";
    public final static String COMA = ",";
    public final static String AND = "AND";

    protected final String TEXTO = COMILLA + "%s" + COMILLA;
    protected final String ASIGNACION = "%s" + IGUAL + "%s";

    protected ControladorDB controladorDb;

    public Dao(Connection connection) {
        this.controladorDb = new ControladorDB(connection);
    }
    
    //Métodos abstractos
    
    /**
     * Método que retorna un String conteniendo el nombre de la tabla
     * implementada en el dao específico
     * @return
     */
    public abstract String tabla();
    
    /**
     * Método que genera un String la llave primaria de una tupla con la estructura
     * primaryKey = valor
     * @param obj Una instancia de la entidad con la que se está trabajando
     * que contiene el valor de la llave primaria
     * @return El String generado
     */
    public abstract String primaryKey(T obj);
    
    /**
     * Método que retorna un String que contiene los campos Obligatorios
     * de la tabla, separados por comas
     * @return
     */
    public abstract String camposObligatorios();

    /**
     * Método que retorna un String conteniendo todos los campos 
     * de la tabla específica a la implementación
     * @return
     */
    public abstract String todos();
    
    /**
     * Método que genera un String que contiene la estructura 
     * de los valores a insertar en una tabla
     * valor1, 'valor2',...
     * para insertar valores en una tabla en una BD
     * En la implementación de cada subclase se define el tipo
     * de entidad y sus campos
     * @param obj El objeto pojo que representa a la entidad a insertar
     * @param noObligatorios Define si se desea ingresar los campos no obligatorios a la bd
     * @return El String generado
     */
    public abstract String insertar(T obj, boolean noObligatorios);
    
    /**
     * Método que genera un String que contiene la estructura
     * para actualizar los campos de una tabla
     * campo1 = valor1, campo2 = 'valor2'
     * @param obj el pojo de la entidad a actualizar, con los valores nuevos
     * @return El String generado
     */
    public abstract String setCamposYValores(T obj);
    
    /**
     * Método que genera una instancia del pojo específio 
     * @param datos Arreglo de tipo String que contiene los datos de 
     * una tupla recuperada de la BD
     * @return La instancia del objeto
     */
    public abstract T generarModelo(String[] datos);
    
    //Métodos no abstractos
    
    /**
     * Método utilizado para insertar una nueva tupla en la BD
     * Depende del modelo (POJO) con el que se está trabajando y
     * la implementación de los métodos abstractos
     * @param nuevo La instancia del objeto que se va a ingresar
     * @param noObligatorios Define si se desea ingresar los campos no obligatorios a la bd
     * @return Boolean true si la inserción fue exitosa, false de lo contrario
     */
    public boolean agregar(T nuevo, boolean noObligatorios) {
        String campos = (noObligatorios) ? todos() : camposObligatorios();
        return this.controladorDb.insert(tabla(), campos, insertar(nuevo, noObligatorios));
    }
    
    /**
     * Método que realiza una búsqueda en la BD en la tabla (Según la implementación)
     * Se retorna un lista de arreglos, para facilitar el manejo de datos
     * y  la generación de reportes
     * @param campos Los campos que se desean recuperar
     * @param condicion La condición con la que se va a buscar
     * @return Una lista de arreglos, en la cual cada arreglo contiene los campos
     * recuperados
     */
    public ArrayList<String[]> buscarVarios(String campos, String condicion) {
        campos = ("*".equals(campos)) ? todos() : campos;
        return this.controladorDb.selectArray(tabla(), campos, condicion);
    }
    
    /**
     * Método para buscar una tupla y convertirla en la instancia
     * correspondiendte
     * @param campoCondicion El campo con el cual se va a buscar la tupla
     * @param valorCondicion El valor con el cual se desea comparar
     * @return Retorna la instancia o null si no se encontró una tupla que
     * cumpliera la condición
     */
    public T seleccionar(String campoCondicion, String valorCondicion) {
        String condicion = String.format(ASIGNACION,
                campoCondicion.replace(IGUAL, ""),
                setTexto(valorCondicion.replace(IGUAL, "")));
        try {
            String datos[] = buscarVarios(todos(), condicion).get(0);
            return generarModelo(datos);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    /**
     * Método que permite actualizar una tupla en la BD
     * @param modificado Una instancia del objeto correspondiente a la tupla
     * que se desea modificar, con valores actualizados
     * @return Boolean true si la actualización fue exitosa, false de lo contrario
     */
    public boolean modificar(T modificado) {
        return controladorDb.update(tabla(), setCamposYValores(modificado), primaryKey(modificado));
    }
    
    /**
     * Método que permite eliminar una tupla en la BD
     * @param eliminado Una instancia del objeto correspondiente a la tupla
     * que se desea eliminar
     * @return Boolean true si la eliminación fue exitosa, false de lo contrario
     */
    public boolean eliminar(T eliminado) {
        return controladorDb.delete(tabla(), primaryKey(eliminado));
    }
    
    /**
     * Método que genera un String
     * utilizando el formato (String.format) de la constante
     * ASIGNACION la cual tiene la estructura 
     *  %s = %s
     * @param campo El primer parametro para la constante,
     * correspondiente a un campo de la tabla específica
     * @param valor El segundo parametro para la constante,
     * correspondiente al valor que se desea asignar al campo
     * @return El String generado
     */
    public String asignacion(String campo, String valor) {
        return String.format(ASIGNACION, campo, valor);
    }

    /**
     * Método que genera un String
     * utilizando el formato (String.format) de la constante
     * TEXTO la cual tiene la estructura 
     *  '%s'
     * @param valor El valor que debe estar contenido en la constante
     * @return El String generado
     */
    public String setTexto(String valor) {
        return String.format(TEXTO, valor);
    }
}

