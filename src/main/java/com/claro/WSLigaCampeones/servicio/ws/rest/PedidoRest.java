package com.claro.WSLigaCampeones.servicio.ws.rest;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.multipart.MultipartFile;

import com.claro.WSLigaCampeones.dto.HistoricoRedencionRequest;
import com.claro.WSLigaCampeones.dto.HistoricoRedencionResponse;
import com.claro.WSLigaCampeones.dto.OutActualizarPedidoDto;
import com.claro.WSLigaCampeones.dto.OutHistoricoRedencionDto;
import com.claro.WSLigaCampeones.dto.OutPuntosGanadosDto;
import com.claro.WSLigaCampeones.dto.PedidoResponse;
import com.claro.WSLigaCampeones.dto.TblAuxActDetallePedidoDto;
import com.claro.WSLigaCampeones.dto.TblAuxDetallePedidoDto;
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
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
public class PedidoRest {

	private static Logger logger = LogManager.getLogger(UtilsConstantes.LOGGER_PRINCIPAL);
	private static Propiedades prop = Propiedades.getInstance();
	ParametrosIniciales ini;

	@ApiOperation(value = "Crea los pedidos", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path = "/pedidos/crearPedidos")
	ResponseEntity<?> crearPedidos(@RequestBody List<TblAuxDetallePedidoDto> tblAuxDetallePedidoDto,
			@RequestParam(name = "UUID", required = false) String UUID,
			@RequestParam(name = "UsuarioCrea", required = false) String UsuarioCrea,
			@RequestParam(name = "cedula", required = false) String cedula) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo la creacion del pedido cedula "+cedula+" request: "+tblAuxDetallePedidoDto.toString());
		PedidoResponse crearPedidoResponse = new PedidoResponse() ;
		try {
			if(tblAuxDetallePedidoDto.size()>1) {
				crearPedidoResponse.setCodigo(-1);
				crearPedidoResponse.setDescripcion("El carrito de compras no esta habilitado para la fase 1");
				logger.info("Response crear pedido "+crearPedidoResponse.toString());
				return ResponseEntity.ok(crearPedidoResponse);
			}
			if(tblAuxDetallePedidoDto.size()==0) {
				crearPedidoResponse.setCodigo(-1);
				crearPedidoResponse.setDescripcion("ingrese el pedido");
				logger.info("Response crear pedido "+crearPedidoResponse.toString());
				return ResponseEntity.ok(crearPedidoResponse);	
			}
			crearPedidoResponse = ServiciosBD.crearPedido(UsuarioCrea, cedula, tblAuxDetallePedidoDto);
			logger.info("request crear pedido"+crearPedidoResponse.toString());
			return ResponseEntity.ok(crearPedidoResponse);
		} catch (Exception e) {
			logger.error("Error inesperado ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}

	@ApiOperation(value = "Actualiza los pedidos", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping("pedidos/actualizarPedidos")
	public ResponseEntity<?> actualizarPedidos(@RequestParam("file") MultipartFile file,@RequestParam(name="usuarioEditor",required= true)String usuarioCreador,@RequestParam(name = "UUID", required = false) String UUID) throws Exception {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("REST request para cargarProductos");
		PedidoResponse crearPedidoResponse = null;
		if (!ExcelHelper.hasExcelFormat(file)) {
			throw new RuntimeException(
					"No se pudo realizar el cargue del archivo excel: Please upload an excel file!\"");
		}
		InputStreamResource fileRta = null;
		List<Row> estadoInvalidos = new ArrayList<Row>();

		try {
			
			ExcelHelper excelHelper = new ExcelHelper();
			excelHelper.loadFileExcel(file.getInputStream(), prop.getPropiedad(Constantes.NOMBRE_HOJA_1_PEDIDOS_HISTORICOS_REDENCION), 1);
			Iterator<Row> rows = excelHelper.getRows();
			List<TblAuxActDetallePedidoDto> tblAuxDetallePedidoDto = new ArrayList<TblAuxActDetallePedidoDto>();
			int i=1;
			while (rows.hasNext()) {
				Row currentRow = rows.next();
				if(i>1) {				
				TblAuxActDetallePedidoDto tblAuxActDetallePedidoDto = excelHelper.obtenerfilaArchivo(currentRow, 23);

				if (tblAuxActDetallePedidoDto == null) {
					break;
				}
				if(tblAuxActDetallePedidoDto.getEstado()!=null) {
				tblAuxDetallePedidoDto.add(tblAuxActDetallePedidoDto);
				}else {
					estadoInvalidos.add(currentRow);
				}
			}i++;

			}
			excelHelper.closeFileExcel();
			logger.info("request actualizar pedido "+tblAuxDetallePedidoDto.toString());
			crearPedidoResponse = ServiciosBD.actualizarPedidos(tblAuxDetallePedidoDto,usuarioCreador);
			logger.info("response actualizar pedidos "+crearPedidoResponse.toString());
			
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_PEDIDOS_HISTORICOS_REDENCION));// nombre
																													// hoja
			excel.crearFila(hoja, new String[] { "RESPUESTA ACTUALIZACION PEDIDOS" },
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR });
			List<String> cabecera = General.getNombresAtributos(OutActualizarPedidoDto.class);
			int fila = excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size() - 1);
			excel.crearFila(hoja, General.getNombresAtributos(OutActualizarPedidoDto.class),
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR, Constantes.APLICAR_BORDES });// cabecera

