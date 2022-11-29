package com.claro.WSLigaCampeones.servicio.ws.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.CrudProductosRequest;
import com.claro.WSLigaCampeones.dto.CrudProductosResponse;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.ParametrosIniciales;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@Api(tags = "productos")
public class ProductosRest {

	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;

	@ApiOperation(value = "Realiza las operaciones crud de productos", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path = "/productos/crudProductos", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<?> crearPedidos(@RequestParam(name = "UUID", required = false) String UUID,
			@RequestBody() CrudProductosRequest crudProductosRequest,
			@RequestParam(name = "Accion", required = true) String accion,
			@RequestParam(name = "numPag", required = false) Long numPag,
			@RequestParam(name = "tamanoPag", required = false) Long tamanoPag) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo del crud producto accion= "+accion+" request: "+crudProductosRequest.toString());
		CrudProductosResponse crudProductosResponse = null;
		try {

			crudProductosResponse = ServiciosBD.crudProductos(crudProductosRequest, accion, numPag, tamanoPag);
			logger.info("request crud producto "+crudProductosResponse.toString());
			return ResponseEntity.ok(crudProductosResponse);
		} catch (Exception e) {
			logger.error("Error inesperado ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}
}