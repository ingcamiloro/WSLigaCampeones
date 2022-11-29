package com.claro.WSLigaCampeones.dto;

import java.util.List;

public class CategoriasResponse {
	
	private Long codigo;
	private String descripcion;
	private List<CategoriasRequest> listaCategorias;
	
	public Long getCodigo() {
		return codigo;
	}
	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<CategoriasRequest> getListaCategorias() {
		return listaCategorias;
	}
	public void setListaCategorias(List<CategoriasRequest> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}
	@Override
	public String toString() {
		return "CategoriasResponse [codigo=" + codigo + ", descripcion=" + descripcion + ", listaCategorias="
				+ listaCategorias.toString() + "]";
	}
	
	

}
