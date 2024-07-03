package com.tuti.dto;

import org.springframework.hateoas.RepresentationModel;

import com.tuti.entidades.Estacionamiento;


/**
 * Objeto utilizado para construir la respuesta de los servicios
 * 
 *
 */
public class EstacionamientoResponseDTO extends RepresentationModel<EstacionamientoResponseDTO> {

	private String patente;
	private String estado;
	private Long dni;	

	public EstacionamientoResponseDTO() {
		super();
	}


	public EstacionamientoResponseDTO(String patente, String estado, Long dni) {
		super();
		this.patente = patente;
		this.estado = estado;
		this.dni = dni;
	}


	public EstacionamientoResponseDTO(Estacionamiento pojo) {
		super();
		this.patente = pojo.getPatente();
		this.estado = pojo.getEstado();
	}


	public String getPatente() {
		return patente;
	}



	public void setPatente(String patente) {
		this.patente = patente;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Long getDni() {
		return dni;
	}


	public void setDni(Long dni) {
		this.dni = dni;
	}


	@Override
	public String toString() {
		return estado + " " + patente;
	}

}
