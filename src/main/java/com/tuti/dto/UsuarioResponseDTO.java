package com.tuti.dto;

import org.springframework.hateoas.RepresentationModel;

import com.tuti.entidades.Persona;
import com.tuti.entidades.Usuario;

/**
 * Objeto utilizado para construir la respuesta de los servicios
 * 
 *
 */
public class UsuarioResponseDTO extends RepresentationModel<UsuarioResponseDTO> {

	private Long dni;
	private String apellido;
	private String nombre;
	private String patente;
	private Double saldoCuenta;

	public UsuarioResponseDTO() {
		super();
	}

	public UsuarioResponseDTO(Usuario pojo) {
		super();
		this.apellido = pojo.getApellido();
		this.nombre = pojo.getNombre();
		this.dni = pojo.getDni();
		this.patente = pojo.getPatente();
		this.saldoCuenta = pojo.getSaldoCuenta();

	}

	public Long getDni() {
		return dni;
	}

	public void setDni(Long dni) {
		this.dni = dni;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public Double getSaldoCuenta() {
		return saldoCuenta;
	}

	public void setSaldoCuenta(Double saldoCuenta) {
		this.saldoCuenta = saldoCuenta;
	}

	@Override
	public String toString() {
		return dni + " - " + nombre + " " + apellido + " " + patente + " " + saldoCuenta;
	}

}
