package com.tuti.accesoADatos;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tuti.entidades.Estacionamiento;


public interface EstacionamientoDAO extends JpaRepository<Estacionamiento, Long> {

	
	@Query("select e from Estacionamiento e where e.patente = ?1 LIMIT 1")
	Estacionamiento findByPatente(String patente);
//	public List<Estacionamiento> findByEstado(String estado);
}
