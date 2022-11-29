package com.claro.WSLigaCampeones.util.configuracion;

/**
 * <b>Nombre: </b> Constantes </br>
 * <b>Descripci�n:</b> Clase que almacena todas las constantes de la aplicaci�n
 * </br>
 * <b>Fecha Creaci�n:</b> 22/02/2018 </br>
 * <b>Autor:</b> HITSS Nombre Ingeniero </br>
 * <b>Fecha de �ltima Modificaci�n: 08/09/2020</b></br>
 * <b>Modificado por: Melkin Jose Mejia</b></br>
 * <b>Brief: XXXX</b></br>
 */

public class Constantes {
	
	public static final String RUTA_ARCHIVO_PROPIEDADES = "/applications/config/EAF/CO_Claro_IntCus_EAF_Domain_PR/config/WSLigaCampeones/WSLigaCampeones.properties";

//	public static final String RUTA_ARCHIVO_PROPIEDADES = "C:/applications/config/EAF/CO_Claro_IntCusOper_EAF_Domain_PR/config/WSLigaCampeones/WSLigaCampeones1.properties";
	public static final String APLICACION = "WSLigaCampeones";

	// Bandera para autenticacion de usuarios
	public static final String FLAG_AUTH = "app.flag.auth";
	public static final String AUTENTICAR = "1";
	public static final String CENTRAR = "centrar";
	public static final String APLICAR_BORDES = "aplicarBordes";
	public static final String NEGRITA = "negrita";

	// Bases de datos
	public static final String SERCON = "app.conexion.SERCON";
	public static final String TIPO_CON_SERCON = "app.tipo.conexion.SERCON";

	// Consultas base de datos
	public static final String TIME_OUT = "app.sql.time.out";
	public static final String PRC_ADM_USUARIOS = "app.sql.admUsuarios";
	public static final String PRC_VALIDAR_USUARIO = "app.sql.validar.usuario";
	public static final String PRC_ADM_LUGARES = "app.sql.admLugares";
	public static final String PRC_ADM_CATEGORIAS = "app.sql.admCategorias";
	public static final String PRC_HISTORICO_USUARIO = "app.sql.historico.usuario";
	public static final String PRC_ADM_REFERIDO = "app.sql.admReferido";
	public static final String PRC_ADM_PERFILES = "app.sql.admPerfiles";
	public static final String PRC_ADM_PERFIL_X_CATEGORIA = "app.sql.admPerfilXCategoria";
	public static final String SP_ADM_USUARIOS_DEL_SISTEMA = "app.sql.usuarios.sistema";
	
	
	// Consultas servicios web
	public static final String TIME_OUT_WS1 = "app.timeout.ws.ws1";

	// Excepciones
	public static final String EXCEPTION = "app.exception.";
	public static final String _000 = "000";
	public static final String _100 = "100";
	public static final String _101 = "101";
	public static final String _102 = "102";

	// Respuestas
	public static final String CODIGO = "app.cod.";
	public static final String DESCRIPCION = "app.des.";
	public static final String MENSAJE = "app.msj.";

	public static final String PROCESO_EXITOSO = "001";
	public static final String PROCESO_EXITOSO_DOS = "002";
	public static final String ERROR_VALIDACION_3 = "100";
	public static final String ERROR_PARAMETROS_INCOMPLETOS = "900";
	public static final String ERROR_NEGOCIO = "901";
	public static final String ERROR_INESPERADO = "999";
	public static final String ERROR_AUTENTICACION = "1000";

	// Otros
	public static final String VALOR_SERVICIO = "app.valor.servicio";
	public static final String DIAS_VIGENCIA = "app.dias.vigencia";

	public static final String USSD = "USSD";
	public static final String SMS = "SMS";
	public static final String LOGGER_PRINCIPAL = "WSUsuario";
	public static final String ACCION_CONSULTA = "C";
	public static final String ERROR_ACCION_INVALIDA = "app.valor.accion.inesperada";
	
	public static final String SP_HISTORICO_REDENCION = "SP_HISTORICO_REDENCION";
	public static final String SP_PUNTOS_GANADOS = "SP_PUNTOS_GANADOS";
	public static final String PRC_CREAR_PEDIDO = "PRC_CREAR_PEDIDO";
	public static final String TBL_AUX_DETALLE_PED = "TBL_AUX_DETALLE_PED";
	public static final String PRC_ACTUALIZAR_ESTADO_PEDIDO = "PRC_ACTUALIZAR_ESTADO_PEDIDO";
	
