package com.claro.WSLigaCampeones.dto;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class OutHistoricoRedencionDto {

	private String cedulaUsuario;
	private Long celular;
	private String nombre;
	private String apellido;
	private String correo;
	private String nombreCategoria;
	private String nombreProducto;
	private Integer cantidad;
	private Long Costo;
	private Long puntos;
	private String departamento;
	private String ciudad;
	private String direccion;
	private String motivoRechazo;
	private Long numeroPedido;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd/MM/yyyy", timezone="GMT-5")
	private Date fechaPedido;
	private Long idCategoria;
	private Long idProducto;	
	private String estado;
	private Long idDepartamento;	
	private Long idCiudad;	
	private String descripcionCortaProducto;
	private String informacionDetalladaProducto;
	public String getCedulaUsuario() {
		return cedulaUsuario;
	}
	public void setCedulaUsuario(String cedulaUsuario) {
		this.cedulaUsuario = cedulaUsuario;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
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
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	public Integer getCantidad() {
		return cantidad;
	}
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	public Long getCosto() {
		return Costo;
	}
	public void setCosto(Long costo) {
		Costo = costo;
	}
	public Long getPuntos() {
		return puntos;
	}
	public void setPuntos(Long puntos) {
		this.puntos = puntos;
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
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getMotivoRechazo() {
		return motivoRechazo;
	}
	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}
	public Long getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(Long numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public Date getFechaPedido() {
		return fechaPedido;
	}
	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
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
	public String getDescripcionCortaProducto() {
		return descripcionCortaProducto;
	}
	public void setDescripcionCortaProducto(String descripcionCortaProducto) {
		this.descripcionCortaProducto = descripcionCortaProducto;
	}
	public String getInformacionDetalladaProducto() {
		return informacionDetalladaProducto;
	}
	public void setInformacionDetalladaProducto(String informacionDetalladaProducto) {
		this.informacionDetalladaProducto = informacionDetalladaProducto;
	}
	@Override
	public String toString() {
		return "OutHistoricoRedencionDto [cedulaUsuario=" + cedulaUsuario + ", celular=" + celular + ", nombre="
				+ nombre + ", correo=" + correo + ", nombreCategoria=" + nombreCategoria + ", nombreProducto="
				+ nombreProducto + ", cantidad=" + cantidad + ", Costo=" + Costo + ", puntos=" + puntos
				+ ", departamento=" + departamento + ", ciudad=" + ciudad + ", direccion=" + direccion
				+ ", motivoRechazo=" + motivoRechazo + ", numeroPedido=" + numeroPedido + ", fechaPedido=" + fechaPedido
				+ ", idCategoria=" + idCategoria + ", idProducto=" + idProducto + ", estado=" + estado
				+ ", idDepartamento=" + idDepartamento + ", idCiudad=" + idCiudad + ", descripcionCortaProducto="
				+ descripcionCortaProducto + ", informacionDetalladaProducto=" + informacionDetalladaProducto + "]";
	}
	
	
}
