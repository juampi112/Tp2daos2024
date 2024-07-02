package com.tuti.presentacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.EstacionamientoResponseDTO;
import com.tuti.entidades.Estacionamiento;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.EstacionamientoService;
import com.tuti.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.tags.Tag;


/**
 * Recurso Usuario
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/Estacionamiento")
//@Api(tags = { SwaggerConfig.USUARIO })
@Tag(name = "Estacionamiento", description = "Estacionamiento")
public class EstacionamientoRestController {

	@Autowired
	private EstacionamientoService service;

	@Autowired
	private UsuarioService serviceU;
	/**
	 * Permite filtrar personas. Ej1 curl --location --request GET
	 * 'http://localhost:8081/personas?apellido=Perez&&nombre=Juan' Lista las
	 * personas llamadas Perez, Juan Ej2 curl --location --request GET
	 * 'http://localhost:8081/personas?apellido=Perez' Lista aquellas personas de
	 * apellido PErez Ej3 curl --location --request GET
	 * 'http://localhost:8081/personas' Lista todas las personas
	 * 
	 * @param apellido
	 * @param nombre
	 * @return
	 * @throws Excepcion
	 */
/*	@Operation(summary = "Permite filtrar usuarios. ")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UsuarioResponseDTO> filtrarUsuario(@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "nombre", required = false) String nombre) throws Excepcion {

		List<Usuario> usuario = service.getUsuario(apellido, nombre);
		List<UsuarioResponseDTO> dtos = new ArrayList<UsuarioResponseDTO>();
		for (Usuario pojo : usuario) {

			dtos.add(buildResponse(pojo));
		}
		return dtos;
	}*/

	/*
	 * public UsuarioResponseDTO(Usuario pojo) { super(); this.apellido =
	 * pojo.getApellido(); this.nombre = pojo.getNombre(); this.dni = pojo.getDni();
	 * this.patente = pojo.getPatente(); this.saldoCuenta = pojo.getSaldoCuenta();
	 * 
	 * }
	 */

	/**
	 * Busca una persona a partir de su dni curl --location --request GET
	 * 'http://localhost:8081/personas/27837171'
	 * 
	 * @param id DNI de la persona buscada
	 * @return Persona encontrada o Not found en otro caso
	 * @throws Excepcion
	 */
	
	
	
	
	
	
	
	
	
	
	
	@GetMapping(value = "/{patente}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EstacionamientoResponseDTO> getPatente(@PathVariable String patente,@RequestParam("password") String password) throws Excepcion {
	    
		boolean PasswordValida = service.validarPassword(patente, password);
	    
	    if (!PasswordValida) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }		
		
		Estacionamiento rta = service.getPatente(patente);
		Usuario usuario = serviceU.getUsuarioByPatente(patente);
		
		if (rta != null && usuario!= null) {
	        Estacionamiento pojo = rta;
	        return new ResponseEntity<EstacionamientoResponseDTO>(buildResponse(pojo,usuario), HttpStatus.OK);            			        	   
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();		
	}

	/**
	 * Inserta una nueva persona en la base de datos curl --location --request POST
	 * 'http://localhost:8081/personas' --header 'Accept: application/json' --header
	 * 'Content-Type: application/json' --data-raw '{ "dni": 27837171, "apellido":
	 * "perez", "nombre": "juan", "idCiudad": 2 }'
	 * 
	 * @param p Persona a insertar
	 * @return Persona insertada o error en otro caso
	 * @throws Exception
	 */
