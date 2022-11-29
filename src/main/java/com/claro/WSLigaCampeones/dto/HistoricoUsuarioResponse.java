package com.claro.WSLigaCampeones.dto;

import java.util.List;

public class HistoricoUsuarioResponse {
	
	private int codigo;
	private String descripcion;
	private List<HistoricoUsuarioDTO> historial;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<HistoricoUsuarioDTO> getHistorial() {
		return historial;
	}
	public void setHistorial(List<HistoricoUsuarioDTO> historial) {
		this.historial = historial;
	}
	@Override
	public String toString() {
		return "HistoricoUsuarioResponse [codigo=" + codigo + ", descripcion=" + descripcion + ", historial="
				+ historial.toString() + "]";
	}
	
	

}
