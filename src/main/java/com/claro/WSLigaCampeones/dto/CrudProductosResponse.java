package com.claro.WSLigaCampeones.dto;

import java.util.ArrayList;

public class CrudProductosResponse {
	private int codigo;
	private String resultado;
	private int totalRegistros;
	private ArrayList<CrudProductosDto> crudProductosDto;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public ArrayList<CrudProductosDto> getCrudProductosDto() {
		return crudProductosDto;
	}
	public void setCrudProductosDto(ArrayList<CrudProductosDto> crudProductosDto) {
		this.crudProductosDto = crudProductosDto;
	}
	public int getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(int totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	@Override
	public String toString() {
		return "CrudProductosResponse [codigo=" + codigo + ", resultado=" + resultado + ", totalRegistros="
				+ totalRegistros + ", crudProductosDto=" + crudProductosDto + "]";
	}
	
	

}

