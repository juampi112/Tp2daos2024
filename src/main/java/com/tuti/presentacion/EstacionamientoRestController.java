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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuti.dto.EstacionamientoResponseDTO;
import com.tuti.entidades.Estacionamiento;
import com.tuti.entidades.Usuario;
import com.tuti.exception.Excepcion;
import com.tuti.presentacion.error.MensajeError;
import com.tuti.servicios.EstacionamientoService;
import com.tuti.servicios.UsuarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/estacionamiento")
//@Api(tags = { SwaggerConfig.USUARIO })
@Tag(name = "Estacionamiento", description = "Estacionamiento")
public class EstacionamientoRestController {

	@Autowired
	private EstacionamientoService service;

	@Autowired
	private UsuarioService serviceU;

	@Operation(summary = "Permite filtrar estacionamientos. ")
	@GetMapping(produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<EstacionamientoResponseDTO> filtrarEstacionamientos(
			@RequestParam(name = "patente", required = false) String patente) throws Excepcion {

		List<Estacionamiento> est = service.getAll();
		List<EstacionamientoResponseDTO> dtos = new ArrayList<EstacionamientoResponseDTO>();
		for (Estacionamiento pojo : est) {
			Usuario u = serviceU.getUsuarioByPatente(pojo.getPatente());
			dtos.add(buildResponse(pojo, u));
		}
		return dtos;
	}

	@GetMapping(value = "/{patente}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<EstacionamientoResponseDTO> getPatenteEstacionamiento(@PathVariable String patente)
			throws Excepcion {

		/*
		 * boolean PasswordValida = service.validarPassword(patente, password);
		 * 
		 * if (!PasswordValida) { return
		 * ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); }
		 */
		Estacionamiento rta = service.getPatente(patente);
		Usuario usuario = serviceU.getUsuarioByPatente(patente);

		if (rta != null) {
			Estacionamiento pojo = rta;
			return new ResponseEntity<EstacionamientoResponseDTO>(buildResponse(pojo, usuario), HttpStatus.OK);
		} else
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	@PostMapping
	public ResponseEntity<Object> guardarEstacionamiento(@Valid @RequestBody EstacionamientoForm form,
			BindingResult result) throws Exception {

		// aca deberiamos traer una variable que obtenga todos los estacionamientos para
		// validar la patente.

//		se va a ingreasar una unica vez el estacionamiento, con estado liberado y dni q no se repita y contraseña

		// Estacionamiento e = service.getPatente(form.getPatente());//ver si esto esta
		// bien

		// Imprimir valores para depuración
		form.toString();
		System.out.println("Patente en form: " + form.toPojo().getPatente());
		System.out.println("Estado en form: " + form.toPojo().getEstado());
		System.out.println("ContraDeUsuario en form: " + form.toPojo().getContraDeUsuario());

		List<Estacionamiento> estacionamientos = service.getAll();
		boolean noExistePatente = false;

		for (Estacionamiento e : estacionamientos) {
			if (e.getPatente() != null && e.getPatente().equals(form.toPojo().getPatente())) {
				noExistePatente = true;
				break;
			}
		}

		if (result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(this.formatearError(result));
		} else if (noExistePatente) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(getError("03", "Dato no insertable", "No se puede insertar una patente duplicada."));
		} else {

			Estacionamiento est = form.toPojo();

			service.insert(est);
			URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{patente}")
					.buildAndExpand(est.getPatente()).toUri();

			return ResponseEntity.created(location).build();
		}
	}

	@PutMapping("/{patente}")
	public ResponseEntity<Object> actualizar1(@RequestBody EstacionamientoForm form, @PathVariable String patente)
			throws Exception {
		// Usuario rta = service.getBydni(dni);

		Estacionamiento e = service.getPatente(patente);
		// e.setEstado(form.toPojo().getEstado());

		System.out.println("Patente en form: " + form.toPojo().getPatente());
		System.out.println("Estado en form: " + form.toPojo().getEstado());
		System.out.println("ContraDeUsuario en form: " + form.toPojo().getContraDeUsuario());

		Estacionamiento ePojo = form.toPojo();

		// service.update(ePojo);

		Usuario u = serviceU.getUsuarioByPatente(patente);

		return ResponseEntity.ok(buildResponse(e, u));
		/*
		 * List<Usuario> usuario = service.getAll(); boolean noExistePatente = false;
		 * 
		 * for (Usuario u : usuario) { if
		 * (u.getPatente().equals(form.toPojo().getPatente()) && u.getDni() !=
		 * form.toPojo().getDni()) { noExistePatente = true; break; } }
		 * 
		 * if (rta == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).
		 * body("No se encuentra el usuario que desea modificar."); else if
		 * (noExistePatente) { return ResponseEntity.status(HttpStatus.BAD_REQUEST)
		 * .body(getError("03", "Dato no editable",
		 * "No se puede modificar una patente.")); } else { Usuario u = form.toPojo();
		 * if (!u.getDni().equals(dni))// El dni es el identificador, con lo cual es el
		 * único dato que no permito // modificar return
		 * ResponseEntity.status(HttpStatus.BAD_REQUEST) .body(getError("03",
		 * "Dato no editable", "No se puede modificar el dni.")); service.update(u);
		 * return ResponseEntity.ok(buildResponse(u)); }
		 */

	}

	private EstacionamientoResponseDTO buildResponse(Estacionamiento pojo, Usuario usuario) throws Excepcion {
		try {

			EstacionamientoResponseDTO dto = new EstacionamientoResponseDTO(pojo);
			Link selfLink = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EstacionamientoRestController.class)
					.getPatenteEstacionamiento(pojo.getPatente())).withRel("Estacionamiento");

			dto.add(selfLink);

			Usuario usu = new Usuario();
			usu = serviceU.getUsuarioByPatente(pojo.getPatente());

			if (usu != null) {
				Link usuarioLink = WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(UsuarioRestController.class).getBydni(usu.getDni()))
						.withRel("Usuario");

				dto.setDni(usuario.getDni());
				dto.add(usuarioLink);
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
