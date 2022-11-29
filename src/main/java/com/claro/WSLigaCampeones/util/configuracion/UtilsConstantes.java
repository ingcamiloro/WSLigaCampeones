package com.claro.WSLigaCampeones.util.configuracion;

/**
 * Desccripcion: Clase que almacena la constantes utilizadas por el jar.
 * 
 * @author Esteban Camilo Beltran
 * @version 1.0.
 *
 */
public abstract class UtilsConstantes {

	public static final String LOGGER_PRINCIPAL = "appLogger";
	public static final String LOGGER_TYPE_XML = "XML";
	public static final String LOGGER_TYPE_PROPERTIES = "PROP";
	public static final String CONEXION_JDBC = "JDBC";
	public static final String CONEXION_JNDI = "JNDI";
	public static final String CONEXION_POOL = "POOL";
	public static final String SEPARADOR = ":";
	public static final String SEPARADOR2 = "/";
	public static final String IGUAL = "=";
	public static final String IS_RAC = "1";
	public static final String CONSULTA_PROPIEDADES = "sql.tbl.properties.consulta";
	public static final String PROPIEDAD = "PROPIEDAD";
	public static final String VALOR = "VALOR";

	// JDBC
	public static final String THIN_BD = "data.base.jdbc.thin.oracle.";
	public static final String HOST_BD = "data.base.jdbc.host.";
	public static final String PORT_BD = "data.base.jdbc.port.";
	public static final String SID_BD = "data.base.jdbc.sid.";
	public static final String RAC_BD = "data.base.jdbc.flag.rac.";
	public static final String USER_BD = "data.base.jdbc.user.";
	public static final String PASSWORD_BD = "data.base.jdbc.password.";

	// JNDI
	public static final String JNDI = "data.base.jndi.data.source.";

	// POOL
	public static final String MIN_LIMIT = "data.base.pool.min.limit.";
	public static final String MAX_LIMIT = "data.base.pool.max.limit.";
	public static final String THIN_POOL = "data.base.pool.thin.oracle.";
	public static final String HOST_POOL = "data.base.pool.host.";
	public static final String PORT_POOL = "data.base.pool.port.";
	public static final String SID_POOL = "data.base.pool.sid.";
	public static final String RAC_POOL = "data.base.pool.flag.rac.";
	public static final String USER_POOL = "data.base.pool.user.";
	public static final String PASSWORD_POOL = "data.base.pool.password.";
	public static final String MIN_LIMIT_DS = "MinLimit";
	public static final String MAX_LIMIT_DS = "MaxLimit";
	public static final String USER_DS = "user";
	public static final String PASSWORD_LIMIT_DS = "password";

	// LOG4J
	public static final String LOGGER_FILE = "data.base.logger.file.config";
	public static final String LOGGER_NAME = "data.base.logger.name.config";

	public static final String BD_PROPERTIES = "data.base.cargue.properties";
	public static final String TIPO_BD_PROPERTIES = "data.base.cargue.properties.tipo";
	public static final String KEY_USUARIO = "usuario.refresh";
	public static final String KEY_PSW = "psw.refresh";

	// Consultas
	public static final String VALIDA_USUARIO = "app.consulta.valida.usuario.psw";

	public static final String DATOS_INC_RESET = "El usuario y la Contrase�a son obligatorios";
	public static final String DATOS_VAL_RESET = "Se ha realizado la actualizacion de las propiedades del sistema. Si desea verificar las propiedades, revise el archivo de Log.";
	public static final String DATOS_INVAL_RESET = "Usuario y/o contrase�a invalidos. Intente nuevamente.";
	public static final String ERROR_RESET = "No es posible recargar las propiedades del sistema. Intente nuevamente";

	public static final Integer RTA_EXITOSA = 1;
}