package com.claro.WSLigaCampeones.util.configuracion;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.util.bd.Conexion;

/**
 * Descripcion: Clase encargada de realizar la configuracion de las propiedades
 * que son utilizadas por la aplicacion
 * 
 * @author Esteban Camilo Beltran
 * @version 1.1
 */
public class Propiedades {

	private static Propiedades instance;
	private static Properties properties = new Properties();
	private static Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());
	
	/**
	 * Constuctor de la clase. Carga las propiedades del Sistema
	 */
	private Propiedades() {
	}

	/**
	 * Carga las propiedades por medio del archivo de configuracion
	 */
	private void cargarPropiedadesPorArchivo() {

		InputStream entrada = null;
		try {
			logger.info("Cargando propiedades por Archivo");
			entrada = new FileInputStream(Configurador.getRUTA_PROPIEDADES());
			properties.load(entrada);
		} catch (Exception e) {
			logger.error("Error obteniendo parametros de configuracion por medio de archivo", e);
		}
	}

	/**
	 * Carga las propiedades por medio de base de datos
	 */
	private void cargarPropiedadesPorBD() {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = getPropiedad(UtilsConstantes.CONSULTA_PROPIEDADES);
		try {
			logger.info("Cargando propiedades por Base de Datos");
			con = Conexion.getInstance().getConnection(getPropiedad(UtilsConstantes.BD_PROPERTIES),
					getPropiedad(UtilsConstantes.TIPO_BD_PROPERTIES));

			ps = con.prepareStatement(sql);
			//ps.setString(1, Configurador.getNOMBRE_APLICACION());
			rs = ps.executeQuery();
			String key, value;
			while (rs.next()) {
				key = rs.getString(UtilsConstantes.PROPIEDAD);
				value = rs.getString(UtilsConstantes.VALOR);
				properties.setProperty(key, value == null ? "" : value);
			}
		} catch (Exception e) {
			logger.error("Error obteniendo parametros de configuracion por medio de tabla", e);
		} finally {
			Conexion.cerrar(rs);
			Conexion.cerrar(ps);
			Conexion.cerrar(con);
		}
	}

	/**
	 * Retorna la instancia del objeto Propiedades
	 * 
	 * @return Un objeto propiedades con las propiedades del jar
	 */
	public static Propiedades getInstance() {
		synchronized (Propiedades.class) {
			if (instance == null) {
				instance = new Propiedades();
				instance.cargarPropiedadesPorArchivo();
				instance.cargarPropiedadesPorBD();
			}
		}
		return instance;
	}

	/**
	 * Obtiene la propiedad ingresada como parametro, y formatea las variables con
	 * los parametros ingresados
	 * 
	 * @param propiedad Propiedad de la cual se desea obtener el valor
	 * @param params    Valor de los parametros para formatear la propiedad
	 * @return El valor de la propiedad solicitada
	 */
	public String getPropiedad(String propiedad, Object... params) {
		String message = properties.getProperty(propiedad);
		if (params != null && params.length != 0) {
			message = MessageFormat.format(message, params);
		}
		return message;
	}

	/**
	 * Obtiene la propiedad ingresada como par�metro y la parsea a Entero
	 * 
	 * @param propiedad Propiedad de la cual se desea obtener el valor
	 * @param params    Valor de los parametros para formatear la propiedad
	 * @return El valor de la propiedad solicitada
	 */
	public Integer getIntPropiedad(String propiedad) {
		Integer result = null;

		try {
			String message = properties.getProperty(propiedad);
			result = Integer.parseInt(message);
		} catch (Exception e) {
			logger.info("Error al obtener la propiedad.");
		}

		return result;
	}
	
	/**
	 * Obtiene boolean a partir de comparaci�n entre propiedad obtenida y valor
	 * entero de modo ON (activo)
	 * 
	 * @param propiedad
	 * @param onParam:  Valor a caracterizar como TRUE
	 * @return
	 */
	public boolean getPropiedadBooleana(String propiedad) {
		Boolean result = false;

		try {
			String message = properties.getProperty(propiedad);
			result = Boolean.parseBoolean(message.trim().toLowerCase());
		} catch (Exception e) {
			logger.info("Error al obtener la propiedad.");
		}

		return result;
	}

	/**
	 * Obtiene boolean a partir de comparaci�n entre propiedad obtenida y valor
	 * entero de modo ON (activo)
	 * 
	 * @param propiedad
	 * @param onParam:  Valor a caracterizar como TRUE
	 * @return
	 */
	public boolean getPropiedadBooleanaDeBinario(String propiedad, int onParam) {
		Boolean result = false;

		try {
			String message = properties.getProperty(propiedad);
			result = (onParam == Integer.parseInt(message));
		} catch (Exception e) {
			logger.info("Error al obtener la propiedad.");
		}

		return result;
	}

	/**
	 * Realiza el reset de las propiedades del sistema.
	 */
	protected static void resetProperties() {
		logger.info("Llamado a proceso de actualizacion de propiedades");
		instance = null;
		getInstance();
		escribirPropiedades();
		logger.info("Actualizacion realizada con exito!");
	}

	/**
	 * escribe Propiedades en Log
	 */
	protected static void escribirPropiedades() {
		Set<Object> keys = properties.keySet();
		StringBuilder propiedades = new StringBuilder();
		
		List sortedKeys = new ArrayList(keys);
		Collections.sort(sortedKeys);
		
		for (Object k : sortedKeys) {
			String key = (String) k;
			propiedades.append(key).append(UtilsConstantes.IGUAL).append(properties.getProperty(key)).append("\r\n");
		}
		
		logger.info("Las propiedades cargadas en el sistema son: \r\n" + propiedades.toString());
	}
}