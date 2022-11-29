package com.claro.WSLigaCampeones.dto;

import java.util.List;

import org.springframework.http.ResponseEntity;

public class PedidoResponse {
	
	private Integer numeroPedido;
	private Integer codigo;
	private String descripcion;
	private List<OutActualizarPedidoDto> outActualizarPedidoDto;
	public Integer getNumeroPedido() {
		return numeroPedido;
	}
	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public List<OutActualizarPedidoDto> getOutActualizarPedidoDto() {
		return outActualizarPedidoDto;
	}
	public void setOutActualizarPedidoDto(List<OutActualizarPedidoDto> outActualizarPedidoDto) {
		this.outActualizarPedidoDto = outActualizarPedidoDto;
	}
	@Override
	public String toString() {
		return "PedidoResponse [numeroPedido=" + numeroPedido + ", codigo=" + codigo + ", descripcion=" + descripcion
				+ ", outActualizarPedidoDto=" + outActualizarPedidoDto + "]";
	}


	
	
}
