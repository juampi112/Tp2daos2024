package com.tuti.entidades;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estacionamiento {

	private String patente;
	private String contraDeUsuario;
	// PODRIA SER UN BOOLEAN CON UNA FUNCION?
	private String estado;

	public Estacionamiento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Estacionamiento(String patente, String contraDeUsuario, String estado) {
		super();
		this.patente = patente;
		this.contraDeUsuario = contraDeUsuario;
		this.estado = estado;
	}

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

}
