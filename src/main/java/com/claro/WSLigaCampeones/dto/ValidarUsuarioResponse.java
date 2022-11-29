package com.claro.WSLigaCampeones.dto;

public class ValidarUsuarioResponse {
	
	private String token;
	private int codigo;
	private String descripcion;	
	private UsuariosRequest usuario;
	
	
	public ValidarUsuarioResponse() {
	}
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public UsuariosRequest getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuariosRequest usuario) {
		this.usuario = usuario;
	}
	
	

	@Override
	public String toString() {
		return "ValidarUsuarioResponse [codigo=" + codigo + ", descripcion=" + descripcion + ", usuario=" + usuario
				+ "]";
	}
}
