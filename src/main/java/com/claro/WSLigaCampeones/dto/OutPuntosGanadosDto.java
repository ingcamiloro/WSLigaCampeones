package com.claro.WSLigaCampeones.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OutPuntosGanadosDto {
	
	private String cedulaUsuario;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaActivacion;
	private String producto;
	private String cedulaCliente;
	private String ctaMin;
	private Long puntos;
	private String estado;
	private String descripcion;
	
	public String getCedulaUsuario() {
		return cedulaUsuario;
	}
	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}
	public Date getFechaActivacion() {
		return fechaActivacion;
	}
	public void setFechaActivacion(Date fechaActivacion) {
		this.fechaActivacion = fechaActivacion;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getCedulaCliente() {
		return cedulaCliente;
	}
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}
	public String getCtaMin() {
		return ctaMin;
	}
	public void setCtaMin(String ctaMin) {
		this.ctaMin = ctaMin;
	}
	public Long getPuntos() {
		return puntos;
	}
	public void setPuntos(Long puntos) {
		this.puntos = puntos;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	@Override
	public String toString() {
		return "OutPuntosGanadosDto [cedulaUsuario=" + cedulaUsuario + ", fechaActivacion=" + fechaActivacion
				+ ", producto=" + producto + ", cedulaCliente=" + cedulaCliente + ", ctaMin=" + ctaMin + ", puntos="
				+ puntos + ", estado=" + estado + ", descripcion=" + descripcion + "]";
	}
	
	
	

}
