package com.claro.WSLigaCampeones.dto;

import java.util.List;

public class PuntosGanadosResponse {
	
	private String resultado;
	private Integer codigo;	
	private List<OutPuntosGanadosDto> outPuntosGanadosDto;
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public List<OutPuntosGanadosDto> getOutPuntosGanadosDto() {
		return outPuntosGanadosDto;
	}
	public void setOutPuntosGanadosDto(List<OutPuntosGanadosDto> outPuntosGanadosDto) {
		this.outPuntosGanadosDto = outPuntosGanadosDto;
	}
	@Override
	public String toString() {
		return "PuntosGanadosResponse [resultado=" + resultado + ", codigo=" + codigo + ", outPuntosGanadosDto="
				+ outPuntosGanadosDto + "]";
	}
	
	

}
