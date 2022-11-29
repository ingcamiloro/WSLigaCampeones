package com.claro.WSLigaCampeones.dto;

import java.util.ArrayList;
import java.util.List;

public class LugarDaneResponse {
	
	private int codigo;
	private String descripcion;
	private List<LugarDaneRequest> lugares;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public List<LugarDaneRequest> getLugares() {
		return lugares;
	}
	public void setLugares(List<LugarDaneRequest> lugares) {
		this.lugares = lugares;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "LugarDaneResponse [codigo=" + codigo + ", descripcion=" + descripcion + ", lugares=" + lugares.toString() + "]";
	}
	
	

}
