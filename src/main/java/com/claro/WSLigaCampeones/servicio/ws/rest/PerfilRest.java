package com.claro.WSLigaCampeones.servicio.ws.rest;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.PerfilRequest;
import com.claro.WSLigaCampeones.dto.PerfilResponse;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.ParametrosIniciales;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class PerfilRest {
	
	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;
	
	@ApiOperation(value = "crud administracion de perfiles", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path ="/perfil/admPerfiles" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity <?> admLugares(
			@RequestParam(name = "UUID", required = false) String UUID
			,@RequestBody()PerfilRequest perfil
			,@RequestParam(name="Accion",required= true)String accion){
		
		PerfilResponse response = new PerfilResponse();
		
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo para de perfil request: "+perfil.toString());
		try {
			logger.info("El request es : "+ perfil.toString() +" -accion: "+ accion);
			ServiciosBD.admPerfiles(perfil, response, accion);
			logger.info("La lista de perfiles obtenida es: "+response.toString());
			return ResponseEntity.ok(response);
		}catch (SQLException e) {
			logger.error("Se presentó error en el consumo del procedimiento de PERFILES",e);
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

}
