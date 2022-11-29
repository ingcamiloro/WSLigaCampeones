package com.claro.WSLigaCampeones.util.bd;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Desccripcion: Clase encargada de realizar la creacion de los Pool de conexion
 * a base de datos y realizar la conexion al pool correspondiente
 * 
 * @author Esteban Camilo Beltran
 * @version 1.0.
 *
 */
public class ConexionPOOL {

	private static ConexionPOOL instance;
	private static Propiedades propiedades = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());
	private static final Map<String, OracleDataSource> dataSources = new HashMap<String, OracleDataSource>();

	/**
	 * Constuctor de la clase.
	 */
	private ConexionPOOL() {
	}

	/**
	 * Se encarga de realizar la creacion del Pool de conexion solicitado. Debe
	 * existir la configuracion del Pool en el archivo de porpiedades del jar. Ej:
	 * Si se desea realizar la conexion al Pool PRUEBA, debe existir la
	 * configuracion descrita en el archivo DataBaseSample.properties
	 * 
	 * @param dataBase Base de Datos a la cual se desea conectar
	 * @throws SQLException SQLException
	 */
	private void crearPool(String dataBase) throws SQLException {

		String thin = propiedades.getPropiedad(UtilsConstantes.THIN_POOL + dataBase);
		String host = propiedades.getPropiedad(UtilsConstantes.HOST_POOL + dataBase);
		String port = propiedades.getPropiedad(UtilsConstantes.PORT_POOL + dataBase);
		String sid = propiedades.getPropiedad(UtilsConstantes.SID_POOL + dataBase);

		String flagRac = propiedades.getPropiedad(UtilsConstantes.RAC_POOL + dataBase);
		String separador = UtilsConstantes.SEPARADOR;

		if (UtilsConstantes.IS_RAC.equalsIgnoreCase(flagRac)) {
			thin = thin + UtilsConstantes.SEPARADOR2 + UtilsConstantes.SEPARADOR2;
			separador = UtilsConstantes.SEPARADOR2;
		}

		String urlBD = thin + host + UtilsConstantes.SEPARADOR + port + separador + sid;

		String user = propiedades.getPropiedad(UtilsConstantes.USER_POOL + dataBase);
		String pswd = propiedades.getPropiedad(UtilsConstantes.PASSWORD_POOL + dataBase);

		Properties prop = new Properties();
		prop.setProperty(UtilsConstantes.MIN_LIMIT_DS,
				propiedades.getPropiedad(UtilsConstantes.MIN_LIMIT + dataBase));
		prop.setProperty(UtilsConstantes.MAX_LIMIT_DS,
				propiedades.getPropiedad(UtilsConstantes.MAX_LIMIT + dataBase));
		prop.put(UtilsConstantes.USER_DS, user);
		prop.put(UtilsConstantes.PASSWORD_LIMIT_DS, pswd);

		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setURL(urlBD);
		dataSource.setUser(user);
		dataSource.setPassword(pswd);
		dataSource.setConnectionCachingEnabled(true);
		dataSource.setConnectionCacheProperties(prop);

		dataSources.put(dataBase, dataSource);

		logger.debug("Creacion de pool exitosa para la conexion a la  Base de Datos " + dataBase);
	}

	/**
	 * Retorna la instancia del objeto ConexionPool
	 * 
	 * @return Un objeto ConexionPool para realizar la conexion a Base de Datos al
	 *         Pool solicitado
	 */
	protected static ConexionPOOL getInstance() {
		synchronized (ConexionPOOL.class) {
			if (instance == null)
				instance = new ConexionPOOL();
		}
		return instance;
	}

	/**
	 * Retorna la conexion al Pool solicitado.
	 * 
	 * @param dataBase: Pool al cual se desea conectar
	 * @return Conexion al Pool solicitado
	 * @throws SQLException
	 */
	protected synchronized Connection getConnection(String dataBase) throws SQLException {

		Connection connection = null;

		if (!dataSources.containsKey(dataBase)) {
			crearPool(dataBase);
		}
		connection = dataSources.get(dataBase).getConnection();
		logger.debug("Conexion por pool exitosa a la Base de Datos " + dataBase);

		return connection;
	}
}
