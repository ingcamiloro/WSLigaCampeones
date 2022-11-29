package com.claro.WSLigaCampeones.dto;

import lombok.Data;
@Data
public class UsuariosRequest {
	private String cedula;
	private String nombres;
	private String apellido;
	private String canal;
	private Long celular;
	private String correo;
	private String direccion;
	private String clave;
	private Integer idCiudad;
	private Integer admin;
	private String barrio;
	private Boolean cambioClave;

	
	}
