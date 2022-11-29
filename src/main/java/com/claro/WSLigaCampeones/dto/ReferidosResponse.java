package com.claro.WSLigaCampeones.dto;

import java.util.List;

import lombok.Data;
@Data
public class ReferidosResponse {
	private Long codigo;
	private String descripcion;
	private List<ReferidosRequest> listaReferidos;

}
