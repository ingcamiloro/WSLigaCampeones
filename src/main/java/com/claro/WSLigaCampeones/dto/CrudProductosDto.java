package com.claro.WSLigaCampeones.dto;

public class CrudProductosDto{
	
	private Long idProducto;
	private Long idCategoria;
	private String nombreCategoria;
	private String Titulo;
	private String descripcionCorta;
	private  String  imagen; 
	private Long estado;
	private Long destacado;
	private Long masRedimido;
	private Long unidadesDisponibles;
	private Long costo;
	private Long puntos;
	private String informacionDetallada;
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public String getNombreCategoria() {
		return nombreCategoria;
	}
	public void setNombreCategoria(String nombreCategoria) {
		this.nombreCategoria = nombreCategoria;
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
	public Long getIdCategoria() {
		return idCategoria;
	}
	public void setIdCategoria(Long idCategoria) {
		this.idCategoria = idCategoria;
	}
	public Long getUnidadesDisponibles() {
		return unidadesDisponibles;
	}
	public void setUnidadesDisponibles(Long unidadesDisponibles) {
		this.unidadesDisponibles = unidadesDisponibles;
	}
	public Long getCosto() {
		return costo;
	}
	public void setCosto(Long costo) {
		this.costo = costo;
	}
	public Long getPuntos() {
		return puntos;
	}
	public void setPuntos(Long puntos) {
		this.puntos = puntos;
	}
	public String getInformacionDetallada() {
		return informacionDetallada;
	}
	public void setInformacionDetallada(String informacionDetallada) {
		this.informacionDetallada = informacionDetallada;
	}
	@Override
	public String toString() {
		return "CrudProductosDto [idProducto=" + idProducto + ", idCategoria=" + idCategoria + ", nombreCategoria="
				+ nombreCategoria + ", Titulo=" + Titulo + ", descripcionCorta=" + descripcionCorta + ", imagen="
				+ imagen + ", estado=" + estado + ", destacado=" + destacado + ", masRedimido=" + masRedimido
				+ ", unidadesDisponibles=" + unidadesDisponibles + ", costo=" + costo + ", puntos=" + puntos
				+ ", informacionDetallada=" + informacionDetallada + "]";
	}

	

}
