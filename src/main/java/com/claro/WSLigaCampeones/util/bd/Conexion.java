package com.claro.WSLigaCampeones.util.bd;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

public class Conexion {

	private static Conexion instance;
	private static Logger logger = LogManager.getLogger(Configurador.getNOMBRE_LOGGER());

	/**
	 * Constuctor de la clase.
	 */
	Conexion() {
	}

	/**
	 * Retorna la instancia del objeto Conexion
	 * 
	 * @return Un objeto Conexion para realizar la conexion a Base de Datos
	 */
	public static Conexion getInstance() {
		synchronized (Conexion.class) {
			if (instance == null)
				instance = new Conexion();
		}
		return instance;
	}

	/**
	 * Retorna la conexion a la base de datos solicitada, dependiendo del tipo de
	 * conexion solicitada. TiposConexion.JDBC, TiposConexion.JNDI,
	 * TiposConexion.POOL
	 * 
	 * @param dataBase     Base de Datos a la cual se desea conectar
	 * @param tipoConexion Tipo de conexion a la base de datos (JDBC, JNDI, POOL)
	 * @return Conexion a la Base de Datos solicitada
	 * @throws SQLException Genera excepcion cuando no pude realizar la conexion a
	 *                      la base de datos solicitada
	 */
	public Connection getConnection(String dataBase, String tipoConexion) throws SQLException {

		if (UtilsConstantes.CONEXION_JDBC.equalsIgnoreCase(tipoConexion))
			return ConexionJDBC.getInstance().getConnection(dataBase);
		else if (UtilsConstantes.CONEXION_JNDI.equalsIgnoreCase(tipoConexion))
			return ConexionJNDI.getInstance().getConnection(dataBase);
		else if (UtilsConstantes.CONEXION_POOL.equalsIgnoreCase(tipoConexion))
			return ConexionPOOL.getInstance().getConnection(dataBase);

		logger.error("Tipo de conexion no configurado");
		return null;
	}

	/**
	 * Realiza el cierre de la conexion a la Base de Datos. Controla las excepiones
	 * que puedan generarse en el cierre.
	 * 
	 * @param conn Conexion que se va a cerrar
	 */
	public static void cerrar(Connection conn) {
		try {
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			logger.error("Error cerrando la conexion", e);
		}
	}

	/**
	 * Realiza el cierre del CallableStatement. Controla las excepiones que puedan
	 * generarse en el cierre.
	 * 
	 * @param cs CallableStatement que se va a cerrar
	 */
	public static void cerrar(CallableStatement cs) {
		try {
			if (cs != null)
				cs.close();
		} catch (Exception e) {
			logger.error("Error cerrando el CallableStatement", e);
		}
	}

	/**
	 * Realiza el cierre del PreparedStatement. Controla las excepiones que puedan
	 * generarse en el cierre.
	 * 
	 * @param ps PreparedStatement que se va a cerrar
	 */
	public static void cerrar(PreparedStatement ps) {
		try {
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			logger.error("Error cerrando el PreparedStatement", e);
		}
	}

	/**
	 * Realiza el cierre del ResultSet. Controla las excepiones que puedan generarse
	 * en el cierre.
	 * 
	 * @param rs ResultSet que se va a cerrar
	 */
	public static void cerrar(ResultSet rs) {
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			logger.error("Error cerrando el ResultSet", e);
		}
	}
}
