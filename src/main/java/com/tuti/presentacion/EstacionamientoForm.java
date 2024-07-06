package com.tuti.presentacion;

import com.tuti.entidades.Estacionamiento;
import jakarta.validation.constraints.NotNull;

public class EstacionamientoForm {

	@NotNull
	private String patente;
	@NotNull
	private String contraDeUsuario;
	@NotNull
	private String estado;

	public String getPatente() {
		return patente;
	}

	public void setPatente(String patente) {
		this.patente = patente;
	}

	public String getContraDeUsuario() {
		return contraDeUsuario;
	}

	public void setContraDeUsuario(String contraDeUsuario) {
		this.contraDeUsuario = contraDeUsuario;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Estacionamiento toPojo() {
		Estacionamiento e = new Estacionamiento();
		e.setPatente(this.getPatente());
		e.setContraDeUsuario(this.getContraDeUsuario());
		e.setEstado(this.getEstado());
		return e;
	}

}
