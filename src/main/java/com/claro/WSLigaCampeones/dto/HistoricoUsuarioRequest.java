package com.claro.WSLigaCampeones.dto;

import java.util.Date;

public class HistoricoUsuarioRequest {
	
	private String cedulaEditada;
	private String usuarioEditor;
	private Date FechaInEdicion;
	private Date FechaFinEdicion;
	private String efectividadCambio;
	
	public String getCedulaEditada() {
		return cedulaEditada;
	}
	public void setCedulaEditada(String cedulaEditada) {
		this.cedulaEditada = cedulaEditada;
	}
	public String getUsuarioEditor() {
		return usuarioEditor;
	}
	public void setUsuarioEditor(String usuarioEditor) {
		this.usuarioEditor = usuarioEditor;
	}
	public Date getFechaInEdicion() {
		return FechaInEdicion;
	}
	public void setFechaInEdicion(Date fechaInEdicion) {
		FechaInEdicion = fechaInEdicion;
	}
	public Date getFechaFinEdicion() {
		return FechaFinEdicion;
	}
	public void setFechaFinEdicion(Date fechaFinEdicion) {
		FechaFinEdicion = fechaFinEdicion;
	}
	public String getEfectividadCambio() {
		return efectividadCambio;
	}
	public void setEfectividadCambio(String efectividadCambio) {
		this.efectividadCambio = efectividadCambio;
	}
	
	@Override
	public String toString() {
		return "HistoricoUsuarioRequest [cedulaEditada=" + cedulaEditada + ", usuarioEditor=" + usuarioEditor
				+ ", FechaInEdicion=" + FechaInEdicion + ", FechaFinEdicion=" + FechaFinEdicion + ", efectividadCambio="
				+ efectividadCambio + "]";
	}
	
	

}
