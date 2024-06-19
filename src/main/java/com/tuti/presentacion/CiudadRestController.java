package com.tuti.presentacion;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuti.entidades.Ciudad;
import com.tuti.servicios.CiudadService;

import io.swagger.v3.oas.annotations.tags.Tag;




@RestController
@RequestMapping("/ciudades")
/**
 *  Recurso ciudades
 *  @author dardo
 *
 */
//@Api(tags = { SwaggerConfig.CIUDADES })
@Tag(name = "Ciudades", description = "Ciudades")
public class CiudadRestController {


	@Autowired
	private CiudadService service;
	
	/**
	 * Obtiene una ciudad a través de su id.
	 *  curl --location --request GET 'http://localhost:8081/ciudades/3'
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	@GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Ciudad> getById(@PathVariable Long id) throws Exception
	{
		Optional<Ciudad> rta=service.getById(id);
		if(rta.isPresent())
		{
			Ciudad pojo=rta.get();
			return new ResponseEntity<Ciudad>(pojo, HttpStatus.OK);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	
	
}
