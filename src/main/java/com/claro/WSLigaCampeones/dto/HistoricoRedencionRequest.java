package com.claro.WSLigaCampeones.dto;



import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HistoricoRedencionRequest {
	
	private String cedula;
	private String celular;
	private String nombre;
	private String correo;
	private Long idProducto;
	private String producto;
	private Long idDepartamento;
	private String departamento;
	private Long idCiudad;
	private String ciudad;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaPedido;
	private String estado;
	private Long numeroPedido;
	
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getCiudad() {
		return ciudad;
	}
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Long getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(Long idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public Long getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(Long idCiudad) {
		this.idCiudad = idCiudad;
	}
	public Long getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	@Override
	public String toString() {
		return "HistoricoRedencionRequest [cedula=" + cedula + ", celular=" + celular + ", nombre=" + nombre
				+ ", correo=" + correo + ", idProducto=" + idProducto + ", producto=" + producto + ", idDepartamento="
				+ idDepartamento + ", departamento=" + departamento + ", idCiudad=" + idCiudad + ", ciudad=" + ciudad
				+ ", fechaPedido=" + fechaPedido + ", estado=" + estado + ", numeroPedido=" + numeroPedido + "]";
	}

	
	

}
