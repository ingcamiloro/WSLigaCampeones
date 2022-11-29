package com.claro.WSLigaCampeones.util.bd;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

/**
 * Desccripcion: Clase encargada de realizar la conexion a base de datos por
 * medio de JNDI
 * 
 * @author Esteban Camilo Beltran
 * @version 1.0.
 *
 */
public class ConexionJNDI {

	private static ConexionJNDI instance;
	private static Propiedades propiedades = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());

	/**
	 * Constuctor de la clase.
	 */
	private ConexionJNDI() {
	}

	/**
	 * Retorna la instancia del objeto ConexionJNDI
	 * 
	 * @return Un objeto ConexionJNDI para realizar la conexion a Base de Datos
	 */
	protected static ConexionJNDI getInstance() {
		synchronized (ConexionJNDI.class) {
			if (instance == null)
				instance = new ConexionJNDI();
		}
		return instance;
	}

	/**
	 * Retorna la conexion al Data Source solicitado. Debe existir la configuracion
	 * del Data Source en el archivo de porpiedades del jar. Ej: Si se desea
	 * realizar la conexion al Data Source PRUEBA, debe existir la configuracion
	 * descrita en el archivo DataBaseSample.properties
	 * 
	 * @param dataSource Data Source al cual se desea conectar
	 * @return Conexion al Data Source solicitado
	 * @throws SQLException Genera excepcion cuando no pude realizar la conexion a
	 *                      la base de datos solicitada
	 */
	protected Connection getConnection(String dataSource) throws SQLException {

		Connection connection = null;

		Context context;
		try {
			context = new InitialContext();
			DataSource dsApp = (DataSource) context
					.lookup(propiedades.getPropiedad(UtilsConstantes.JNDI + dataSource));
			connection = dsApp.getConnection();

			logger.debug("Conexion por JNDI exitosa al Data Source " + dataSource);
		} catch (Exception e) {
			logger.error("Error obteniendo conexion al data source solicitado");
			throw new SQLException(e);
		}
		return connection;
	}
}
