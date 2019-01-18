/**
 * 
 */
package com.creativedrive.resources;

import java.io.Serializable;
import java.util.Optional;

import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.creativedrive.domain.Usuario;
import com.creativedrive.service.impl.UsuarioService;

/**
 * @author Delano Jr
 *
 */

@RestController
@RequestMapping("/usuario")
@CrossOrigin("*")
public class UsuarioResource implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private UsuarioService usuarioService;

	@GetMapping(produces = MediaType.APPLICATION_JSON)
	private ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(usuarioService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/find")
	private ResponseEntity<?> findAllPagineted(@RequestParam("page") Integer page, @RequestParam("size") Integer size) {
		try {
			return ResponseEntity.ok(usuarioService.findAllPagineted(page, size));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping("/find")
	private ResponseEntity<?> findByUsuarioExample(@RequestBody Usuario usuario) {
		try {

			Optional<Usuario> usuarioFinded = usuarioService.findByExample(usuario);
			if (usuarioFinded.isPresent())
				return ResponseEntity.ok(usuarioFinded);

			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@PostMapping(consumes = MediaType.APPLICATION_JSON)
	private ResponseEntity<?> save(@RequestBody Usuario usuario) {
		try {
			return ResponseEntity.ok(usuarioService.save(usuario));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	@GetMapping("/{id}")
	private ResponseEntity<?> findOne(@PathVariable("id") String id) {
		try {
			return ResponseEntity.ok(usuarioService.findOne(id));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
