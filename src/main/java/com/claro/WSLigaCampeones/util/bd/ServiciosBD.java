package com.claro.WSLigaCampeones.util.bd;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.claro.WSLigaCampeones.dto.CategoriasRequest;
import com.claro.WSLigaCampeones.dto.CategoriasResponse;
import com.claro.WSLigaCampeones.dto.CrudProductosDto;
import com.claro.WSLigaCampeones.dto.CrudProductosRequest;
import com.claro.WSLigaCampeones.dto.CrudProductosResponse;
import com.claro.WSLigaCampeones.dto.HistoricoRedencionRequest;
import com.claro.WSLigaCampeones.dto.HistoricoRedencionResponse;
import com.claro.WSLigaCampeones.dto.HistoricoUsuarioDTO;
import com.claro.WSLigaCampeones.dto.HistoricoUsuarioRequest;
import com.claro.WSLigaCampeones.dto.HistoricoUsuarioResponse;
import com.claro.WSLigaCampeones.dto.LugarDaneRequest;
import com.claro.WSLigaCampeones.dto.LugarDaneResponse;
import com.claro.WSLigaCampeones.dto.OutActualizarPedidoDto;
import com.claro.WSLigaCampeones.dto.OutHistoricoRedencionDto;
import com.claro.WSLigaCampeones.dto.OutPuntosGanadosDto;
import com.claro.WSLigaCampeones.dto.PedidoResponse;
import com.claro.WSLigaCampeones.dto.PerfilRequest;
import com.claro.WSLigaCampeones.dto.PerfilResponse;
import com.claro.WSLigaCampeones.dto.PerfilXCategoriaResponse;
import com.claro.WSLigaCampeones.dto.PerfilXCategoriarequest;
import com.claro.WSLigaCampeones.dto.PuntosGanadosRequest;
import com.claro.WSLigaCampeones.dto.PuntosGanadosResponse;
import com.claro.WSLigaCampeones.dto.ReferidosRequest;
import com.claro.WSLigaCampeones.dto.ReferidosResponse;
import com.claro.WSLigaCampeones.dto.ResumenPuntoDto;
import com.claro.WSLigaCampeones.dto.ResumenPuntosResponse;
import com.claro.WSLigaCampeones.dto.TblAuxActDetallePedidoDto;
import com.claro.WSLigaCampeones.dto.TblAuxDetallePedidoDto;
import com.claro.WSLigaCampeones.dto.UsuariosRequest;
import com.claro.WSLigaCampeones.dto.UsuariosResponse;
import com.claro.WSLigaCampeones.dto.UsuariosSistemaDto;
import com.claro.WSLigaCampeones.dto.UsuariosSistemaDtosResponse;
import com.claro.WSLigaCampeones.dto.ValidarUsuarioResponse;
import com.claro.WSLigaCampeones.security.com.claro.WSLigaCampeones.security.encriptadoaes.EncriptadorAES;
import com.claro.WSLigaCampeones.util.configuracion.Constantes;
import com.claro.WSLigaCampeones.util.configuracion.Propiedades;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleConnection;
import oracle.jdbc.OracleTypes;

public class ServiciosBD {

	private static Propiedades prop = Propiedades.getInstance();
	private static Logger logger = LogManager.getLogger(Constantes.LOGGER_PRINCIPAL);
	
	public static String pass(String Password) throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {

		String clave = prop.getPropiedad(Constantes.CLAVE_ENCRIPTACION);
		EncriptadorAES encriptador = new EncriptadorAES();

		String claveCifrada = encriptador.encriptar(Password, clave);

		String desencriptado = encriptador.desencriptar(claveCifrada, clave);

		System.out.println("clave claveCifrada: " + claveCifrada + " clave descifrada " + desencriptado);
		return claveCifrada;
 
	  }

