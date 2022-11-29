package com.claro.WSLigaCampeones.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ReferidosRequest {
	
	private Long idReferido;
	private String cedula;
	private String nombre;
	private Long telefonoCelular;
	private Long telefono2;
	private Integer idDepartamento;
	private Integer idCiudad;
	private String nombreDepartamento;
	private String nombreCiudad;
	private String dirreccion;
	private String segmento;
	private String producto;
	private String cedulaQuienRefiere;
	private String nombreQuienRefiere;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaCreacion;
	
}
