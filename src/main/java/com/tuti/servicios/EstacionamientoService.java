package com.tuti.servicios;


import com.tuti.entidades.Estacionamiento;

public interface EstacionamientoService {

//	public Estacionamiento getByid(Long id);

	
	public Estacionamiento getPatente(String patente);
	
	public void insert(Estacionamiento e) throws Exception;
	
	public void update(Estacionamiento e);


	
}

