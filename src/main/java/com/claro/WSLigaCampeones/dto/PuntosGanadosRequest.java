package com.claro.WSLigaCampeones.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class PuntosGanadosRequest {
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaActivacionIn;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaActivacionFinal;
	private String cedulaCliente;
	private String cedulaUsuario;
	private String cuentaMin;
	private String producto;
	private String estado;
	public Date getFechaActivacionIn() {
		return fechaActivacionIn;
	}
	public void setFechaActivacionIn(Date fechaActivacionIn) {
		this.fechaActivacionIn = fechaActivacionIn;
	}
	public Date getFechaActivacionFinal() {
		return fechaActivacionFinal;
	}
	public void setFechaActivacionFinal(Date fechaActivacionFinal) {
		this.fechaActivacionFinal = fechaActivacionFinal;
	}
	public String getCedulaCliente() {
		return cedulaCliente;
	}
	public void setCedulaCliente(String cedulaCliente) {
		this.cedulaCliente = cedulaCliente;
	}
	public String getCedulaUsuario() {
		return cedulaUsuario;
	}
	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}
	public String getCuentaMin() {
		return cuentaMin;
	}
	public void setCuentaMin(String cuentaMin) {
		this.cuentaMin = cuentaMin;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("InPuntosGanadosDto [fechaActivacionIn=");
		builder.append(fechaActivacionIn);
		builder.append(", fechaActivacionFinal=");
		builder.append(fechaActivacionFinal);
		builder.append(", cedulaCliente=");
		builder.append(cedulaCliente);
		builder.append(", cedulaUsuario=");
		builder.append(cedulaUsuario);
		builder.append(", cuentaMin=");
		builder.append(cuentaMin);
		builder.append(", producto=");
		builder.append(producto);
		builder.append(", estado=");
		builder.append(estado);
		builder.append("]");
		return builder.toString();
	}
	
	
	

}