			for (OutActualizarPedidoDto outActualizarPedidoDto : crearPedidoResponse.getOutActualizarPedidoDto())
					 {
				excel.crearFila(hoja, General.getValuesByFields(outActualizarPedidoDto),
						new String[] { Constantes.APLICAR_BORDES });
			}
			
			for (Row invalidos : estadoInvalidos) {
				excel.crearFila(hoja, invalidos,
						new String[] { Constantes.APLICAR_BORDES });
			}
			fileRta = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();
			
		
		
		} catch (IOException e) {
			logger.error("No se pudo realizar el cargue del archivo excel:" + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e);
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
		String nombreArchivoDeDescarga = prop
				.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_PEDIDOS_HISTORICOS_REDENCION);// nombre archivo

		return  ResponseEntity.ok()
 				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
 				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(fileRta);
	
	}

	@ApiOperation(value = "Consulta los pedidos", authorizations = { @Authorization(value="jwtToken") })
	@PostMapping(path = "/pedidos/consultaPedidos")
	ResponseEntity<?> historicoRedenciones(@RequestParam(name = "UUID", required = false) String UUID,
			@RequestParam(name = "numPagina", required = false) Long numPagina,
			@RequestParam(name = "tamanoPagina", required = false) Long tamanoPagina,
			@RequestBody HistoricoRedencionRequest historicoRedencionRequest) {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de consultar pedido request: "+historicoRedencionRequest.toString());
		HistoricoRedencionResponse historicoRedencionResponse = null;
		try {
			historicoRedencionResponse = ServiciosBD.obtenerHistoricoRedenciones(historicoRedencionRequest, numPagina,
					tamanoPagina);
			logger.info("response historico de redenciones "+historicoRedencionResponse.toString());
			return ResponseEntity.ok(historicoRedencionResponse);
		} catch (Exception e) {
			logger.error("Error inesperado ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
			logger.info("--- FIN DE TRANSACCION ---");
			Configurador.cerrarTransaccion(ini, logger);
		}
	}
	@ApiOperation(value = "Descarga excel de pedidos", authorizations = { @Authorization(value="jwtToken") })
	@GetMapping(path = "/pedidos/descargar/consultaPedidos")
	ResponseEntity<?> descargarConsultaPedidos(@RequestParam(name = "UUID", required = false) String UUID,
			@RequestParam(name = "numPagina", required = false) Long numPagina,
			@RequestParam(name = "tamanoPagina", required = false) Long tamanoPagina,
			@RequestParam(required = false, name = "json") String json) throws Exception {
		ini = UUID == null ? Configurador.iniciarTransaccion() : Configurador.iniciarTransaccion(UUID);
		logger.info("--- INICIO TRANSACCION ---");
		logger.info("Se inicia consumo de procedimiento para consultar la auditoria de los usuarios ");
		InputStreamResource file = null;
		HistoricoRedencionRequest historicoRedencionRequest=new HistoricoRedencionRequest();

		if(json!=null && !json.equals("")) {
		JSONObject obj = new JSONObject(json);
		
		historicoRedencionRequest.setCedula(!obj.isNull("cedula") ? obj.getString("cedula"): null);
		historicoRedencionRequest.setCelular(!obj.isNull("celular") ? obj.getString("celular"): null);
		historicoRedencionRequest.setCiudad(!obj.isNull("ciudad") ? obj.getString("ciudad"): null);
		historicoRedencionRequest.setCorreo(!obj.isNull("correo") ? obj.getString("correo"): null);
		historicoRedencionRequest.setDepartamento(!obj.isNull("departamento") ? obj.getString("departamento"): null);
		historicoRedencionRequest.setEstado(!obj.isNull("estado") ? obj.getString("estado"): null);
		historicoRedencionRequest.setFechaPedido(!obj.isNull("fechaPedido") ? fechaSql(obj.getString("fechaPedido")): null);
		historicoRedencionRequest.setIdCiudad(!obj.isNull("idCiudad") ? obj.getLong("idCiudad"): null);
		historicoRedencionRequest.setIdDepartamento(!obj.isNull("idDepartamento") ? obj.getLong("idDepartamento"): null);
		historicoRedencionRequest.setIdProducto(!obj.isNull("idProducto") ? obj.getLong("idProducto"): null);
		historicoRedencionRequest.setNombre(!obj.isNull("nombre") ? obj.getString("nombre"): null);
		historicoRedencionRequest.setNumeroPedido(!obj.isNull("numeroPedido") ? obj.getLong("numeroPedido"): null);
		historicoRedencionRequest.setProducto(!obj.isNull("producto") ? obj.getString("producto"): null);
		}
		
		HistoricoRedencionResponse historicoRedencionResponse = null;

		try {
			logger.info("El request es: " + historicoRedencionRequest.toString());

			historicoRedencionResponse = ServiciosBD.obtenerHistoricoRedenciones(historicoRedencionRequest, numPagina,
					tamanoPagina);
			logger.info("response histrorico de redenciones "+historicoRedencionResponse.toString());
			ExcelHelper excel = new ExcelHelper();
			excel.crearDocEnBlanco();
			Sheet hoja = excel.crearHoja(prop.getPropiedad(Constantes.NOMBRE_HOJA_1_PEDIDOS_HISTORICOS_REDENCION));// nombre
																													// hoja
			excel.crearFila(hoja, new String[] { "HISTORICOS REDENCION" },
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR });
			List<String> cabecera = General.getNombresAtributos(OutHistoricoRedencionDto.class);
			int fila = excel.getContadorFila();
			excel.combinarCeldas(hoja, fila, fila, 0, cabecera.size() - 1);
			excel.crearFila(hoja, General.getNombresAtributos(OutHistoricoRedencionDto.class),
					new String[] { Constantes.NEGRITA, Constantes.CENTRAR, Constantes.APLICAR_BORDES });// cabecera

			for (OutHistoricoRedencionDto OutHistoricoRedencionDto : historicoRedencionResponse
					.getOutHistoricoRedencionDto()) {
				excel.crearFila(hoja, General.getValuesByFields(OutHistoricoRedencionDto),
						new String[] { Constantes.APLICAR_BORDES });
			}
			
			file = new InputStreamResource(excel.getFile());
			excel.closeFileExcel();

		} catch (Exception e) {
			logger.error("Error obteniendo el historico de redenciones: ", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		} finally {
		logger.info("--- FIN DE TRANSACCION ---");
		Configurador.cerrarTransaccion(ini, logger);
	}
		String nombreArchivoDeDescarga = prop
				.getPropiedad(Constantes.NOMBRE_ARCHIVO_DESCARGA_PEDIDOS_HISTORICOS_REDENCION);// nombre archivo
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + nombreArchivoDeDescarga)
				.contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(file);
	}
	
    public  Date fechaSql(String fecha) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date parsed = format.parse(fecha);
        java.sql.Date sqlFecha = new java.sql.Date(parsed.getTime());
		return sqlFecha;
    }

}