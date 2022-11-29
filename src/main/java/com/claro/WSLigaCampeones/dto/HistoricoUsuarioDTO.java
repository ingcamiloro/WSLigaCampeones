package com.claro.WSLigaCampeones.dto;

import java.util.Date;

public class HistoricoUsuarioDTO {
	
	private String cedula;
	private String nombre;
	private String apellido;
	private String correo;
	private String celular;
	private String barrio;
	private String canal;
	private String direccion;
	private String idCiudad;
	private String nombreCiudad;
	private String idDepartamento;
	private String nombreDepartamento;
	private Date fechaRegistro;
	private String usuarioCambio;
	private String accion;
	private Long codRespuesta;
	private String descRespuesta;
	
	public String getCedula() {
		return cedula;
	}
	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getCorreo() {
		return correo;
	}
	public void setCorreo(String correo) {
		this.correo = correo;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
		this.celular = celular;
	}
	public String getBarrio() {
		return barrio;
	}
	public void setBarrio(String barrio) {
		this.barrio = barrio;
	}
	public String getCanal() {
		return canal;
	}
	public void setCanal(String canal) {
		this.canal = canal;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public Date getFechaRegistro() {
		return fechaRegistro;
	}
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	public String getUsuarioCambio() {
		return usuarioCambio;
	}
	public void setUsuarioCambio(String usuarioCambio) {
		this.usuarioCambio = usuarioCambio;
	}
	public String getIdCiudad() {
		return idCiudad;
	}
	public void setIdCiudad(String idCiudad) {
		this.idCiudad = idCiudad;
	}
	public String getNombreCiudad() {
		return nombreCiudad;
	}
	public void setNombreCiudad(String nombreCiudad) {
		this.nombreCiudad = nombreCiudad;
	}
	public String getIdDepartamento() {
		return idDepartamento;
	}
	public void setIdDepartamento(String idDepartamento) {
		this.idDepartamento = idDepartamento;
	}
	public String getNombreDepartamento() {
		return nombreDepartamento;
	}
	public void setNombreDepartamento(String nombreDepartamento) {
		this.nombreDepartamento = nombreDepartamento;
	}
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public Long getCodRespuesta() {
		return codRespuesta;
	}
	public void setCodRespuesta(Long codRespuesta) {
		this.codRespuesta = codRespuesta;
	}
	public String getDescRespuesta() {
		return descRespuesta;
	}
	public void setDescRespuesta(String descRespuesta) {
		this.descRespuesta = descRespuesta;
	}
	@Override
	public String toString() {
		return "HistoricoUsuarioDTO [cedula=" + cedula + ", nombre=" + nombre + ", apellido=" + apellido + ", correo="
				+ correo + ", celular=" + celular + ", barrio=" + barrio + ", canal=" + canal + ", direccion="
				+ direccion + ", idCiudad=" + idCiudad + ", nombreCiudad=" + nombreCiudad + ", idDepartamento="
				+ idDepartamento + ", nombreDepartamento=" + nombreDepartamento + ", fechaRegistro=" + fechaRegistro
				+ ", usuarioCambio=" + usuarioCambio + ", accion=" + accion + ", codRespuesta=" + codRespuesta
				+ ", descRespuesta=" + descRespuesta + "]";
	}
	

}
