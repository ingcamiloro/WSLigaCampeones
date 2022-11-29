package com.claro.WSLigaCampeones.dto;

public class ResumenPuntoDto {
	private String Cedula;
	private Long puntosGanados;
	private Long puntosCanjeados;
	private Long puntosRestantes;
	public String getCedula() {
		return Cedula;
	}
	public void setCedula(String cedula) {
		Cedula = cedula;
	}
	public Long getPuntosGanados() {
		return puntosGanados;
	}
	public void setPuntosGanados(Long puntosGanados) {
		this.puntosGanados = puntosGanados;
	}

	public Long getPuntosCanjeados() {
		return puntosCanjeados;
	}
	public void setPuntosCanjeados(Long puntosCanjeados) {
		this.puntosCanjeados = puntosCanjeados;
	}
	public Long getPuntosRestantes() {
		return puntosRestantes;
	}
	public void setPuntosRestantes(Long puntosRestantes) {
		this.puntosRestantes = puntosRestantes;
	}
	@Override
	public String toString() {
		return "ResumenPuntoDto [Cedula=" + Cedula + ", puntosGanados=" + puntosGanados + ", puntosCanjeados="
				+ puntosCanjeados + ", puntosRestantes=" + puntosRestantes + "]";
	}
	
	
	
}
