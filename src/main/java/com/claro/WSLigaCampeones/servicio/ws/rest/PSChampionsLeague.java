package com.claro.WSLigaCampeones.servicio.ws.rest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.claro.WSLigaCampeones.dto.ChampionsLeagueDto;
import com.claro.WSLigaCampeones.dto.ChampionsrequestDto;
import com.claro.WSLigaCampeones.dto.ResumenPuntosResponse;
import com.claro.WSLigaCampeones.util.bd.ServiciosBD;
import com.claro.WSLigaCampeones.util.configuracion.Configurador;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.ParametrosIniciales;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;
import com.claro.WSLigaCampeones.util.configuracion.UtilsConstantes;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import com.sun.jersey.api.json.JSONConfiguration;
import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.core.MediaType;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping(path = "/")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST, RequestMethod.DELETE})
public class PSChampionsLeague {
	
	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;
	
	@ApiOperation(value = "Consulta servicio rest champions", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path = "/champions/GetVendors", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> consultar(@RequestParam(name = "cedula", required = false)String cedula ,@RequestParam(required = false, name = "UUID") String UUID) throws Exception {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo del servicio rest PS_ChampionsLeagueV1.0_RS cedula: "+cedula);
		ChampionsLeagueDto championsLeagueDto=null;
		try {
			 championsLeagueDto=new ChampionsLeagueDto();
			ChampionsrequestDto championsrequestDto=new ChampionsrequestDto();
			championsrequestDto.setCc(cedula);
			ClientConfig clientConfig=new DefaultClientConfig();
			clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
			Client client=Client.create(clientConfig);
			WebResource webResource=client.resource(prop.getPropiedad(Constantes.URL_CHAMPIONS_LIGUE));
			ClientResponse clientResponse=webResource.type("application/json").post(ClientResponse.class,championsrequestDto);
			if(clientResponse.getClientResponseStatus()!=null && clientResponse.getClientResponseStatus().getStatusCode()==200) {
			championsLeagueDto=clientResponse.getEntity(ChampionsLeagueDto.class);
			logger.info("request PS_ChampionsLeagueV1.0_RS "+championsLeagueDto.toString());
			return ResponseEntity.ok(championsLeagueDto);
			}
			
			System.out.print(clientResponse.getClientResponseStatus());
			logger.error("Error consumiendo el legado: "+prop.getPropiedad(Constantes.URL_CHAMPIONS_LIGUE) + "  respuesta legado: "+clientResponse.getClientResponseStatus().getStatusCode());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error consumiendo el legado: "+prop.getPropiedad(Constantes.URL_CHAMPIONS_LIGUE));
		} catch (Exception e) {
			logger.error("Error inesperado ",e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}


}