/*	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody UsuarioForm form, BindingResult result) throws Exception {
		// aca deberiamos traer una variable que obtenga todos los usuarios.

		List<Usuario> usuario = service.getAll();
		boolean noExisteDni = false;
		boolean noExistePatente = false;

		for (Usuario u : usuario) {
			if (u.getDni() == form.toPojo().getDni()) {
				noExisteDni = true;
				break;
			}
			if (u.getPatente().equals(form.toPojo().getPatente())) {
				noExistePatente = true;
				break;
			}
		}

		if (result.hasErrors()) {
			// Dos alternativas:
			// throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
			// this.formatearError(result));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
		} else if (form.toPojo().getSaldoCuenta() != null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
		} else if (noExisteDni) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(getError("03", "Dato no insertable", "No se puede insertar un dni duplicado."));
		} else if (noExistePatente) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(getError("03", "Dato no insertable", "No se puede insertar una patente duplicada."));
		}

		Usuario u = form.toPojo();

		/*
		 * Optional<Ciudad> c = ciudadService.getById(form.getIdCiudad()); if
		 * (c.isPresent()) p.setCiudad(c.get()); else { return
		 * ResponseEntity.status(HttpStatus.BAD_REQUEST).body( getError("02",
		 * "Ciudad Requerida",
		 * "La ciudad indicada no se encuentra en la base de datos.")); // return
		 * ResponseEntity.status(HttpStatus.BAD_REQUEST).
		 * body("La ciudad indicada no se encuentra en la base de datos.");
		 * 
		 * { { "dni": 20203131, "apellido": "Perez", "nombre":
		 * "Juan","patente":"123 ASD" }
		 * 
		 * 
		 * 
		 * }
		 */

	/*	// ahora inserto el cliente
		service.insert(u);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{dni}").buildAndExpand(u.getDni())
				.toUri(); // Por convención en REST, se devuelve la url del recurso recién creado

		return ResponseEntity.created(location).build();// 201 (Recurso creado correctamente)

	}*/

	/**
	 * Modifica una persona existente en la base de datos: curl --location --request
	 * PUT 'http://localhost:8081/personas/27837176' --header 'Accept:
	 * application/json' --header 'Content-Type: application/json' --data-raw '{
	 * "apellido": "Perez", "nombre": "Juan Martin" "idCiudad": 1 }'
	 * 
	 * @param p Persona a modificar
	 * @return Persona Editada o error en otro caso
	 * @throws Excepcion
	 */

/*	@PutMapping("/{dni}")
	public ResponseEntity<Object> actualizar(@RequestBody UsuarioForm form, @PathVariable long dni) throws Exception {
		Usuario rta = service.getBydni(dni);

		List<Usuario> usuario = service.getAll();
		boolean noExistePatente = false;

		for (Usuario u : usuario) {
			if (u.getPatente().equals(form.toPojo().getPatente()) && u.getDni() != form.toPojo().getDni()) {
				noExistePatente = true;
				break;
			}
		}

		if (rta == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encuentra el usuario que desea modificar.");
		else if (noExistePatente) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(getError("03", "Dato no editable", "No se puede modificar una patente."));
		} else {
			Usuario u = form.toPojo();
			if (!u.getDni().equals(dni))// El dni es el identificador, con lo cual es el único dato que no permito
										// modificar
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(getError("03", "Dato no editable", "No se puede modificar el dni."));
			service.update(u);
			return ResponseEntity.ok(buildResponse(u));
		}

	}
*/

	/**
	 * Métdo auxiliar que toma los datos del pojo y construye el objeto a devolver
	 * en la response, con su hipervinculos
	 * 
	 * @param pojo
	 * @return
	 * @throws Excepcion
	 */
	private EstacionamientoResponseDTO buildResponse(Estacionamiento pojo, Usuario usuario) throws Excepcion {
		try {

/*
	        if (usuario != null) {	        	

	            Link link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(YourControllerClass.class).getDatosS01(usuario.getDni())).withRel("datosS01");


	            response.setDni(usuario.getDni());
	            response.add(link);
	            
				        	
	        } 			
			*/
			
			
			
			
			
			
			
			EstacionamientoResponseDTO dto = new EstacionamientoResponseDTO(pojo);
			Link selfLink = WebMvcLinkBuilder.linkTo(EstacionamientoRestController.class).slash(pojo.getPatente()).withSelfRel();
			dto.add(selfLink);

			return dto;
			// UsuarioResponseDTO dto = new UsuarioResponseDTO(pojo);
			// return dto;
		} catch (Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}

	private String formatearError(BindingResult result) throws JsonProcessingException {
//		primero transformamos la lista de errores devuelta por Java Bean Validation
		List<Map<String, String>> errores = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		MensajeError e1 = new MensajeError();
		e1.setCodigo("01");
		e1.setMensajes(errores);

		// ahora usamos la librería Jackson para pasar el objeto a json
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

	/* controlar esto de get error */
	private String getError(String code, String err, String descr) throws JsonProcessingException {
		MensajeError e1 = new MensajeError();
		e1.setCodigo(code);
		ArrayList<Map<String, String>> errores = new ArrayList<>();
		Map<String, String> error = new HashMap<String, String>();
		error.put(err, descr);
		errores.add(error);
		e1.setMensajes(errores);

		// ahora usamos la librería Jackson para pasar el objeto a json
		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

}
