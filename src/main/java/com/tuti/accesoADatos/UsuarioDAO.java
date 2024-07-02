package com.tuti.accesoADatos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuti.entidades.Usuario;

public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
	//public Usuario findByPatenteVehiculo(String patenteVehiculo);

	public Usuario findBydni(Long dni);

	public List<Usuario> findByApellidoOrNombre(String apellido, String nombre);
	
	public Usuario findByPatente(String patente);
	
}
