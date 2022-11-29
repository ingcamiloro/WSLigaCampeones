package com.claro.WSLigaCampeones.util.configuracion;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

import com.claro.WSLigaCampeones.util.bd.Conexion;

/**
 * Desccripcion: Clase encargada de realizar la configuracion de las propiedades
 * y el log del sistema
 * 
 * @author Esteban Camilo Beltran
 * @version 1.0.
 *
 */
public class Configurador {

	private static String RUTA_PROPIEDADES;
	private static String NOMBRE_LOGGER;
	private static String NOMBRE_APLICACION;

	/**
	 * Metodo encargado de realizar la configuracion de las propiedades y el log de
	 * la aplicacion
	 * 
	 * @param rutaPropiedades
	 */
	public static void configurar(String rutaPropiedades, String nombreLogger, String nombreAplicacion) {

		RUTA_PROPIEDADES = rutaPropiedades;
		NOMBRE_LOGGER = nombreLogger;
		NOMBRE_APLICACION = nombreAplicacion;
		Propiedades.getInstance();
		configurarLog(true);
		Propiedades.escribirPropiedades();
	}

	/**
	 * Metodo encargado de resetear la configuracion del archivo de log4j, que
	 * obligatoriamente debe ser tipo PROPERTIES
	 */
	public static String resetPropiedades(String rutaPropiedades, String nombreLogger, String nombreAplicacion,
			String usuario, String psw) {

		Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		String response = "";
		try {
			if (usuario == null || psw == null) {
				return UtilsConstantes.DATOS_INC_RESET;
			}
			Boolean esValido = validarDatos(usuario, psw);

			if (esValido) {
				logger.info("Usuario y contrase�a validos");
				RUTA_PROPIEDADES = rutaPropiedades;
				NOMBRE_LOGGER = nombreLogger;
				NOMBRE_APLICACION = nombreAplicacion;
				Propiedades.resetProperties();
				configurarLog(false);
				response = UtilsConstantes.DATOS_VAL_RESET;
			} else {
				logger.info("Usuario y/o contrase�a incorrectos");
				response = UtilsConstantes.DATOS_INVAL_RESET;
			}
		} catch (Throwable tr) {
			logger.error("Error actualizando las propiedades del sistema", tr);
			response = UtilsConstantes.ERROR_RESET;
		} finally {
			Configurador.cerrarTransaccion(ini, logger);
		}
		return response;
	}
	
