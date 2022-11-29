package com.claro.WSLigaCampeones.dto;

public class LugarDaneRequest {
	
	private Long codigoDane;
	private String nombreLugar;
	private Long departamento;
	private String centroDistribucion;
	
	public String getNombreLugar() {
		return nombreLugar;
	}
	public void setNombreLugar(String nombreLugar) {
		this.nombreLugar = nombreLugar;
	}
	public Long getDepartamento() {
		return departamento;
	}
	public void setDepartamento(Long departamento) {
		this.departamento = departamento;
	}
	public String getCentroDistribucion() {
		return centroDistribucion;
	}
	public void setCentroDistribucion(String centroDistribucion) {
		this.centroDistribucion = centroDistribucion;
	}
	public Long getCodigoDane() {
		return codigoDane;
	}
	public void setCodigoDane(Long codigoDane) {
		this.codigoDane = codigoDane;
	}
	@Override
	public String toString() {
		return "LugarDaneRequest [codigoDane=" + codigoDane + ", nombreLugar=" + nombreLugar + ", departamento="
				+ departamento + ", centroDistribucion=" + centroDistribucion + "]";
	}
	
	
	

}
