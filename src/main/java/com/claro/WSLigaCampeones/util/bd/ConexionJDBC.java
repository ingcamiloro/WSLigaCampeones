package com.claro.WSLigaCampeones.util.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import oracle.jdbc.OracleDriver;

/**
 * Desccripcion: Clase encargada de realizar la conexion a base de datos por
 * medio de JDBC
 * 
 * @author Esteban Camilo Beltran
 * @version 1.0.
 *
 */
public class ConexionJDBC {

	private static ConexionJDBC instance;
	private static Propiedades propiedades = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());

	/**
	 * Constuctor de la clase.
	 */
	private ConexionJDBC() {
	}

	/**
	 * Retorna la instancia del objeto ConexionJDBC
	 * 
	 * @return Un objeto ConexionJDBC para realizar la conexion a Base de Datos
	 */
	protected static ConexionJDBC getInstance() {
		synchronized (ConexionJDBC.class) {
			if (instance == null)
				instance = new ConexionJDBC();
		}
		return instance;
	}

	/**
	 * Retorna la conexion a la base de datos solicitada. Debe existir la
	 * configuracion de la base de datos en el archivo de porpiedades del jar. Ej:
	 * Si se desea realizar la conexion a la base de datos PRUEBA, debe existir la
	 * configuracion descrita en el archivo DataBaseSample.properties
	 * 
	 * @param dataBase Base de Datos a la cual se desea conectar
	 * @return Conexion a la Base de Datos solicitada
	 * @throws SQLException Genera excepcion cuando no pude realizar la conexion a
	 *                      la base de datos solicitada
	 */
	protected Connection getConnection(String dataBase) throws SQLException {

		Connection connection = null;

		String thin = propiedades.getPropiedad(UtilsConstantes.THIN_BD + dataBase);
		String host = propiedades.getPropiedad(UtilsConstantes.HOST_BD + dataBase);
		String port = propiedades.getPropiedad(UtilsConstantes.PORT_BD + dataBase);
		String sid = propiedades.getPropiedad(UtilsConstantes.SID_BD + dataBase);

		String flagRac = propiedades.getPropiedad(UtilsConstantes.RAC_BD + dataBase);
		String separador = UtilsConstantes.SEPARADOR;

		if (UtilsConstantes.IS_RAC.equalsIgnoreCase(flagRac)) {
			thin = thin + UtilsConstantes.SEPARADOR2 + UtilsConstantes.SEPARADOR2;
			separador = UtilsConstantes.SEPARADOR2;
		}

		String user = propiedades.getPropiedad(UtilsConstantes.USER_BD + dataBase);
		String pswd = propiedades.getPropiedad(UtilsConstantes.PASSWORD_BD + dataBase);

		logger.info(thin);
		String urlBD = thin + host + UtilsConstantes.SEPARADOR + port + separador + sid;
		logger.info(urlBD);
		DriverManager.registerDriver(new OracleDriver());
		connection = DriverManager.getConnection(urlBD, user, pswd);

		logger.debug("Conexion por JDBC exitosa a la  Base de Datos " + dataBase);

		return connection;
	}
}