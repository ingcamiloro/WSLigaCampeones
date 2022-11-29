package com.claro.WSLigaCampeones.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;
@Data
@ToString
public class UsuariosSistemaDtosResponse {
	
	private String resultado;
	private Integer codigo;	
	private List<UsuariosSistemaDto> usuariosSistemaDtos;

}
