package com.claro.WSLigaCampeones.security.dto;

public class JwtDto {
	private int codigo;
    private String token;
    

    public JwtDto(String token, int codigo) {
    	this.codigo=codigo;
    	this.token=token;
    	

    }
    

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}



}
