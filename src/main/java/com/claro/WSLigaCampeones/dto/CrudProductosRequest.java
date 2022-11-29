package com.claro.WSLigaCampeones.dto;

public class CrudProductosRequest {
	
	private Long idProducto;
	private Long idCategoria;
	private String Titulo;
	private String descripcionCorta;
	private String imagen; 
	private Long estado;
	private Long destacado;
	private Long masRedimido;
	private Integer unidadesDisponibles;
	private Long costos;	
	private String informacionDetallada;
	private Long puntos;


	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public String getTitulo() {
		return Titulo;
	}
	public void setTitulo(String titulo) {
		Titulo = titulo;
	}
	public String getDescripcionCorta() {
		return descripcionCorta;
	}
	public void setDescripcionCorta(String descripcionCorta) {
		this.descripcionCorta = descripcionCorta;
	}
	public String getImagen() {
		return imagen;
	}
	public void setImagen(String imagen) {
		this.imagen = imagen;
	}
	public Long getEstado() {
		return estado;
	}
	public void setEstado(Long estado) {
		this.estado = estado;
	}
	public Long getDestacado() {
		return destacado;
	}
	public void setDestacado(Long destacado) {
		this.destacado = destacado;
	}
	public Long getMasRedimido() {
		return masRedimido;
	}
	public void setMasRedimido(Long masRedimido) {
		this.masRedimido = masRedimido;
	}
	public Integer getUnidadesDisponibles() {
		return unidadesDisponibles;
	}
	public void setUnidadesDisponibles(Integer unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}
	public Long getCostos() {
		return costos;
	}
	public void setCostos(Long costos) {
		this.costos = costos;
	}
	public String getInformacionDetallada() {
		return informacionDetallada;
	}
	public void setInformacionDetallada(String informacionDetallada) {
		this.informacionDetallada = informacionDetallada;
	}
	public Long getPuntos() {
		return puntos;
	}
	public void setPuntos(Long puntos) {
		this.puntos = puntos;
	}
	@Override
	public String toString() {
		return "CrudProductosRequest [idProducto=" + idProducto + ", idCategoria=" + idCategoria + ", Titulo=" + Titulo
				+ ", descripcionCorta=" + descripcionCorta + ", imagen=" + imagen + ", estado=" + estado
				+ ", destacado=" + destacado + ", masRedimido=" + masRedimido + ", unidadesDisponibles="
				+ unidadesDisponibles + ", costos=" + costos + ", informacionDetallada=" + informacionDetallada
				+ ", puntos=" + puntos + "]";
	}
	
}
