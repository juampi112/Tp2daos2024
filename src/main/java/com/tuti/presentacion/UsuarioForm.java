package com.tuti.presentacion;

import com.tuti.entidades.Usuario;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UsuarioForm {

	@NotNull(message = "el dni no puede ser nulo")
	// @Min(7000000)
	private Long dni;
	@NotNull
	@Size(min = 2, max = 30)
	private String apellido;
	@NotNull
	@Size(min = 2, max = 30)
	private String nombre;
	@NotNull
	private String patente;

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

	public Usuario toPojo() {
		Usuario u = new Usuario();
		u.setDni(this.getDni());
		u.setApellido(this.getApellido());
		u.setNombre(this.getNombre());
		u.setPatente(this.getPatente());
		return u;
	}
}
