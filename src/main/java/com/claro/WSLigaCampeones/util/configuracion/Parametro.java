package com.claro.WSLigaCampeones.util.configuracion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;



@Entity
@Table(name="T_PARAMETROS")
@NamedQuery(name = "Parametro.findByParNombre", query = "SELECT p FROM Parametro p WHERE p.parNombre = :parNombre")
public class Parametro implements Serializable{
	
	private static final long serialVersionUID = 3124636808488372121L;

	@Id
	@ApiModelProperty(notes = "Nombre único del parámetro en Base de Datos - ID")
	private String parNombre;
	
	@Column(name = "PAR_VALOR")
	@ApiModelProperty(notes = "Valor del parámetro")
	private String parValor;
	
	@Column(name = "PAR_DESCRIPCION")
	@ApiModelProperty(notes = "Descripción básica del parámetro")
	private String parDescripcion;
	
	@Column(name = "PAR_ESTADO")
	@ApiModelProperty(notes = "Estado del parámetro - I-Inactivo, A-Activo.")
    private String parEstado;
	
    @Column(name = "PAR_TIPO")
    @ApiModelProperty(notes = "Tipificación del parámetro: Input, Input Text, List, etc.")
    private String parTipo;
	/**
	 * @return the parNombre
	 */
	
	public String getParNombre() {
		return parNombre;
	}
	/**
	 * @param parNombre the parNombre to set
	 */
	public void setParNombre(String parNombre) {
		this.parNombre = parNombre;
	}
	/**
	 * @return the parValor
	 */
	public String getParValor() {
		return parValor;
	}
	/**
	 * @param parValor the parValor to set
	 */
	public void setParValor(String parValor) {
		this.parValor = parValor;
	}
	/**
	 * @return the parDescripcion
	 */
	public String getParDescripcion() {
		return parDescripcion;
	}
	/**
	 * @param parDescripcion the parDescripcion to set
	 */
	public void setParDescripcion(String parDescripcion) {
		this.parDescripcion = parDescripcion;
	}
	/**
	 * @return the parEstado
	 */
	public String getParEstado() {
		return parEstado;
	}
	/**
	 * @param parEstado the parEstado to set
	 */
	public void setParEstado(String parEstado) {
		this.parEstado = parEstado;
	}
	/**
	 * @return the parTipo
	 */
	public String getParTipo() {
		return parTipo;
	}
	/**
	 * @param parTipo the parTipo to set
	 */
	public void setParTipo(String parTipo) {
		this.parTipo = parTipo;
	}
	
	
	
		

}
