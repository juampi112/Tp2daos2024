package com.tuti.accesoADatos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tuti.entidades.Ciudad;

public interface CiudadDAO extends JpaRepository<Ciudad, Long>{

}
