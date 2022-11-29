package com.claro.WSLigaCampeones.dto;

import java.util.ArrayList;

public class UsuariosResponse {
	
	private int codigo;
	private String resultado;
	private ArrayList<UsuariosRequest> cursorUsuarios;
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getResultado() {
		return resultado;
	}
	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	public ArrayList<UsuariosRequest> getCursorUsuarios() {
		return cursorUsuarios;
	}
	public void setCursorUsuarios(ArrayList<UsuariosRequest> cursorUsuarios) {
		this.cursorUsuarios = cursorUsuarios;
	}
	@Override
	public String toString() {
		return "UsuariosResponse [codigo=" + codigo + ", resultado=" + resultado + ", cursorUsuarios=" + cursorUsuarios
				+ "]";
	}
	
}
