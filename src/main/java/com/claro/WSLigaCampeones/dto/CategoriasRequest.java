package com.claro.WSLigaCampeones.dto;

import lombok.Data;

@Data
public class CategoriasRequest {
	
	private Long idCategoria;
	private String nombreCategoria;
	private Long isPendiente;
	private Integer estado;
	private Integer orden;
	
	}
