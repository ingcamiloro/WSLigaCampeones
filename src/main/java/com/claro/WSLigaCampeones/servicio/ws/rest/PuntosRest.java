package com.claro.WSLigaCampeones.servicio.ws.rest;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.claro.WSLigaCampeones.dto.HistoricoRedencionRequest;
import com.claro.WSLigaCampeones.dto.HistoricoRedencionResponse;
import com.claro.WSLigaCampeones.dto.OutHistoricoRedencionDto;
import com.claro.WSLigaCampeones.dto.OutPuntosGanadosDto;
import com.claro.WSLigaCampeones.dto.PuntosGanadosRequest;
import com.claro.WSLigaCampeones.dto.PuntosGanadosResponse;
import com.claro.WSLigaCampeones.dto.ResumenPuntoDto;
import com.claro.WSLigaCampeones.dto.ResumenPuntosResponse;
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
public class PuntosRest {

	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;

	@ApiOperation(value = "Consulta los puntos ganados", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path = "/puntos/puntosGanados")
	ResponseEntity<?> puntosGanados(@RequestBody PuntosGanadosRequest puntosGanadosRequest, @RequestParam(name = "UUID", required = false) String UUID) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de puntos ganados request: "+puntosGanadosRequest.toString());
		PuntosGanadosResponse puntosGanadosResponse=null;
		try {

				puntosGanadosResponse = ServiciosBD.optenerPuntosGanados(puntosGanadosRequest);
				logger.info("response puntos ganados "+puntosGanadosResponse.getOutPuntosGanadosDto().toString());

			return ResponseEntity.ok(puntosGanadosResponse);
		} catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}	
	@ApiOperation(value = "Consulta el resumen de puntos", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path = "/puntos/resumenPuntos", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultar(@RequestParam(name = "UUID", required = false) String UUID,
			@RequestParam(required = false, name = "cedula") String cedula
			,@RequestParam(required = false, name = "numPag") Integer numPag
			,@RequestParam(required = false, name = "tamanoPag") Integer tamanoPag) throws Exception {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de resumen puntos cedula: "+cedula);
		try {
			ResumenPuntosResponse resumenPuntos = ServiciosBD.optenerResumenPuntos(cedula,numPag,tamanoPag);
			logger.info("reuqest resumen puntos ganados "+resumenPuntos.toString());

			return ResponseEntity.ok(resumenPuntos);
		}catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}
	
	@ApiOperation(value = "Descarga excel con los puntos ganados", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path = "/puntos/descargar/puntosGanados")
	ResponseEntity<?> descargarPuntosGanados(@RequestBody PuntosGanadosRequest puntosGanadosRequest, @RequestParam(name = "UUID", required = false) String UUID) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de de descarga de puntos ganados request: "+puntosGanadosRequest.toString());
		InputStreamResource file = null;
		PuntosGanadosResponse puntosGanadosResponse=null;

		try {
			logger.info("El request es: "+ puntosGanadosRequest.toString());
			
			puntosGanadosResponse = ServiciosBD.optenerPuntosGanados(puntosGanadosRequest);
			logger.info("response puntos gandos "+puntosGanadosResponse.toString());
			
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_PUNTOS_GANADOS));//nombre hoja
			
			excel.crearFila(hoja, new String[] {"PUNTOS GANADOS"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabecera=General.getNombresAtributos(OutPuntosGanadosDto.class);
			int fila=excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-1);
			excel.crearFila(hoja, cabecera,new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
			
			for (OutPuntosGanadosDto outPuntosGanadosDto : puntosGanadosResponse.getOutPuntosGanadosDto()) {
				excel.crearFila(hoja, General.getValuesByFields(outPuntosGanadosDto),new String[] {Constantes.APLICAR_BORDES});
			}
		

			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		} catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_PUNTOS_GANADOS);//nombre archivo
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	@ApiOperation(value = "Descarga excel con el resumen de los puntos ganados", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path = "/puntos/descargar/resumenPuntosGanados")
	ResponseEntity<?> descargarResumenPuntosGanados(@RequestParam(name = "UUID", required = false) String UUID,@RequestParam(required = false, name = "cedula") String cedula) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de resumen puntos ganados cedula: "+cedula);
		InputStreamResource file = null;
		try {
			logger.info("El request es: "+ cedula);
			
			ResumenPuntosResponse resumenPuntos = ServiciosBD.optenerResumenPuntos(cedula,null,null);
			logger.info("response resumen puntos ganados "+resumenPuntos.toString());
			
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_RESUMEN_PUNTOS_GANADOS));//nombre hoja
			
			excel.crearFila(hoja, new String[] {"RESUMEN PUNTOS GANADOS"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabecera=General.getNombresAtributos(ResumenPuntoDto.class);
			int fila=excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-1);
			excel.crearFila(hoja, cabecera,new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
			
			for (ResumenPuntoDto ResumenPuntoDto : resumenPuntos.getResumenPuntoDto()) {
				excel.crearFila(hoja, General.getValuesByFields(ResumenPuntoDto),new String[] {Constantes.APLICAR_BORDES});
			}
		

			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		} catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_RESUMEN_PUNTOS_GANADOS);//nombre archivo
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
	@ApiOperation(value = "descarga excel de canjes detalle", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping("/puntos/canjes/descargar/detalle")
    public ResponseEntity<?> descargarDetallePuntosYCanjes(
    		@RequestParam(name = "UUID", required = false) String UUID
			,@RequestParam(name="cedulaUsuario", required= false) String cedulaUsuario
			) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para consultar el detalle de puntos ganados y redimidos ");
		InputStreamResource file = null;
		try {
			logger.info("El request es: "+ cedulaUsuario);
			PuntosGanadosRequest request = new PuntosGanadosRequest();
			request.setCedulaUsuario(cedulaUsuario);
			PuntosGanadosResponse response = ServiciosBD.optenerPuntosGanados(request); 
			logger.info("response detalle canjes "+response.toString());
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_DETALLE_PUNTOS_CANJES));//nombre hoja
			
			//agregar info de puntos ganados
			excel.crearFila(hoja, new String[] {"PUNTOS GANADOS"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabecera=General.getNombresAtributos(OutPuntosGanadosDto.class);
			int fila=excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size()-1);
			excel.crearFila(hoja, General.getNombresAtributos(OutPuntosGanadosDto.class),new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});//cabecera
			for (OutPuntosGanadosDto puntosGanados : response.getOutPuntosGanadosDto()) {
				excel.crearFila(hoja, General.getValuesByFields(puntosGanados),new String[] {Constantes.APLICAR_BORDES});
			}
			
			//agregar info de puntos redimidos
			HistoricoRedencionRequest requestRedencion = new HistoricoRedencionRequest();
			requestRedencion.setCedula(cedulaUsuario);
			HistoricoRedencionResponse responseRedencion = ServiciosBD.obtenerHistoricoRedenciones(
					requestRedencion, null, null); 
			
			excel.setContadorFila(excel.getContadorFila()+5);//poner espacios
			excel.crearFila(hoja, new String[] {"PUNTOS REDIMIDOS"},new String[] {Constantes.NEGRITA,Constantes.CENTRAR});
			List<String> cabeceraPuntos=General.getNombresAtributos(OutHistoricoRedencionDto.class);
			int filaPuntos=excel.getContadorFila();
			 String[] valores=new String[] {"Cedula usuario","Fecha redencion", "producto", "Especificaciones del premio", "Puntos redimidos", "Estado", "Motivo rechazo"};
			excel.combinarCeldas(hoja, filaPuntos, filaPuntos, 0, valores.length-1);
			excel.crearFila(hoja,valores ,new String[] {Constantes.NEGRITA,Constantes.CENTRAR,Constantes.APLICAR_BORDES});
			for (OutHistoricoRedencionDto puntosRedimidos : responseRedencion.getOutHistoricoRedencionDto()) {
				excel.crearFila(hoja, new String[] {
										puntosRedimidos.getCedulaUsuario()
										,puntosRedimidos.getFechaPedido().toString()
										,puntosRedimidos.getNombreProducto()
										,( prop.getIntPropiedad(Constantes.MOSTRAR_INFORMACION_PRODUCTO) == 1? puntosRedimidos.getDescripcionCortaProducto(): puntosRedimidos.getInformacionDetalladaProducto())
										,String.valueOf(puntosRedimidos.getPuntos())
										, (puntosRedimidos.getEstado())
										,puntosRedimidos.getMotivoRechazo()
						},new String[] {Constantes.APLICAR_BORDES});
			}
			
			
			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		} catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_DETALLE_PUNTOS_CANJES);//nombre archivo
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
}

