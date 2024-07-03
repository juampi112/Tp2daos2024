package com.tuti.accesoADatos;



import java.awt.print.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tuti.entidades.Estacionamiento;


public interface EstacionamientoDAO extends JpaRepository<Estacionamiento, Long> {

	Estacionamiento findTopByPatente(String patente);
	//@Query("select e from Estacionamiento e where e.patente = ?1")
	//Estacionamiento findByPatente(String patente, Pageable pageable);
//	public List<Estacionamiento> findByEstado(String estado);
	Estacionamiento findTopByPatenteOrderByIdDesc(String patente);
	
	@Query("select count(e) > 0 from Estacionamiento e where e.patente = ?1 and e.contraDeUsuario = ?2")
	public Boolean validarPassword(String patente,String password);
}
