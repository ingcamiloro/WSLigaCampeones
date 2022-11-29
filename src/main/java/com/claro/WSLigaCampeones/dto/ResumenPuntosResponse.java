package com.claro.WSLigaCampeones.dto;

import java.util.ArrayList;

public class ResumenPuntosResponse {
	
	private Integer codigo;
	private String resultado;
	private Integer totalRegistros;

	private ArrayList<ResumenPuntoDto> resumenPuntoDto;

	public Integer getCodigo() {
		return codigo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public ArrayList<ResumenPuntoDto> getResumenPuntoDto() {
		return resumenPuntoDto;
	}
	public void setResumenPuntoDto(ArrayList<ResumenPuntoDto> resumenPuntoDto) {
		this.resumenPuntoDto = resumenPuntoDto;
	}
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}

	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	@Override
	public String toString() {
		return "ResumenPuntosResponse [codigo=" + codigo + ", resultado=" + resultado + ", totalRegistros="
				+ totalRegistros + ", resumenPuntoDto=" + resumenPuntoDto + "]";
	}
	
	
	

}
