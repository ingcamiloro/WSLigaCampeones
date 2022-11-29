package com.claro.WSLigaCampeones.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UsuariosSistemaDto {
	
	private String cedulaUsuario;
	private String nombres;
	private String apellido;
	private String direccion;
	private Long celular;
	private String correo;
	private String aceptaTerminos;
	private String departamento;
	private String ciudad;
	private Integer puntosRestantes;

}