	/**
	 * Metodo encargado de resetear la configuracion del archivo de log4j, que
	 * obligatoriamente debe ser tipo PROPERTIES
	 */
	public static String resetPropiedades(String rutaPropiedades, String nombreLogger, String nombreAplicacion) {

		Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());
		ParametrosIniciales ini = Configurador.iniciarTransaccion();
		String response = "";
		try {
			RUTA_PROPIEDADES = rutaPropiedades;
			NOMBRE_LOGGER = nombreLogger;
			NOMBRE_APLICACION = nombreAplicacion;
			Propiedades.resetProperties();
			configurarLog(false);
			response = UtilsConstantes.DATOS_VAL_RESET;
		} catch (Throwable tr) {
			logger.error("Error actualizando las propiedades del sistema", tr);
			response = UtilsConstantes.ERROR_RESET;
		} finally {
			Configurador.cerrarTransaccion(ini, logger);
		}
		return response;
	}

	/**
	 * Metodo encargado de validar el usuario y la contrase�a para realizar la
	 * actualizacion de las propiedades del sistema
	 * 
	 * @param usuario: Usuario que realiza la actualizacion
	 * @param psw:     Contrase�a del usuario
	 * @return
	 */
	public static Boolean validarDatos(String usuario, String psw) {

		Connection con = null;
		CallableStatement cs = null;
		Boolean valido = false;
		Propiedades prop = Propiedades.getInstance();
		Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());
		String sql = prop.getPropiedad(UtilsConstantes.VALIDA_USUARIO);
		try {
			logger.debug("Ingresa a validarDatos");
			if (sql == null || sql.isEmpty())
				return false;

			con = Conexion.getInstance().getConnection(prop.getPropiedad(UtilsConstantes.BD_PROPERTIES),
					prop.getPropiedad(UtilsConstantes.TIPO_BD_PROPERTIES));
			cs = con.prepareCall(sql);
			cs.setString(1, UtilsConstantes.KEY_USUARIO);
			cs.setString(2, usuario);
			cs.setString(3, UtilsConstantes.KEY_PSW);
			cs.setString(4, psw);
			cs.setString(5, NOMBRE_APLICACION);
			cs.registerOutParameter(6, Types.NUMERIC);
			cs.registerOutParameter(7, Types.VARCHAR);
			cs.execute();
			logger.info("Respuesta validacion de usuario y contrase�a: " + cs.getInt(6) + " - " + cs.getString(7));

			valido = UtilsConstantes.RTA_EXITOSA == cs.getInt(6);
		} catch (Exception e) {
			logger.error("Error desconocido validando usuario y contrase�a", e);
		} finally {
			Conexion.cerrar(cs);
			Conexion.cerrar(con);
		}
		logger.debug("Termina validarDatos");
		return valido;
	}

	/**
	 * Metodo encargado de retornar el tiempo inicial de la transaccion junto con el
	 * id de transaccion. Configura el log para registrar el id de transaccion
	 * 
	 * @return Objeto con los parametros de tiempo inicial de la transaccion y id de
	 *         transaccion
	 */
	public static ParametrosIniciales iniciarTransaccion() {
		ParametrosIniciales param = new ParametrosIniciales();
		ThreadContext.put("UUID", "WS-"+Long.toString(param.getUuid()));
		return param;
	}
	
//	public static ParametrosIniciales iniciarTransaccion(Long UUID) {
//		ParametrosIniciales param = new ParametrosIniciales();
//		ThreadContext.put("UUID", "FE-"+Long.toString(UUID));
//		return param;
//	}
	public static ParametrosIniciales iniciarTransaccion(String UUID) {
		ParametrosIniciales param = new ParametrosIniciales();
		ThreadContext.put("UUID", "FE-"+(UUID));
		return param;
	}

	/**
	 * Metodo encargado de inicializar o reinicializar las configuraciones del
	 * logger
	 * 
	 * @param isNew true si se trata de una nueva configuraci�n, false si es un
	 *              reinicio de configuraci�n
	 */
	private static void configurarLog(boolean isNew) {
		Propiedades prop = Propiedades.getInstance();
		File file = new File(prop.getPropiedad(UtilsConstantes.LOGGER_FILE));
		if (isNew) {
			Configurator.initialize(prop.getPropiedad(UtilsConstantes.LOGGER_NAME), null, file.toURI());
		} else {
			LoggerContext ctx = (LoggerContext) LogManager.getContext();
			ctx.setConfigLocation(file.toURI());
			ctx.reconfigure();
		}
	}

	/**
	 * Metodo encargado de generar en el Log el tiempo de procesamiento de la
	 * transaccion. Adicionalmente limpia la variable de UUID mostrada en el log.
	 * 
	 * @param param  Parametros calculados al inicio de la transaccion
	 * @param logger Logger de la aplicacion para imprimir el tiempo total de la
	 *               transaccion
	 */
	public static void cerrarTransaccion(ParametrosIniciales param, Logger logger) {
		logger.info("Tiempo de procesamiento (ms) " + (System.currentTimeMillis() - param.getTime()));
		ThreadContext.clearAll();
	}

	/**
	 * @return la ruta de propiedades
	 */
	public static String getRUTA_PROPIEDADES() {
		return RUTA_PROPIEDADES;
	}

	/**
	 * @return el nombre del logger
	 */
	public static String getNOMBRE_LOGGER() {
		return NOMBRE_LOGGER;
	}

	/**
	 * @return el nombre de la aplicaci�n
	 */
	public static String getNOMBRE_APLICACION() {
		return NOMBRE_APLICACION;
	}
}
