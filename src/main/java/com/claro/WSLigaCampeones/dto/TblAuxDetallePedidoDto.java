package com.claro.WSLigaCampeones.dto;

public class TblAuxDetallePedidoDto {
	
	   private Long idProducto;
	   private Long cantidad;
	public Long getIdProducto() {
		return idProducto;
	}
	public void setIdProducto(Long idProducto) {
		this.idProducto = idProducto;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	@Override
	public String toString() {
		return "TblAuxDetallePedidoDto [idProducto=" + idProducto + ", cantidad=" + cantidad + "]";
	}
	   
	   

	}
