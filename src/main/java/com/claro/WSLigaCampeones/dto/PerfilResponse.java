package com.claro.WSLigaCampeones.dto;

import java.util.List;

import lombok.Data;
@Data
public class PerfilResponse {
	private Long codigo;
	private String descripcion;
	private List<PerfilRequest> listaPerfiles;

}
