package com.tuti.servicios;

import java.util.List;

import com.tuti.entidades.Usuario;

public interface UsuarioService {
	
	public List<Usuario> getAll();


	public Usuario getBydni(Long dni);

	
	public void update(Usuario u);


	public void insert(Usuario u) throws Exception;

	
	public void delete(Long dni);
	
	public List<Usuario> getUsuario(String apellido, String nombre);
}

