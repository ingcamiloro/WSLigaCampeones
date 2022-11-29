package com.claro.WSLigaCampeones.servicio.ws.rest;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.HistoricoUsuarioDTO;
import com.claro.WSLigaCampeones.dto.HistoricoUsuarioRequest;
import com.claro.WSLigaCampeones.dto.HistoricoUsuarioResponse;
import com.claro.WSLigaCampeones.dto.OutPuntosGanadosDto;
import com.claro.WSLigaCampeones.dto.UsuariosRequest;
import com.claro.WSLigaCampeones.dto.UsuariosResponse;
import com.claro.WSLigaCampeones.dto.UsuariosSistemaDto;
import com.claro.WSLigaCampeones.dto.UsuariosSistemaDtosResponse;
import com.claro.WSLigaCampeones.dto.ValidarUsuarioResponse;
import com.claro.WSLigaCampeones.util.ExcelHelper;
import com.claro.WSLigaCampeones.util.General;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.ParametrosIniciales;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * <b>Nombre: </b> WSUsuarioRest </br>
 * <b>Descripci�n:</b> Clase que se encarga de desplegar el servicio REST </br>
 * <b>Fecha Creaci�n:</b> 07/09/2020 </br>
 * <b>Autor:</b> Juan Camilo Leal </br>
 * <b>Fecha de �ltima Modificaci�n: </b></br>
 * <b>Modificado por: </b></br>
 * <b>Brief: XXXX</b></br>
 */

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class UsuariosRest {

	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;

	@ApiOperation(value = "Consulta Usuarios registrados en el sistema", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path ="/users/admUsers" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> getUsuarios(
			@RequestParam(name = "UUID", required = false) String UUID
			,@RequestBody()UsuariosRequest usuario
			,@RequestParam(name="Accion",required= true)String Accion
			,@RequestParam(name="usuarioEditor",required= true)String usuarioEditor){
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para consulta de usuarios request: "+usuario.toString());
		try {
			if(Accion.equalsIgnoreCase("A") && (usuario==null ||usuario.getCambioClave()==null)) {
				return new ResponseEntity("El atributo cambioClave no puede ser nulo cuando se realiza una actualizacion",HttpStatus.BAD_REQUEST);
			}
			logger.info("El request es : "+usuario);
			ServiciosBD bd= new ServiciosBD(); 
			UsuariosResponse userResponse= new UsuariosResponse();
			bd.prcAdmUsuarios(usuario,userResponse,Accion, usuarioEditor);
			logger.info("La lista de usuarios obtenida es: "+userResponse.getCursorUsuarios());
			//-99 0 1no hay data
			if(userResponse.getCodigo() == prop.getIntPropiedad(Constantes.CODIGO + Constantes.ERROR_INESPERADO)) {
				return new ResponseEntity(userResponse,HttpStatus.CONFLICT);
			}
			if(userResponse.getCodigo()==prop.getIntPropiedad(Constantes.ERROR_ACCION_INVALIDA)) {
				return new ResponseEntity(userResponse,HttpStatus.BAD_REQUEST);
			}
//			if(userResponse.getCursorUsuarios().isEmpty()) {
//				return new ResponseEntity(userResponse,HttpStatus.NO_CONTENT);
//			}
			return ResponseEntity.ok(userResponse);
		} catch (SQLException e) {
			logger.error("Error conectando a base de daos",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		}
		catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		}finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}
	
	@ApiOperation(value = "", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path ="/users/validarUsuario" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ValidarUsuarioResponse> validarUsuario(
			@RequestParam(name = "UUID", required = false) String UUID,
			@RequestParam(name="cedula", required= true) String cedula,
			@RequestParam(name="clave", required= true) String clave
			){
		ValidarUsuarioResponse response = new ValidarUsuarioResponse();
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para validar el usuario ");
		try {
			logger.info("El request es cedula:"+cedula + " clave: "+clave);
			ServiciosBD.validarUsuario(cedula, clave, response);
		}catch (SQLException e) {
			logger.error("Se presentó error en el consumo del procedimiento de usuarios",e);
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
		} catch (Exception e) {
			logger.error("Error inesperado",e);
		}finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Consulta histrorico de usuarios", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path ="/users/consultar/historico" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<HistoricoUsuarioResponse> historicoUsuario(
			@RequestParam(name = "UUID", required = false) String UUID
			,@RequestParam(name="cedulaEditada", required= false) String cedulaEditada
			,@RequestParam(name="usuarioEditor", required= false) String usuarioEditor
			,@RequestParam(name="FechaInEdicion", required= false) Date fechaInEdicion
			,@RequestParam(name="FechaFinEdicion", required= false) Date fechaFinEdicion
			,@RequestParam(name="efectividadCambio", required= false) String efectividadCambio
			){
		HistoricoUsuarioResponse response = new HistoricoUsuarioResponse();
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para consultar la auditoria de los usuarios ");
		try {
			HistoricoUsuarioRequest historicoUsuario = new HistoricoUsuarioRequest();
			historicoUsuario.setCedulaEditada(cedulaEditada);
			historicoUsuario.setUsuarioEditor(usuarioEditor);
			historicoUsuario.setFechaInEdicion(fechaInEdicion);
			historicoUsuario.setFechaFinEdicion(fechaFinEdicion);
			historicoUsuario.setEfectividadCambio(efectividadCambio);
			logger.info("El request es: "+ historicoUsuario.toString());
			ServiciosBD.historicoUsuario(historicoUsuario, response);
			logger.info("reponse historico de usuario"+response.getHistorial().toString());
		}catch (SQLException e) {
			logger.error("Se presentó error en el consumo del procedimiento historicoUsuario",e);
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
		} catch (Exception e) {
			logger.error("Error inesperado",e);
		}finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		return ResponseEntity.ok(response);
	}
	
	@ApiOperation(value = "Descarga excel de Historico de usuario", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping("/users/descargar/historico")
    public ResponseEntity<Resource> descargarHistoricoUsuario(
    		@RequestParam(name = "UUID", required = false) String UUID
			,@RequestParam(name="cedulaEditada", required= false) String cedulaEditada
			,@RequestParam(name="usuarioEditor", required= false) String usuarioEditor
			,@RequestParam(name="FechaInEdicion", required= false) Date fechaInEdicion
			,@RequestParam(name="FechaFinEdicion", required= false) Date fechaFinEdicion
			,@RequestParam(name="efectividadCambio", required= false) String efectividadCambio
			) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para consultar la auditoria de los usuarios ");
		InputStreamResource file = null;
		try {
			HistoricoUsuarioRequest historicoUsuario = new HistoricoUsuarioRequest();
			historicoUsuario.setCedulaEditada(cedulaEditada);
			historicoUsuario.setUsuarioEditor(usuarioEditor);
			historicoUsuario.setFechaInEdicion(fechaInEdicion);
			historicoUsuario.setFechaFinEdicion(fechaFinEdicion);
			historicoUsuario.setEfectividadCambio(efectividadCambio);
			logger.info("El request es: "+ historicoUsuario.toString());
			
			HistoricoUsuarioResponse response = new HistoricoUsuarioResponse();
			response=ServiciosBD.historicoUsuario(historicoUsuario, response);
			
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_HISTORICO_USUARIO));//nombre hoja
			excel.crearFila(hoja, new String[] {"HISTORICO USUARIO"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabecera=General.getNombresAtributos(HistoricoUsuarioDTO.class);
			int fila=excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-2);
			excel.crearFila(hoja, General.getNombresAtributos(HistoricoUsuarioDTO.class),new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
			
			for (HistoricoUsuarioDTO historial : response.getHistorial()) {
				excel.crearFila(hoja, General.getValuesByFields(historial),new String[] {Constantes.APLICAR_BORDES});
			}
			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		} catch (Exception e) {
			logger.error("Se presentó error en el consumo del procedimiento historicoUsuario",e);			
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_HISTORICO_USUARIO);//nombre archivo
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
//	@ApiOperation(value = "genera excel de usuario registrados en el sistema", authorizations = { @Authorization(value="jwtToken") })
//	@GetMapping(path ="/users/descarga/admUsers" , produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity <?> getUsuariosDescarga(
//			@RequestParam(name = "UUID", required = false) String UUID
//			,@RequestParam(name="Accion",required= true)String Accion
//			,@RequestParam(name="usuarioEditor",required= true)String usuarioEditor){
//		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
//		logger.info("--- INICIO TRANSACCION ---");
//		logger.info("Se inicia consumo de procedimiento para consulta de usuarios request descarga: ");
//		
//		InputStreamResource file = null;
//		try {
//			UsuariosRequest usuario= new UsuariosRequest(); 
//			UsuariosResponse userResponse= new UsuariosResponse();
//			ServiciosBD.prcAdmUsuarios(usuario,userResponse,Accion, usuarioEditor);
//			logger.info("La lista de usuarios obtenida es: "+userResponse.getCursorUsuarios());
//			
//			
//			ExcelHelper excel = new ExcelHelper();
//			excel.crearDocEnBlanco();
//			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_USUARIOS_SISTEMA));//nombre hoja
//			excel.crearFila(hoja, new String[] {"USUARIOS DEL SISTEMA"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
//			List<String> cabecera=General.getNombresAtributosUsuarios(UsuariosRequest.class);
//			int fila=excel.getContadorFila();
//			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-1);
//			excel.crearFila(hoja, General.getNombresAtributosUsuarios(UsuariosRequest.class),new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
//			
//			
//			for (UsuariosRequest usuariosRequest : userResponse.getCursorUsuarios()) {
//				excel.crearFilaUsuarios(hoja, General.getValuesByFields(usuariosRequest),new String[] {Constantes.APLICAR_BORDES});
//			}
//			file = new InputStreamResource(excel.getFile());
//			excel.closeFileExcel();
//			
//			
//			
//			//-99 0 1no hay data
//			if(userResponse.getCodigo() == prop.getIntPropiedad(Constantes.CODIGO + Constantes.ERROR_INESPERADO)) {
//				return new ResponseEntity(userResponse,HttpStatus.CONFLICT);
//			}
//			if(userResponse.getCodigo()==prop.getIntPropiedad(Constantes.ERROR_ACCION_INVALIDA)) {
//				return new ResponseEntity(userResponse,HttpStatus.BAD_REQUEST);
//			}
//			String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_USUARIOS_SISTEMA);//nombre archivo
//			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
//	                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
//		} catch (SQLException e) {
//			logger.error("Error conectando a base de daos",e);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
//		}
//		catch (Exception e) {
//			logger.error("Error inesperado ",e);
//			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
//		}finally {
//			logger.info("--- FIN DE TRANSACCION ---");
//			Configurador.cerrarTransaccion(ini, logger);
//		}
//	}	
	@ApiOperation(value = "genera excel de usuario registrados en el sistema", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path ="/users/descarga/admUsers" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> getUsuariosDescarga(
			@RequestParam(name = "UUID", required = false) String UUID){
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de de descarga de puntos ganados request: " );
		InputStreamResource file = null;
		UsuariosSistemaDtosResponse usuariosSistemaDtosResponse = null;

		try {
			usuariosSistemaDtosResponse = ServiciosBD.optenerUsuariosSistema();
			logger.info("response puntos gandos " + usuariosSistemaDtosResponse.toString());

			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_USUARIOS_SISTEMA));// nombre hoja

			excel.crearFila(hoja, new String[] { "PUNTOS GANADOS" },
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR });
			List<String> cabecera = General.getNombresAtributos(UsuariosSistemaDto.class);
			int fila = excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size() - 1);
			excel.crearFila(hoja, cabecera,
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR, Constantes.APLICAR_BORDES });// cabecera

			for (UsuariosSistemaDto usuariosSistemaDto : usuariosSistemaDtosResponse.getUsuariosSistemaDtos()) {
				excel.crearFila(hoja, General.getValuesByFields(usuariosSistemaDto),
						new String[] { Constantes.APLICAR_BORDES });
			}

			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();

		} catch (Exception e) {
			logger.error("Error inesperado ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_USUARIOS_SISTEMA);// nombre
																												// archivo
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}


}
