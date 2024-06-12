package com.tuti.presentacion;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tuti.dto.CotizacionDolarDTO;
import com.tuti.exception.Excepcion;
import com.tuti.servicios.DolarProxy;
/**
 * Recurso Personas
 * @author dardo
 *
 */
@RestController
@RequestMapping("/dolar")
public class DolarRestController {
	
	@Autowired
	private DolarProxy service; 
	
	
	
	
	/**
	 * Busca la cotización del dolar desde un servicio externo. 
	 * 	curl --location --request GET 'http://localhost:8081/dolar'
	 * @return Cotización actual del dolar
	 */
	@GetMapping( produces = { MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<List<CotizacionDolarDTO>> getCotizacionActual() throws Excepcion
	{
		List<CotizacionDolarDTO> rta = service.getCotizaciones();
		if(rta.size()>0)
		{
			return new ResponseEntity<List<CotizacionDolarDTO>>(rta, HttpStatus.OK);
		}
		else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		
	}
	
	
	
	
	
	
	

}
