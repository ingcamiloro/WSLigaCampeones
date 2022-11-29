package com.claro.WSLigaCampeones.dto;

import java.util.List;


public class HistoricoRedencionResponse {	
	
	private String resultado;
	private Integer codigo;	
	private Integer totalRegistros;
	private List<OutHistoricoRedencionDto> outHistoricoRedencionDto;
	
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
	public List<OutHistoricoRedencionDto> getOutHistoricoRedencionDto() {
		return outHistoricoRedencionDto;
	}
	public void setOutHistoricoRedencionDto(List<OutHistoricoRedencionDto> outHistoricoRedencionDto) {
		this.outHistoricoRedencionDto = outHistoricoRedencionDto;
	}
	public Integer getTotalRegistros() {
		return totalRegistros;
	}
	public void setTotalRegistros(Integer totalRegistros) {
		this.totalRegistros = totalRegistros;
	}
	@Override
	public String toString() {
		return "HistoricoRedencionResponse [resultado=" + resultado + ", codigo=" + codigo + ", totalRegistros="
				+ totalRegistros + ", outHistoricoRedencionDto=" + outHistoricoRedencionDto + "]";
	}
	
	
	

}
