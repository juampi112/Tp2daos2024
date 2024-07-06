package com.tuti.presentacion;

import java.net.URI;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.UsuarioResponseDTO;
import com.tuti.entidades.Estacionamiento;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.EstacionamientoService;
import com.tuti.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * Recurso Usuario
 * 
 * @author
 *
 */
@RestController
@RequestMapping("/usuario")
//@Api(tags = { SwaggerConfig.USUARIO })
@Tag(name = "Usuario", description = "Usuario")
public class UsuarioRestController {

	@Autowired
	private UsuarioService service;
	@Autowired
	private EstacionamientoService serviceEstacionamiento;

	@Operation(summary = "Permite filtrar usuarios. ")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<UsuarioResponseDTO> filtrarUsuario(@RequestParam(name = "apellido", required = false) String apellido,
			@RequestParam(name = "nombre", required = false) String nombre) throws Excepcion {

		List<Usuario> usuario = service.getUsuario(apellido, nombre);
		List<UsuarioResponseDTO> dtos = new ArrayList<UsuarioResponseDTO>();
		for (Usuario pojo : usuario) {
			dtos.add(buildResponse(pojo));
		}
		return dtos;
	}

	@GetMapping(value = "/{dni}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<UsuarioResponseDTO> getBydni(@PathVariable Long dni) throws Excepcion {
		Usuario rta = service.getBydni(dni);
		if (rta != null) {
			Usuario pojo = rta;
			return new ResponseEntity<UsuarioResponseDTO>(buildResponse(pojo), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

	}

	@PostMapping
	public ResponseEntity<Object> guardar(@Valid @RequestBody UsuarioForm form, BindingResult result) throws Exception {

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

		service.insert(u);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{dni}").buildAndExpand(u.getDni())
				.toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/{dni}")
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
			if (!u.getDni().equals(dni))
				return ResponseEntity.status(HttpStatus.BAD_REQUEST)
						.body(getError("03", "Dato no editable", "No se puede modificar el dni."));
			service.update(u);
			return ResponseEntity.ok(buildResponse(u));
		}
	}

	@DeleteMapping("/{dni}")
	public ResponseEntity<String> eliminar(@PathVariable Long dni) {
		if (service.getBydni(dni) == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No existe una persona con ese dni");
		service.delete(dni);

		return ResponseEntity.ok().build();
	}

	private UsuarioResponseDTO buildResponse(Usuario pojo) throws Excepcion {
		try {

			UsuarioResponseDTO dto = new UsuarioResponseDTO(pojo);
			Estacionamiento est = new Estacionamiento();

			est = serviceEstacionamiento.getPatente(pojo.getPatente());

			Link selfLink = WebMvcLinkBuilder.linkTo(UsuarioRestController.class).slash(pojo.getDni())
					.withRel("Usuario");

			dto.add(selfLink);

			if (est != null) {
				Link estacionLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
						.methodOn(EstacionamientoRestController.class).getPatenteEstacionamiento(pojo.getPatente()))
						.withRel("Estacionamiento");
				dto.add(estacionLink);
			}

			return dto;

		} catch (Exception e) {
			throw new Excepcion(e.getMessage(), 500);
		}
	}

	private String formatearError(BindingResult result) throws JsonProcessingException {
		List<Map<String, String>> errores = result.getFieldErrors().stream().map(err -> {
			Map<String, String> error = new HashMap<>();
			error.put(err.getField(), err.getDefaultMessage());
			return error;
		}).collect(Collectors.toList());
		MensajeError e1 = new MensajeError();
		e1.setCodigo("01");
		e1.setMensajes(errores);

		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

	private String getError(String code, String err, String descr) throws JsonProcessingException {
		MensajeError e1 = new MensajeError();
		e1.setCodigo(code);
		ArrayList<Map<String, String>> errores = new ArrayList<>();
		Map<String, String> error = new HashMap<String, String>();
		error.put(err, descr);
		errores.add(error);
		e1.setMensajes(errores);

		ObjectMapper maper = new ObjectMapper();
		String json = maper.writeValueAsString(e1);
		return json;
	}

}