	public static void prcAdmUsuarios(UsuariosRequest request, UsuariosResponse response, String accion,
			String usuarioEditor) throws Exception {
		logger.debug("Ingresa a prcAdminUsuarios");
		String consultaSql = prop.getPropiedad(Constantes.PRC_ADM_USUARIOS);
		ResultSet rs = null;
		Connection con = null;
		CallableStatement stmt = null;
		try {
			con = new Conexion().getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
					prop.getPropiedad(Constantes.TIPO_CON_SERCON));

			stmt = con.prepareCall(consultaSql);
			logger.info("Sentencia sql: " + consultaSql);
			mapearCampos(request, stmt, accion, usuarioEditor);
			// logger.info("Request:"+stmt.getInt(5)+" "+stmt.getInt(9)+"
			// "+stmt.getInt(10));
			stmt.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			logger.info("Se consume el procedimiento con los parametros: " + "Accion: " + accion);
			long timeIni = System.currentTimeMillis();
			stmt.execute();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(stmt.getInt(14));
			response.setResultado(stmt.getString(15));

			rs = ((OracleCallableStatement) stmt).getCursor(16);

			ArrayList<UsuariosRequest> cursorUsuarios = new ArrayList<UsuariosRequest>();
			while (rs != null && rs.next()) {
				UsuariosRequest usuario = new UsuariosRequest();
				usuario.setCedula(rs.getString(1));
				usuario.setNombres(rs.getString(2));
				usuario.setApellido(rs.getString(3));
				usuario.setCanal(rs.getString(4));
				usuario.setCelular(rs.getLong(5));
				usuario.setIdCiudad(rs.getInt(6));
				usuario.setDireccion(rs.getString(7));
				usuario.setBarrio(rs.getString(8));
				usuario.setCorreo(rs.getString(9));
				usuario.setClave(rs.getString(10));
				usuario.setAdmin(rs.getInt(11));
				cursorUsuarios.add(usuario);
			}
			response.setCursorUsuarios(cursorUsuarios);
			logger.info("El resultado de la consulta es : " + response.getCodigo() + " " + response.getResultado() + " "
					+ response.getCursorUsuarios());
		}  finally {
			
				if (rs != null)
					rs.close();
				if (stmt != null)
					stmt.close();
				if (con != null)
					con.close();
			} 

		}



	private static CallableStatement mapearCampos(UsuariosRequest request, CallableStatement stmt, String accion,
			String usuarioEditor) {
		try {
			if (request.getCedula() != null) {
				stmt.setString(1, request.getCedula());
			} else {
				stmt.setNull(1, Types.VARCHAR);
			}
			if (request.getNombres() != null) {
				stmt.setString(2, request.getNombres());
			} else {
				stmt.setNull(2, Types.VARCHAR);
			}
			if (request.getApellido() != null) {
				stmt.setString(3, request.getApellido());
			} else {
				stmt.setNull(3, Types.VARCHAR);
			}
			if (request.getCanal() != null) {
				stmt.setString(4, request.getCanal());
			} else {
				stmt.setNull(4, Types.VARCHAR);
			}
			if (request.getCelular() != null) {
				stmt.setLong(5, request.getCelular());
			} else {
				stmt.setNull(5, java.sql.Types.FLOAT);
			}
			if (request.getCorreo() != null) {
				stmt.setString(6, request.getCorreo());
			} else {
				stmt.setNull(6, Types.VARCHAR);
			}
			if (request.getDireccion() != null) {
				stmt.setString(7, request.getDireccion());
			} else {
				stmt.setNull(7, Types.VARCHAR);
			}
			if (request.getClave() != null) {
				stmt.setString(8, accion.equalsIgnoreCase("A") && request.getCambioClave() ? pass(request.getClave()) :accion.equalsIgnoreCase("I")? pass(request.getClave()):request.getClave());
			} else {
				stmt.setNull(8, Types.VARCHAR);
			}
			if (request.getIdCiudad() != null) {
				stmt.setFloat(9, request.getIdCiudad());
			} else {
				stmt.setNull(9, Types.NUMERIC);
			}
			if (request.getAdmin() != null) {
				stmt.setFloat(10, request.getAdmin());
			} else {
				stmt.setNull(10, Types.NUMERIC);
			}
			if (request.getBarrio() != null) {
				stmt.setString(11, request.getBarrio());
			} else {
				stmt.setNull(11, Types.VARCHAR);
			}
			stmt.setString(12, usuarioEditor);
			stmt.setString(13, accion);
			stmt.registerOutParameter(14, Types.NUMERIC);
			stmt.registerOutParameter(15, Types.VARCHAR);
			stmt.registerOutParameter(16, OracleTypes.CURSOR);

		} catch (Exception e) {
			logger.error("Error en los datos", e);
		}
		return stmt;
	}

	public static ValidarUsuarioResponse validarUsuario(String cedula, String clave, ValidarUsuarioResponse response)
			throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_VALIDAR_USUARIO);
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;
		try {

			con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
					prop.getPropiedad(Constantes.TIPO_CON_SERCON));
			proc = con.prepareCall(sql);

			logger.info("Sentencia sql: " + sql);
			proc.setString(1, cedula);
			proc.setString(2, pass(clave));
			proc.registerOutParameter(3, Types.NUMERIC);
			proc.registerOutParameter(4, Types.VARCHAR);
			proc.registerOutParameter(5, OracleTypes.CURSOR);
			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));

			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			rs = ((OracleCallableStatement) proc).getCursor(5);
			while (rs != null && rs.next()) {
				UsuariosRequest usuario = new UsuariosRequest();
				usuario.setCedula(rs.getString(1));
				usuario.setNombres(rs.getString(2));
				usuario.setApellido(rs.getString(3));
				usuario.setCanal(rs.getString(4));
				usuario.setCelular(rs.getLong(5));
				usuario.setCorreo(rs.getString(6));
				usuario.setDireccion(rs.getString(7));
				usuario.setClave(rs.getString(8));
				usuario.setIdCiudad(rs.getInt(9));
				usuario.setAdmin(rs.getInt(10));
				usuario.setBarrio(rs.getString(11));

				response.setUsuario(usuario);
			}

			response.setCodigo(proc.getInt(3));
			response.setDescripcion(proc.getString(4));
			logger.info("RTA PROCEDIMIENTO>> codigo:" + proc.getInt(3) + " -descripcion:" + proc.getString(4));

		} finally {			
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (con != null)
					con.close();
			
		}
		return response;
	}

	public static LugarDaneResponse admLugares(LugarDaneRequest request, LugarDaneResponse response, String accion)
			throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_ADM_LUGARES);
		
		Connection con = null;
		ResultSet rs = null;
		CallableStatement proc=null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				proc = con.prepareCall(sql) ;

			logger.info("Sentencia sql: " + sql);
			mapearCamposLugar(request, proc, accion);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getInt(6));
			response.setDescripcion(proc.getString(7));

			rs = ((OracleCallableStatement) proc).getCursor(8);

			List<LugarDaneRequest> listaLugares = new ArrayList<LugarDaneRequest>();
			while (rs != null && rs.next()) {
				LugarDaneRequest lugar = new LugarDaneRequest();
				lugar.setCodigoDane(rs.getLong(1));
				lugar.setNombreLugar(rs.getString(2));
				lugar.setDepartamento(rs.getLong(3));
				lugar.setCentroDistribucion(rs.getString(4));
				listaLugares.add(lugar);
			}
			response.setLugares(listaLugares);
			logger.info("RTA PROCEDIMIENTO>> codigo:" + proc.getInt(6) + " -descripcion:" + proc.getString(7));
		}finally {
			
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (con != null)
					con.close();		
		}
		return response;

	
	}

	private static CallableStatement mapearCamposLugar(LugarDaneRequest request, CallableStatement stmt,
			String accion) {
		try {
			if (request.getCodigoDane() != null) {
				stmt.setLong(1, request.getCodigoDane());
			} else {
				stmt.setNull(1, Types.NUMERIC);
			}
			if (request.getNombreLugar() != null) {
				stmt.setString(2, request.getNombreLugar());
			} else {
				stmt.setNull(2, Types.VARCHAR);
			}
			if (request.getDepartamento() != null) {
				stmt.setLong(3, request.getDepartamento());
			} else {
				stmt.setNull(3, Types.NUMERIC);
			}
			if (request.getCentroDistribucion() != null) {
				stmt.setString(4, request.getCentroDistribucion());
			} else {
				stmt.setNull(4, Types.VARCHAR);
			}
			stmt.setString(5, accion);
			stmt.registerOutParameter(6, Types.NUMERIC);
			stmt.registerOutParameter(7, Types.VARCHAR);
			stmt.registerOutParameter(8, OracleTypes.CURSOR);

		} catch (SQLException e) {
			logger.error("Error en los datos", e);
		}
		return stmt;
	}

	public static HistoricoRedencionResponse obtenerHistoricoRedenciones(
			HistoricoRedencionRequest inHistoricoRedencionDto, Long numPagina, Long tamanoPagina) throws Exception {

		List<OutHistoricoRedencionDto> redenciones = new ArrayList<OutHistoricoRedencionDto>();
		HistoricoRedencionResponse historicoRedencionResponse = new HistoricoRedencionResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_HISTORICO_REDENCION));
		
		Connection con = null;
		ResultSet rs = null;
		CallableStatement proc=null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.SP_HISTORICO_REDENCION));
			String estadoIn = null;
			if (inHistoricoRedencionDto.getEstado() != null && inHistoricoRedencionDto.getEstado().trim().toUpperCase()
					.equals(prop.getPropiedad(Constantes.ESTADO_EFECTIVO).toUpperCase())) {
				estadoIn = prop.getPropiedad(Constantes.ESTADO_NUMERICO_EFECTIVO);
			} else if (inHistoricoRedencionDto.getEstado() != null && inHistoricoRedencionDto.getEstado().trim()
					.toUpperCase().equals(prop.getPropiedad(Constantes.ESTADO_RECHAZADO).trim().toUpperCase())) {
				estadoIn = prop.getPropiedad(Constantes.ESTADO_NUMERICO_RECHAZADO);

			} else if (inHistoricoRedencionDto.getEstado() != null && inHistoricoRedencionDto.getEstado().trim()
					.toUpperCase().equals(prop.getPropiedad(Constantes.ESTADO_PENDIENTE).trim().toUpperCase())) {
				estadoIn = prop.getPropiedad(Constantes.ESTADO_NUMERICO_PENDIENTE);

			}

			if (inHistoricoRedencionDto.getCedula() != null) {
				proc.setString(1, inHistoricoRedencionDto.getCedula());
			} else {
				proc.setNull(1, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getCelular() != null) {
				proc.setString(2, inHistoricoRedencionDto.getCelular());
			} else {
				proc.setNull(2, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getNombre() != null) {
				proc.setString(3, inHistoricoRedencionDto.getNombre());
			} else {
				proc.setNull(3, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getCorreo() != null) {
				proc.setString(4, inHistoricoRedencionDto.getCorreo());
			} else {
				proc.setNull(4, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getIdProducto() != null) {
				proc.setLong(5, inHistoricoRedencionDto.getIdProducto());
			} else {
				proc.setNull(5, Types.NUMERIC);
			}

			if (inHistoricoRedencionDto.getProducto() != null) {
				proc.setString(6, inHistoricoRedencionDto.getProducto());
			} else {
				proc.setNull(6, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getIdDepartamento() != null) {
				proc.setLong(7, inHistoricoRedencionDto.getIdDepartamento());
			} else {
				proc.setNull(7, Types.NUMERIC);
			}

			if (inHistoricoRedencionDto.getDepartamento() != null) {
				proc.setString(8, inHistoricoRedencionDto.getDepartamento());
			} else {
				proc.setNull(8, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getIdCiudad() != null) {
				proc.setLong(9, inHistoricoRedencionDto.getIdCiudad());
			} else {
				proc.setNull(9, Types.NUMERIC);
			}

			if (inHistoricoRedencionDto.getCiudad() != null) {
				proc.setString(10, inHistoricoRedencionDto.getCiudad());
			} else {
				proc.setNull(10, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getFechaPedido() != null) {
				proc.setDate(11, inHistoricoRedencionDto.getFechaPedido());
			} else {
				proc.setNull(11, Types.DATE);
			}

			if (estadoIn != null) {
				proc.setString(12, estadoIn);
			} else {
				proc.setNull(12, Types.VARCHAR);
			}

			if (inHistoricoRedencionDto.getNumeroPedido() != null) {
				proc.setLong(13, inHistoricoRedencionDto.getNumeroPedido());
			} else {
				proc.setNull(13, Types.NUMERIC);
			}

			if (numPagina != null) {
				proc.setLong(14, numPagina);
			} else {
				proc.setNull(14, Types.NUMERIC);
			}

			if (tamanoPagina != null) {
				proc.setLong(15, tamanoPagina);
			} else {
				proc.setNull(15, Types.NUMERIC);
			}
			proc.registerOutParameter(16, OracleTypes.NUMBER);
			proc.registerOutParameter(17, OracleTypes.NVARCHAR);
			proc.registerOutParameter(18, OracleTypes.NUMBER);
			proc.registerOutParameter(19, OracleTypes.CURSOR);
			proc.setQueryTimeout(5000);
			proc.executeQuery();

			historicoRedencionResponse.setCodigo(proc.getInt(16));
			historicoRedencionResponse.setResultado(proc.getString(17));
			historicoRedencionResponse.setTotalRegistros(proc.getInt(18));
			 rs = (ResultSet) proc.getObject(19);

			while (rs.next()) {
				Integer estado = null;
				OutHistoricoRedencionDto redencionDto = new OutHistoricoRedencionDto();
				redencionDto.setCedulaUsuario(rs.getString("CEDULA_USUARIO"));
				redencionDto.setCelular(rs.getLong("CELULAR"));
				redencionDto.setNombre(rs.getString("NOMBRES"));
				redencionDto.setApellido(rs.getString("APELLIDO"));
				redencionDto.setCorreo(rs.getString("CORREO"));
				redencionDto.setDireccion(rs.getString("DIRECCION"));
				redencionDto.setNumeroPedido(rs.getLong("NUMERO_PEDIDO"));
				redencionDto.setFechaPedido(rs.getDate("FECHA_PEDIDO"));
				redencionDto.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				redencionDto.setNombreCategoria(rs.getString("NOMBRE_CATEGORIA"));
				redencionDto.setNombreProducto(rs.getString("TITULO"));
				redencionDto.setCosto(rs.getLong("COSTO"));
				redencionDto.setCantidad(rs.getInt("CANTIDAD"));
				redencionDto.setPuntos(rs.getLong("PUNTOS"));
				estado = rs.getInt("ESTADO");
				if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_EFECTIVO))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_EFECTIVO));
				} else if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_RECHAZADO))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_RECHAZADO));
				} else if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_PENDIENTE))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_PENDIENTE));
				}
				redencionDto.setMotivoRechazo(rs.getString("MOTIVO_RECHAZO"));
				redencionDto.setIdDepartamento(rs.getLong("ID_DEPARTAMENTO"));
				redencionDto.setDepartamento(rs.getString("NOMBRE_DEPARTAMENTO"));
				redencionDto.setIdCiudad(rs.getLong("ID_CIUDAD"));
				redencionDto.setIdProducto(rs.getLong("ID_PRODUCTO"));
				redencionDto.setCiudad(rs.getString("NOMBRE_CIUDAD"));
				redencionDto.setDescripcionCortaProducto(rs.getString("DESCRIPCION_CORTA"));
				redencionDto.setInformacionDetalladaProducto(rs.getString("INFORMACION_DETALLADA"));
				redenciones.add(redencionDto);

			}
			historicoRedencionResponse.setOutHistoricoRedencionDto(redenciones);
			logger.info(
					"--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_HISTORICO_REDENCION));


		}finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();		
	}
		return historicoRedencionResponse;

	}

	public static PedidoResponse crearPedido(String UsuarioCrea, String cedula,
			List<TblAuxDetallePedidoDto> tblAuxDetallePedidoDto) throws Exception {

		PedidoResponse outCrearPedidoDto = new PedidoResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_CREAR_PEDIDO));
		Connection con = null;
		CallableStatement proc=null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.PRC_CREAR_PEDIDO)); 

			Array arr = ((OracleConnection) con).createOracleArray(prop.getPropiedad(Constantes.TBL_AUX_DETALLE_PED),
					getArrayCrearPedido(tblAuxDetallePedidoDto));

			logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + arr);
			proc.setString(1, (UsuarioCrea != null) ? UsuarioCrea : null);
			proc.setString(2, (cedula != null) ? cedula : null);
			proc.setArray(3, arr);

			proc.registerOutParameter(4, OracleTypes.NUMBER);
			proc.registerOutParameter(5, OracleTypes.NUMBER);
			proc.registerOutParameter(6, OracleTypes.NVARCHAR);
			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			proc.executeQuery();

			outCrearPedidoDto.setNumeroPedido(proc.getInt(4));
			outCrearPedidoDto.setCodigo(proc.getInt(5));
			outCrearPedidoDto.setDescripcion(proc.getString(6));

			logger.info(
					"--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_HISTORICO_REDENCION));


		}finally {
	
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();		
	}
		return outCrearPedidoDto;

	}

	public static PedidoResponse actualizarPedidos(List<TblAuxActDetallePedidoDto> tblAuxDetallePedidoDto,
			String usuarioCreador) throws Exception {

		List<OutActualizarPedidoDto> redenciones = new ArrayList<OutActualizarPedidoDto>();
		PedidoResponse outCrearPedidoDto = new PedidoResponse();
		logger.info(
				"--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_ACTUALIZAR_ESTADO_PEDIDO));
		Connection con = null;
		ResultSet rs = null;
		CallableStatement proc=null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.PRC_ACTUALIZAR_ESTADO_PEDIDO));

			Array arr = ((OracleConnection) con).createOracleArray(
					prop.getPropiedad(Constantes.TBL_AUX_ACT_DETALLE_PED),
					getArrayActualizarPedido(tblAuxDetallePedidoDto));

			proc.setString(1, usuarioCreador);
			proc.setArray(2, arr);

			proc.registerOutParameter(3, OracleTypes.NUMBER);
			proc.registerOutParameter(4, OracleTypes.NVARCHAR);
			proc.registerOutParameter(5, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			proc.executeQuery();

			outCrearPedidoDto.setNumeroPedido(proc.getInt(3));
			outCrearPedidoDto.setDescripcion(proc.getString(4));
			 rs = (ResultSet) proc.getObject(5);

			logger.info("--- FINALIZA CONSULTA PROCEDIMIENTO --- "
					+ prop.getPropiedad(Constantes.PRC_ACTUALIZAR_ESTADO_PEDIDO));

			while (rs != null && rs.next()) {
				Integer estado = null;
				OutActualizarPedidoDto redencionDto = new OutActualizarPedidoDto();
				redencionDto.setCedulaUsuario(rs.getString("CEDULA_USUARIO"));
				redencionDto.setCelular(rs.getLong("CELULAR"));
				redencionDto.setNombre(rs.getString("NOMBRES"));
				redencionDto.setApellido(rs.getString("APELLIDO"));
				redencionDto.setCorreo(rs.getString("CORREO"));
				redencionDto.setDireccion(rs.getString("DIRECCION"));
				redencionDto.setNumeroPedido(rs.getLong("NUMERO_PEDIDO"));
				redencionDto.setFechaPedido(rs.getDate("FECHA_PEDIDO"));
				redencionDto.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				redencionDto.setNombreCategoria(rs.getString("NOMBRE_CATEGORIA"));
				redencionDto.setNombreProducto(rs.getString("TITULO"));
				redencionDto.setCosto(rs.getLong("COSTO"));
				redencionDto.setCantidad(rs.getInt("CANTIDAD"));
				redencionDto.setPuntos(rs.getLong("PUNTOS"));
				estado = rs.getInt("ESTADO");
				if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_EFECTIVO))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_EFECTIVO));
				} else if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_RECHAZADO))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_RECHAZADO));
				} else if (estado.toString().equals(prop.getPropiedad(Constantes.ESTADO_NUMERICO_PENDIENTE))) {
					redencionDto.setEstado(prop.getPropiedad(Constantes.ESTADO_PENDIENTE));
				}
				redencionDto.setMotivoRechazo(rs.getString("MOTIVO_RECHAZO"));
				redencionDto.setIdDepartamento(rs.getLong("ID_DEPARTAMENTO"));
				redencionDto.setDepartamento(rs.getString("NOMBRE_DEPARTAMENTO"));
				redencionDto.setIdCiudad(rs.getLong("ID_CIUDAD"));
				redencionDto.setIdProducto(rs.getLong("ID_PRODUCTO"));
				redencionDto.setCiudad(rs.getString("NOMBRE_CIUDAD"));
				redencionDto.setDescripcionCortaProducto(rs.getString("DESCRIPCION_CORTA"));
				redencionDto.setInformacionDetalladaProducto(rs.getString("INFORMACION_DETALLADA"));
				redencionDto.setRespuestaActualizacion(rs.getString("RTA_ACTUALIZACION"));

				redenciones.add(redencionDto);

			}
			outCrearPedidoDto.setOutActualizarPedidoDto(redenciones);
			;


		}finally {
			
			if (proc != null)
				proc.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();		
	}
		return outCrearPedidoDto;

	}

	public static PuntosGanadosResponse optenerPuntosGanados(PuntosGanadosRequest InPuntosGanadosDto) throws Exception {

		List<OutPuntosGanadosDto> puntos = new ArrayList<OutPuntosGanadosDto>();
		PuntosGanadosResponse puntosGanadosResponse = new PuntosGanadosResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_PUNTOS_GANADOS));
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.SP_PUNTOS_GANADOS));

			proc.setDate(1,
					(InPuntosGanadosDto.getFechaActivacionIn() != null) ? InPuntosGanadosDto.getFechaActivacionIn()
							: null);
			proc.setDate(2,
					(InPuntosGanadosDto.getFechaActivacionFinal() != null)
							? InPuntosGanadosDto.getFechaActivacionFinal()
							: null);
			proc.setString(3,
					(InPuntosGanadosDto.getCedulaCliente() != null) ? InPuntosGanadosDto.getCedulaCliente() : null);
			proc.setString(4,
					(InPuntosGanadosDto.getCedulaUsuario() != null) ? InPuntosGanadosDto.getCedulaUsuario() : null);
			proc.setString(5, (InPuntosGanadosDto.getCuentaMin() != null) ? InPuntosGanadosDto.getCuentaMin() : null);
			proc.setString(6, (InPuntosGanadosDto.getProducto() != null) ? InPuntosGanadosDto.getProducto() : null);
			proc.setString(7, (InPuntosGanadosDto.getEstado() != null) ? InPuntosGanadosDto.getEstado() : null);

			proc.registerOutParameter(8, OracleTypes.CURSOR);
			proc.registerOutParameter(9, OracleTypes.NUMBER);
			proc.registerOutParameter(10, OracleTypes.NVARCHAR);

			proc.setQueryTimeout(5000);
			proc.executeQuery();

			 rs = (ResultSet) proc.getObject(8);
			puntosGanadosResponse.setCodigo(proc.getInt(9));
			puntosGanadosResponse.setResultado(proc.getString(10));

			while (rs.next()) {
				OutPuntosGanadosDto puntoGanados = new OutPuntosGanadosDto();
				puntoGanados.setCedulaUsuario(rs.getString("CEDULA_USUARIO"));
				puntoGanados.setFechaActivacion(rs.getDate("FECHA_ACTIVACION"));
				puntoGanados.setProducto(rs.getString("PRODUCTO"));
				puntoGanados.setCedulaCliente(rs.getString("CEDULA_CLIENTE"));
				puntoGanados.setCtaMin(rs.getString("CTA_MIN"));
				puntoGanados.setPuntos(rs.getLong("PUNTOS"));
				puntoGanados.setEstado(rs.getString("ESTADO"));
				puntoGanados.setDescripcion(rs.getString("DESCRIPCION"));

				puntos.add(puntoGanados);

			}
			puntosGanadosResponse.setOutPuntosGanadosDto(puntos);
			logger.info("--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_PUNTOS_GANADOS));

			}finally {
				
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (con != null)
					con.close();
			} 
		
		return puntosGanadosResponse;
	}

	/**
	 * Crea Objeto tipo Array
	 * 
	 * @param tblAuxDetallePedidoDto
	 * @return
	 * @throws Exception
	 */
	private static Object[] getArrayCrearPedido(List<TblAuxDetallePedidoDto> tblAuxDetallePedidoDto) throws Exception {

		Object[] bookStructArray = new Object[tblAuxDetallePedidoDto.size()];

		for (int i = 0; i < tblAuxDetallePedidoDto.size(); i++) {

			Object[] obj = new Object[] { tblAuxDetallePedidoDto.get(i).getIdProducto(),
					tblAuxDetallePedidoDto.get(i).getCantidad() };

			bookStructArray[i] = obj;
		}

		return bookStructArray;
	}

	/**
	 * Crea Objeto tipo Array
	 * 
	 * @param tblAuxDetallePedidoDto
	 * @return
	 * @throws Exception
	 */
	private static Object[] getArrayActualizarPedido(List<TblAuxActDetallePedidoDto> tblAuxDetallePedidoDto)
			throws Exception {

		Object[] bookStructArray = new Object[tblAuxDetallePedidoDto.size()];

		for (int i = 0; i < tblAuxDetallePedidoDto.size(); i++) {

			Object[] obj = new Object[] { tblAuxDetallePedidoDto.get(i).getIdProducto(),
					tblAuxDetallePedidoDto.get(i).getIdPedido(), tblAuxDetallePedidoDto.get(i).getEstado(),
					tblAuxDetallePedidoDto.get(i).getMotivoRechazo() };

			bookStructArray[i] = obj;
		}

		return bookStructArray;
	}

	public static CategoriasResponse admCategorias(CategoriasRequest request, CategoriasResponse response,
			String accion) throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_ADM_CATEGORIAS);
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				 proc = con.prepareCall(sql); 

			logger.info("Sentencia sql: " + sql);

			if (request.getIdCategoria() != null) {
				proc.setLong(1, request.getIdCategoria());
			} else {
				proc.setNull(1, Types.NUMERIC);
			}
			if (request.getNombreCategoria() != null) {
				proc.setString(2, request.getNombreCategoria());
			} else {
				proc.setNull(2, Types.VARCHAR);
			}
			if (request.getIsPendiente() != null) {
				proc.setLong(3, request.getIsPendiente());
			} else {
				proc.setNull(3, Types.NUMERIC);
			}
			proc.setString(4, accion);
			
			if (request.getOrden() != null) {
				proc.setLong(5, request.getOrden());
			} else {
				proc.setNull(5, Types.NUMERIC);
			}
			
			if (request.getEstado()!= null) {
				proc.setInt(6, request.getEstado());
			} else {
				proc.setNull(6, Types.VARCHAR);
			}
			proc.registerOutParameter(7, Types.NUMERIC);
			proc.registerOutParameter(8, Types.VARCHAR);
			proc.registerOutParameter(9, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getLong(7));
			response.setDescripcion(proc.getString(8));

			 rs = ((OracleCallableStatement) proc).getCursor(9);

			List<CategoriasRequest> listaCategorias = new ArrayList<CategoriasRequest>();
			while (rs != null && rs.next()) {
				CategoriasRequest categoria = new CategoriasRequest();
				categoria.setIdCategoria(rs.getLong(1));
				categoria.setNombreCategoria(rs.getString(2));
				categoria.setIsPendiente(rs.getLong(3));
				categoria.setEstado(rs.getInt(4));
				categoria.setOrden(rs.getInt(5));
				listaCategorias.add(categoria);
			}
			response.setListaCategorias(listaCategorias);
			logger.info("RTA PROCEDIMIENTO>> codigo:" + proc.getInt(7) + " -descripcion:" + proc.getString(8));
		}finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return response;

	}

	public static HistoricoUsuarioResponse historicoUsuario(HistoricoUsuarioRequest request,
			HistoricoUsuarioResponse response) throws SQLException, Exception {
		List<HistoricoUsuarioDTO> historico = new ArrayList<HistoricoUsuarioDTO>();
		String sql = prop.getPropiedad(Constantes.PRC_HISTORICO_USUARIO);
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				 proc = con.prepareCall(sql);

			logger.info("Sentencia sql: " + sql);
			if (request.getCedulaEditada() != null) {
				proc.setString(1, request.getCedulaEditada());
			} else {
				proc.setNull(1, Types.VARCHAR);
			}
			if (request.getUsuarioEditor() != null) {
				proc.setString(2, request.getUsuarioEditor());
			} else {
				proc.setNull(2, Types.VARCHAR);
			}
			if (request.getFechaInEdicion() != null) {
				proc.setDate(3, new java.sql.Date(request.getFechaInEdicion().getTime()));
			} else {
				proc.setNull(3, Types.DATE);
			}
			if (request.getFechaFinEdicion() != null) {
				proc.setDate(4, new java.sql.Date(request.getFechaFinEdicion().getTime()));
			} else {
				proc.setNull(4, Types.DATE);
			}
			if (request.getEfectividadCambio() != null) {
				proc.setString(5, request.getEfectividadCambio());
			} else {
				proc.setNull(5, Types.VARCHAR);
			}

			proc.registerOutParameter(6, Types.NUMERIC);
			proc.registerOutParameter(7, Types.VARCHAR);
			proc.registerOutParameter(8, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));

			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getInt(6));
			response.setDescripcion(proc.getString(7));
			logger.info("RTA PROCEDIMIENTO>> codigo:" + proc.getInt(6) + " -descripcion:" + proc.getString(7));

			 rs = ((OracleCallableStatement) proc).getCursor(8);

			while (rs != null && rs.next()) {
				HistoricoUsuarioDTO registroHistorial = deResulsetAHistoricoUsuarioDTO(rs);
				historico.add(registroHistorial);
			}
			response.setHistorial(historico);
		} finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 

		return response;
	}

	public static HistoricoUsuarioDTO deResulsetAHistoricoUsuarioDTO(ResultSet rs) throws SQLException, Exception {
		HistoricoUsuarioDTO usuarioAUD = new HistoricoUsuarioDTO();

		usuarioAUD.setCedula(rs.getString(1));
		usuarioAUD.setNombre(rs.getString(2));
		usuarioAUD.setApellido(rs.getString(3));
		usuarioAUD.setCorreo(rs.getString(4));
		usuarioAUD.setCelular(rs.getString(5));
		usuarioAUD.setBarrio(rs.getString(6));
		usuarioAUD.setCanal(rs.getString(7));
		usuarioAUD.setDireccion(rs.getString(8));
		usuarioAUD.setFechaRegistro(rs.getDate(9));
		usuarioAUD.setUsuarioCambio(rs.getString(10));
		usuarioAUD.setIdCiudad(rs.getString(11));
		usuarioAUD.setNombreCiudad(rs.getString(12));
		usuarioAUD.setAccion(rs.getString(13));
		if (usuarioAUD.getAccion() != null
				&& usuarioAUD.getAccion().trim().equalsIgnoreCase(prop.getPropiedad(Constantes.ACCION_UPDATE_CORTA))) {
			usuarioAUD.setAccion(prop.getPropiedad(Constantes.ACCION_UPDATE_LARGA));
		} else if (usuarioAUD.getAccion() != null
				&& usuarioAUD.getAccion().trim().equalsIgnoreCase(prop.getPropiedad(Constantes.ACCION_INSERT_CORTA))) {
			usuarioAUD.setAccion(prop.getPropiedad(Constantes.ACCION_INSERT_LARGA));
		} else if (usuarioAUD.getAccion() != null
				&& usuarioAUD.getAccion().trim().equalsIgnoreCase(prop.getPropiedad(Constantes.ACCION_DELETE_CORTA))) {
			usuarioAUD.setAccion(prop.getPropiedad(Constantes.ACCION_DELETE_LARGA));
		} else if (usuarioAUD.getAccion() != null && usuarioAUD.getAccion().trim()
				.equalsIgnoreCase(prop.getPropiedad(Constantes.ACCION_CONSULTA_CORTA))) {
			usuarioAUD.setAccion(prop.getPropiedad(Constantes.ACCION_CONSULTA_LARGA));
		}
		usuarioAUD.setCodRespuesta(rs.getLong(14));
		usuarioAUD.setDescRespuesta(rs.getString(15));
		usuarioAUD.setIdDepartamento(rs.getString(16));
		usuarioAUD.setNombreDepartamento(rs.getString(17));

		return usuarioAUD;
	}

	public static CrudProductosResponse crudProductos(CrudProductosRequest crudProductosRequest, String accion,
			Long numPag, Long tamanoPag) throws Exception {

		ArrayList<CrudProductosDto> crudProductos = new ArrayList<CrudProductosDto>();
		CrudProductosResponse crudProductosResponse = new CrudProductosResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_ADM_PRODUCTOS));
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.PRC_ADM_PRODUCTOS)); 

			if (crudProductosRequest.getIdProducto() != null) {
				proc.setLong(1, crudProductosRequest.getIdProducto());
			} else {
				proc.setNull(1, OracleTypes.NUMBER);
			}
			if (crudProductosRequest.getIdCategoria() != null) {
				proc.setLong(2, crudProductosRequest.getIdCategoria());
			} else {
				proc.setNull(2, OracleTypes.NUMBER);
			}
			if (crudProductosRequest.getTitulo() != null) {
				proc.setString(3, crudProductosRequest.getTitulo());
			} else {
				proc.setNull(3, OracleTypes.NVARCHAR);
			}
			if (crudProductosRequest.getDescripcionCorta() != null) {
				proc.setString(4, crudProductosRequest.getDescripcionCorta());
			} else {
				proc.setNull(4, OracleTypes.NVARCHAR);
			}
			if (crudProductosRequest.getImagen() != null) {
				Blob myClob = con.createBlob();
				myClob.setBytes(1, crudProductosRequest.getImagen().getBytes());
				proc.setBlob(5, myClob);
			} else {
				proc.setNull(5, OracleTypes.BLOB);
			}

			if (crudProductosRequest.getEstado() != null) {
				proc.setLong(6, crudProductosRequest.getEstado());
			} else {
				proc.setNull(6, OracleTypes.NUMBER);
			}

			if (crudProductosRequest.getDestacado() != null) {
				proc.setLong(7, crudProductosRequest.getDestacado());
			} else {
				proc.setNull(7, OracleTypes.NUMBER);
			}

			if (crudProductosRequest.getMasRedimido() != null) {
				proc.setLong(8, crudProductosRequest.getMasRedimido());
			} else {
				proc.setNull(8, OracleTypes.NUMBER);
			}
			if (crudProductosRequest.getUnidadesDisponibles() != null) {
				proc.setInt(9, crudProductosRequest.getUnidadesDisponibles());
			} else {
				proc.setNull(9, OracleTypes.NUMBER);
			}

			if (crudProductosRequest.getCostos() != null) {
				proc.setLong(10, crudProductosRequest.getCostos());
			} else {
				proc.setNull(10, OracleTypes.NUMBER);
			}

			if (crudProductosRequest.getInformacionDetallada() != null) {
				proc.setString(11, crudProductosRequest.getInformacionDetallada());
			} else {
				proc.setNull(11, OracleTypes.NVARCHAR);
			}

			if (crudProductosRequest.getPuntos() != null) {
				proc.setLong(12, crudProductosRequest.getPuntos());
			} else {
				proc.setNull(12, OracleTypes.NUMBER);
			}

			proc.setString(13, accion);

			proc.registerOutParameter(14, OracleTypes.NUMBER);
			proc.registerOutParameter(15, OracleTypes.NVARCHAR);
			proc.registerOutParameter(16, OracleTypes.CURSOR);
			proc.registerOutParameter(17, OracleTypes.NUMBER);
			if (numPag != null) {
				proc.setLong(18, numPag);
			} else {
				proc.setNull(18, OracleTypes.NUMBER);
			}

			if (tamanoPag != null) {
				proc.setLong(19, tamanoPag);
			} else {
				proc.setNull(19, OracleTypes.NUMBER);
			}
			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			proc.executeQuery();
			crudProductosResponse.setCodigo(proc.getInt(14));
			crudProductosResponse.setResultado(proc.getString(15));
			 rs = (ResultSet) proc.getObject(16);
			crudProductosResponse.setTotalRegistros(proc.getInt(17));

			while (rs != null && rs.next()) {
				CrudProductosDto CrudProductosDto = new CrudProductosDto();
				CrudProductosDto.setDescripcionCorta(rs.getString("descripcion_corta"));
				CrudProductosDto.setDestacado(rs.getLong("destacado"));
				CrudProductosDto.setEstado(rs.getLong("estado"));
				CrudProductosDto.setIdProducto(rs.getLong("id_producto"));
				CrudProductosDto.setIdCategoria(rs.getLong("id_categoria"));
				String imagen = "";
				Blob blob = rs.getBlob("imagen");
				if (blob != null) {
					byte[] bdata = blob.getBytes(1, (int) blob.length());
					imagen = new String(bdata);
				}
				CrudProductosDto.setImagen(imagen);
				CrudProductosDto.setMasRedimido(rs.getLong("mas_redimido"));
				CrudProductosDto.setNombreCategoria(rs.getString("nombre_categoria"));
				CrudProductosDto.setTitulo(rs.getString("titulo"));
				CrudProductosDto.setUnidadesDisponibles(rs.getLong("unidades_disponibles"));
				CrudProductosDto.setCosto(rs.getLong("COSTO"));
				CrudProductosDto.setPuntos(rs.getLong("puntos"));
				CrudProductosDto.setInformacionDetallada(rs.getString("informacion_detallada"));

				crudProductos.add(CrudProductosDto);

			}
			crudProductosResponse.setCrudProductosDto(crudProductos);
			logger.info("--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_ADM_PRODUCTOS));


		} finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return crudProductosResponse;

	}

	public static ResumenPuntosResponse optenerResumenPuntos(String cedula, Integer numPag, Integer tamanoPag)
			throws Exception {

		ArrayList<ResumenPuntoDto> ResumenPuntos = new ArrayList<ResumenPuntoDto>();
		ResumenPuntosResponse resumenPuntosResponse = new ResumenPuntosResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_RESUMEN_PUNTOS));
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.PRC_RESUMEN_PUNTOS));

			proc.setString(1, cedula);

			proc.registerOutParameter(2, OracleTypes.CURSOR);
			proc.registerOutParameter(3, OracleTypes.NUMBER);
			proc.registerOutParameter(4, OracleTypes.NVARCHAR);
			proc.registerOutParameter(5, OracleTypes.NUMBER);
			if (numPag != null) {
				proc.setInt(6, numPag);
			} else {
				proc.setNull(6, OracleTypes.NUMBER);
			}
			if (tamanoPag != null) {
				proc.setInt(7, tamanoPag);
			} else {
				proc.setNull(7, OracleTypes.NUMBER);
			}

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			proc.executeQuery();

			rs = (ResultSet) proc.getObject(2);
			resumenPuntosResponse.setCodigo(proc.getInt(3));
			resumenPuntosResponse.setResultado(proc.getString(4));
			resumenPuntosResponse.setTotalRegistros(proc.getInt(5));

			while (rs.next()) {
				ResumenPuntoDto resumenPuntoDto = new ResumenPuntoDto();
				resumenPuntoDto.setCedula(rs.getString("CEDULA_USUARIO"));
				resumenPuntoDto.setPuntosCanjeados(rs.getLong("PUNTOS_CANJEADOS"));
				resumenPuntoDto.setPuntosGanados(rs.getLong("PUNTOS_GANADOS"));
				resumenPuntoDto.setPuntosRestantes(rs.getLong("PUNTOS_RESTANTES"));

				ResumenPuntos.add(resumenPuntoDto);

			}
			resumenPuntosResponse.setResumenPuntoDto(ResumenPuntos);
			logger.info("--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.PRC_RESUMEN_PUNTOS));

			

		}  finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return resumenPuntosResponse;
	}
	
	public static ReferidosResponse admReferidos(ReferidosRequest request, ReferidosResponse response,
			String accion) throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_ADM_REFERIDO);
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				 proc = con.prepareCall(sql); 

			logger.info("Sentencia sql: " + sql);

			if (request.getIdReferido()!= null) {
				proc.setLong(1, request.getIdReferido());
			} else {
				proc.setNull(1, Types.NUMERIC);
			}
			if (request.getCedula() != null) {
				proc.setString(2, request.getCedula());
			} else {
				proc.setNull(2, Types.VARCHAR);
			}
			if (request.getNombre() != null) {
				proc.setString(3, request.getNombre());
			} else {
				proc.setNull(3, Types.VARCHAR);
			}
			
			if (request.getTelefonoCelular() != null) {
				proc.setLong(4, request.getTelefonoCelular());
			} else {
				proc.setNull(4, Types.NUMERIC);
			}
			
			if (request.getTelefono2() != null) {
				proc.setLong(5, request.getTelefono2());
			} else {
				proc.setNull(5, Types.NUMERIC);
			}
			
			if (request.getIdDepartamento() != null) {
				proc.setInt(6, request.getIdDepartamento());
			} else {
				proc.setNull(6, Types.NUMERIC);
			}
			
			if (request.getIdCiudad() != null) {
				proc.setInt(7, request.getIdCiudad());
			} else {
				proc.setNull(7, Types.NUMERIC);
			}
			
			if (request.getDirreccion() != null) {
				proc.setString(8, request.getDirreccion());
			} else {
				proc.setNull(8, Types.VARCHAR);
			}
			if (request.getSegmento() != null) {
				proc.setString(9, request.getSegmento());
			} else {
				proc.setNull(9, Types.VARCHAR);
			}
			if (request.getProducto() != null) {
				proc.setString(10, request.getProducto());
			} else {
				proc.setNull(10, Types.VARCHAR);
			}
			if (request.getCedulaQuienRefiere() != null) {
				proc.setString(11, request.getCedulaQuienRefiere());
			} else {
				proc.setNull(11, Types.VARCHAR);
			}
			if (request.getNombreQuienRefiere() != null) {
				proc.setString(12, request.getNombreQuienRefiere());
			} else {
				proc.setNull(12, Types.VARCHAR);
			}
			proc.setString(13, accion);
			proc.registerOutParameter(14, Types.NUMERIC);
			proc.registerOutParameter(15, Types.VARCHAR);
			proc.registerOutParameter(16, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getLong(14));
			response.setDescripcion(proc.getString(15));

			 rs = ((OracleCallableStatement) proc).getCursor(16);

			List<ReferidosRequest> listaReferidos = new ArrayList<ReferidosRequest>();
			while (rs != null && rs.next()) {
				ReferidosRequest referido = new ReferidosRequest();
				referido.setIdReferido(rs.getLong("ID_REFERIDO"));
				referido.setCedula(rs.getString("CEDULA"));
				referido.setNombre(rs.getString("NOMBRE"));
				referido.setTelefonoCelular(rs.getLong("TELEFONO_CELULAR"));
				referido.setTelefono2(rs.getLong("TELEFONO2"));
				referido.setNombreDepartamento(rs.getString("NOMBRE_DEPARTAMENTO"));
				referido.setNombreCiudad(rs.getString("NOMBRE_CIUDAD"));
				referido.setIdCiudad(rs.getInt("CIUDAD"));
				referido.setIdDepartamento(rs.getInt("DEPARTAMENTO"));
				referido.setDirreccion(rs.getString("DIRECCION"));
				referido.setSegmento(rs.getString("SEGMENTO"));
				referido.setProducto(rs.getString("PRODUCTO"));
				referido.setCedulaQuienRefiere(rs.getString("CEDULA_QUIEN_REFIERE"));
				referido.setNombreQuienRefiere(rs.getString("NOMBRE_QUIEN_REFIERE"));
				referido.setFechaCreacion(rs.getDate("FECHA_CREACION"));

				listaReferidos.add(referido);
			}
			response.setListaReferidos(listaReferidos);
		}finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return response;

	}
	
	public static PerfilResponse admPerfiles(PerfilRequest request, PerfilResponse response,
			String accion) throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_ADM_PERFILES);
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				 proc = con.prepareCall(sql); 

			logger.info("Sentencia sql: " + sql);

			if (request.getIdPerfil()!= null) {
				proc.setLong(1, request.getIdPerfil());
			} else {
				proc.setNull(1, Types.NUMERIC);
			}
			if (request.getNombrePerfil() != null) {
				proc.setString(2, request.getNombrePerfil());
			} else {
				proc.setNull(2, Types.VARCHAR);
			}
			if (request.getIsReferido() != null) {
				proc.setString(3, request.getIsReferido());
			} else {
				proc.setNull(3, Types.VARCHAR);
			}
			
			
			proc.setString(4, accion);
			proc.registerOutParameter(5, Types.NUMERIC);
			proc.registerOutParameter(6, Types.VARCHAR);
			proc.registerOutParameter(7, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getLong(5));
			response.setDescripcion(proc.getString(6));

			 rs = ((OracleCallableStatement) proc).getCursor(7);

			List<PerfilRequest> listaPerfiles = new ArrayList<PerfilRequest>();
			while (rs != null && rs.next()) {
				PerfilRequest perfil = new PerfilRequest();
				perfil.setIdPerfil(rs.getLong("ID_PERFIL"));
				perfil.setNombrePerfil(rs.getString("NOMBRE_PERFIL"));
				perfil.setIsReferido(rs.getString("IS_REFERIDO"));

				listaPerfiles.add(perfil);
			}
			response.setListaPerfiles(listaPerfiles);
		}finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return response;

	}
	
	public static PerfilXCategoriaResponse admPerfilXCategoria(PerfilXCategoriarequest request, PerfilXCategoriaResponse response,
			String accion) throws SQLException, Exception {
		String sql = prop.getPropiedad(Constantes.PRC_ADM_PERFIL_X_CATEGORIA);
		
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;

		try { con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
				prop.getPropiedad(Constantes.TIPO_CON_SERCON));

				 proc = con.prepareCall(sql); 

			logger.info("Sentencia sql: " + sql);

			if (request.getIdPerfil()!= null) {
				proc.setLong(1, request.getIdPerfil());
			} else {
				proc.setNull(1, Types.NUMERIC);
			}
			
			if (request.getIdCategoria()!= null) {
				proc.setLong(2, request.getIdCategoria());
			} else {
				proc.setNull(2, Types.NUMERIC);
			}


			
			
			proc.setString(3, accion);
			proc.registerOutParameter(4, Types.NUMERIC);
			proc.registerOutParameter(5, Types.VARCHAR);
			proc.registerOutParameter(6, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			long timeIni = System.currentTimeMillis();
			proc.executeQuery();
			long timeFin = System.currentTimeMillis() - timeIni;

			response.setCodigo(proc.getLong(4));
			response.setDescripcion(proc.getString(5));

			 rs = ((OracleCallableStatement) proc).getCursor(6);

			List<PerfilXCategoriarequest> listaPerfilesXCategoria = new ArrayList<PerfilXCategoriarequest>();
			while (rs != null && rs.next()) {
				PerfilXCategoriarequest perfilXCategoriarequest = new PerfilXCategoriarequest();
				perfilXCategoriarequest.setIdPerfil(rs.getLong("ID_PERFIL"));
				perfilXCategoriarequest.setIdCategoria(rs.getLong("ID_CATEGORIA"));
				
				listaPerfilesXCategoria.add(perfilXCategoriarequest);
			}
			response.setListaPerfilesXCategoria(listaPerfilesXCategoria);
		}finally {
			
			if (rs != null)
				rs.close();
			if (proc != null)
				proc.close();
			if (con != null)
				con.close();
		} 
		return response;

	}
	
	public static UsuariosSistemaDtosResponse optenerUsuariosSistema() throws Exception {

		List<UsuariosSistemaDto> usuarios = new ArrayList<UsuariosSistemaDto>();
		UsuariosSistemaDtosResponse usuariosSistemaDtosResponse = new UsuariosSistemaDtosResponse();
		logger.info("--- INICIA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_ADM_USUARIOS_DEL_SISTEMA));
		ResultSet rs = null;
		Connection con = null;
		CallableStatement proc = null;
		try {

				 con = Conexion.getInstance().getConnection(prop.getPropiedad(Constantes.SERCON),
						(prop.getPropiedad(Constantes.TIPO_CON_SERCON)));
				 proc = con.prepareCall(prop.getPropiedad(Constantes.SP_ADM_USUARIOS_DEL_SISTEMA));

		
			proc.registerOutParameter(1, OracleTypes.NUMBER);
			proc.registerOutParameter(2, OracleTypes.NVARCHAR);
			proc.registerOutParameter(3, OracleTypes.CURSOR);

			proc.setQueryTimeout(prop.getIntPropiedad(Constantes.TIME_OUT));
			proc.executeQuery();

			 rs = (ResultSet) proc.getObject(3);
			 usuariosSistemaDtosResponse.setCodigo(proc.getInt(1));
			 usuariosSistemaDtosResponse.setResultado(proc.getString(2));

			while (rs.next()) {
				UsuariosSistemaDto usuariosSistemaDto = new UsuariosSistemaDto();
				usuariosSistemaDto.setCedulaUsuario(rs.getString("CEDULA_USUARIO"));
				usuariosSistemaDto.setNombres(rs.getNString("NOMBRES"));
				usuariosSistemaDto.setApellido(rs.getString("APELLIDO"));
				usuariosSistemaDto.setDireccion(rs.getString("DIRECCION"));
				usuariosSistemaDto.setCelular(rs.getLong("CELULAR"));
				usuariosSistemaDto.setCorreo(rs.getString("CORREO"));
				usuariosSistemaDto.setAceptaTerminos(rs.getString("ACEPTA_TERMINOS"));
				usuariosSistemaDto.setDepartamento(rs.getString("DEPARTAMENTO"));
				usuariosSistemaDto.setCiudad(rs.getString("CIUDAD"));
				usuariosSistemaDto.setPuntosRestantes(rs.getInt("PUNTOS_RESTANTES"));
				usuarios.add(usuariosSistemaDto);

			}
			usuariosSistemaDtosResponse.setUsuariosSistemaDtos(usuarios);
			logger.info("--- FINALIZA CONSULTA PROCEDIMIENTO --- " + prop.getPropiedad(Constantes.SP_ADM_USUARIOS_DEL_SISTEMA));

			}finally {
				
				if (rs != null)
					rs.close();
				if (proc != null)
					proc.close();
				if (con != null)
					con.close();
			} 
		
		return usuariosSistemaDtosResponse;
	}
}