	public static final String NOMBRE_ARCHIVO_DESCARGA_HISTORICO_USUARIO = "app.nombre.archivo.descarga.historico.usuario";
	public static final String NOMBRE_HOJA_1_HISTORICO_USUARIO = "app.nombre.hoja1.historico.usuario";
	public static final String NOMBRE_HOJA_1_USUARIOS_SISTEMA= "app.nombre.hoja1.usuarios.sistema";
	public static final String NOMBRE_ARCHIVO_DESCARGA_USUARIOS_SISTEMA = "app.nombre.archivo.descarga.usuarios.sistema";
	public static final String NOMBRE_ARCHIVO_DESCARGA_REFERIDO = "app.nombre.archivo.descarga.referido";
	public static final String NOMBRE_HOJA_1_REFERIDOS = "app.nombre.archivo.hoja.descarga.referido";
	public static final String NOMBRE_HOJA_1_PUNTOS_RESTANTES = "app.nombre.hoja1.puntos.restantes";
	public static final String NOMBRE_ARCHIVO_DESCARGA_PUNTOS_RESTANTES = "app.nombre.archivo.descarga.puntos.restantes";



	public static final String NOMBRE_ARCHIVO_DESCARGA_HISTORICO_REDENCION = "app.nombre.archivo.descarga.historico.redencion";
	public static final String NOMBRE_HOJA_1_HISTORICO_REDENCION = "app.nombre.hoja1.historisco.redencion";
	public static final String NOMBRE_ARCHIVO_DESCARGA_DETALLE_PUNTOS_CANJES = "app.nombre.archivo.descarga.puntos.canjes";
	public static final String NOMBRE_HOJA_1_DETALLE_PUNTOS_CANJES = "app.nombre.hoja1.puntos.canjes";
	public static final String PRC_ADM_PRODUCTOS = "PRC_ADM_PRODUCTOS";
	public static final String PRC_RESUMEN_PUNTOS = "PRC_RESUMEN_PUNTOS";
	public static final String TBL_AUX_ACT_DETALLE_PED = "TBL_AUX_ACT_DETALLE_PED";
	
	public static final String NOMBRE_ESTADO_DETALLE_PEDIDO = "app.nombre.estado.detalle.app.nombre.hoja1.pedidos.historico.redencionespedido.";
	public static final String MOSTRAR_INFORMACION_PRODUCTO = "app.informacion.producto" ;
	public static final String NOMBRE_HOJA_1_PEDIDOS_HISTORICOS_REDENCION = "app.nombre.hoja1.pedidos.historico.redenciones";
	public static final String NOMBRE_ARCHIVO_DESCARGA_PEDIDOS_HISTORICOS_REDENCION = "app.nombre.archivo.descarga.pedidos.historico.redenciones";
	public static final String NOMBRE_HOJA_1_PUNTOS_GANADOS = "app.nombre.hoja1.puntos.ganados";
	public static final String NOMBRE_ARCHIVO_DESCARGA_PUNTOS_GANADOS = "app.nombre.archivo.descarga.puntos.ganados";
	public static final String URL_CHAMPIONS_LIGUE = "url.champions.ligue";
	public static final String NOMBRE_HOJA_1_RESUMEN_PUNTOS_GANADOS = "app.nombre.hoja1.resumen.puntos.ganados";
	public static final String NOMBRE_ARCHIVO_DESCARGA_RESUMEN_PUNTOS_GANADOS = "app.nombre.archivo.descarga.resumen.puntos.ganados";
	public static final String ESTADO_EFECTIVO = "app.estado.efectivo";
	public static final String ESTADO_NUMERICO_EFECTIVO = "app.estado.numerico.efectivo";
	public static final String ESTADO_NUMERICO_RECHAZADO = "app.estado.numerico.rechazado";
	public static final String ESTADO_RECHAZADO = "app.estado.rechazado";
	public static final String ESTADO_NUMERICO_PENDIENTE = "app.estado.numerico.pendiente";
	public static final String ESTADO_PENDIENTE = "app.estado.pendiente";
	public static final String ACCION_UPDATE_LARGA = "app.accion.update.larga";
	public static final String ACCION_INSERT_LARGA = "app.accion.insert.larga";
	public static final String ACCION_DELETE_LARGA = "app.accion.delete.larga";
	public static final String ACCION_UPDATE_CORTA = "app.accion.update.corta";
	public static final String ACCION_INSERT_CORTA = "app.accion.insert.corta";
	public static final String ACCION_DELETE_CORTA = "app.accion.delete.corta";
	public static final String ACCION_CONSULTA_LARGA = "app.accion.consulta.larga";
	public static final String ACCION_CONSULTA_CORTA = "app.accion.consulta.corta";
	
	public static final String CLAVE_ENCRIPTACION = "app.clave.encriptacion";
	
	
}
