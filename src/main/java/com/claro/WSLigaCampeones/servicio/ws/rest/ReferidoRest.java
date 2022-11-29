package com.claro.WSLigaCampeones.servicio.ws.rest;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.ReferidosRequest;
import com.claro.WSLigaCampeones.dto.ReferidosResponse;
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

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class ReferidoRest {
	
	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;
	
	@ApiOperation(value = "Administracion del crud de referidos ", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path ="/referidos/admReferidos" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <ReferidosResponse> admReferidos(
			@RequestParam(name = "UUID", required = false) String UUID
			,@RequestBody()ReferidosRequest referidosRequest
			,@RequestParam(name="Accion",required= true)String accion){
		
		ReferidosResponse response = new ReferidosResponse();
		
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo para descarga de referidos request: "+referidosRequest.toString());
		try {
			logger.info("El request es : "+ referidosRequest.toString() +" -accion: "+ accion);
			ServiciosBD.admReferidos(referidosRequest, response, accion);
			logger.info("La lista de lugares obtenida es: "+response.toString());
			return ResponseEntity.ok(response);
		}catch (SQLException e) {
			logger.error("Se presentó error en el consumo del procedimiento de CATEGORIAS",e);
			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);
		} catch (Exception e) {
			logger.error("Error inesperado",e);
//			return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).body(response);

		}finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		return ResponseEntity.ok(response);
	}
	
	

	@ApiOperation(value = "Descarga excel de referidos", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping("/referidos/descargar")
    public ResponseEntity<Resource> descargarHistoricoUsuario(
    		@RequestParam(name = "UUID", required = false) String UUID
			,@RequestParam(name="cedula", required= false) String cedula) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para descargar el excel de referidos cedula: "+cedula);
		InputStreamResource file = null;
		try {
			ReferidosRequest referidosRequest = new ReferidosRequest();
			referidosRequest.setCedula(cedula);
	
			logger.info("El request es: "+ referidosRequest.toString());
			
			ReferidosResponse response = new ReferidosResponse();
			response=ServiciosBD.admReferidos(referidosRequest, response,"C");
			
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_REFERIDOS));//nombre hoja
			excel.crearFila(hoja, new String[] {"REFERIDOS"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabecera=General.getNombresAtributos(ReferidosRequest.class);
			int fila=excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-2);
			excel.crearFila(hoja, General.getNombresAtributos(ReferidosRequest.class),new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
			
			for (ReferidosRequest referido : response.getListaReferidos()) {
				excel.crearFila(hoja, General.getValuesByFields(referido),new String[] {Constantes.APLICAR_BORDES});
			}
			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		} catch (Exception e) {
			logger.error("Se presentó error en el consumo del procedimiento referidos",e);			
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_REFERIDO);//nombre archivo
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}

}
