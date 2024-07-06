package com.tuti.servicios;

import java.util.List;

import com.tuti.entidades.Estacionamiento;

public interface EstacionamientoService {

	public List<Estacionamiento> getAll();

	public Estacionamiento getPatente(String patente);

	public void insert(Estacionamiento e) throws Exception;

	public void update(Estacionamiento e);

	public Boolean validarPassword(String patente, String password);

}
