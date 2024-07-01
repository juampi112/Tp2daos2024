package com.tuti.entidades;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Estacionamiento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String patente;
	private String contraDeUsuario;
	private String estado;

	

	public Estacionamiento() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Estacionamiento(Long id, String patente, String contraDeUsuario, String estado) {
		super();
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
