/**
 * 
 */
package com.creativedrive.datatest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.creativedrive.domain.Usuario;
import com.creativedrive.service.impl.UsuarioService;

/**
 * @author Delano Jr
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestEntityManager
public class UsuarioServiceTest {

	@Autowired
	private UsuarioService usuarioService;

	@Test
	public void testsaveNewUsuarioUsuario() {
		Usuario usuario = new Usuario("Jonh Smith", "jonh.smith@email.com", "jonh123", "Endereco ABC", "8851561651",
				"USER");

		Optional<Usuario> usuariosaveNewUsuariod = usuarioService.saveNewUsuario(usuario);
		Assert.assertNotNull(usuariosaveNewUsuariod);
	}

	@Test
	public void testsaveNewUsuarioListUsuarios() {
		Usuario usuario1 = new Usuario("Jonh Smith", "jonh.smith@email.com", "jonh123", "Endereco ABC", "8851561651",
				"USER");
		Usuario usuario2 = new Usuario("Jamal Jones", "jamal.jones@email.com", "jamal123", "Endereco DEF", "8746545461",
				"ADMIN");
		Usuario usuario3 = new Usuario("James Sullivan", "james.sullivan@email.com", "james123", "Endereco GHI",
				"9481284941", "USER");

		List<Usuario> usuarios = Arrays.asList(usuario1, usuario2, usuario3);
		Optional<List<Usuario>> usuariossaveNewUsuariod = usuarioService.saveListNewUsuario(usuarios);

		Assert.assertEquals(usuarios.stream().count(), usuariossaveNewUsuariod.get().stream().count());
	}

	@Test
	public void testFindUsuarioByExample() {

		String email = "jonh.smith@email.com";

		Usuario usuario = new Usuario();
		usuario.setEmail("jonh.smith@email.com");

		Optional<Usuario> usuarioFinded = usuarioService.findByExample(usuario);

		Assert.assertEquals(usuarioFinded.get().getEmail(), email);

	}

	@Test
	public void testFindUsuarioById() {
		Usuario usuario = new Usuario();
		usuario.setId(new ObjectId().toHexString());
		usuario.setNome("Jimmy Hendrix");
		usuario.setEmail("jimmy.h@email.com");
		usuario.setEndereco("Some Place");
		usuario.setSenha("jim789");
		usuario.setTelefone("3338784646");
		usuario.setPerfil("ADMIN");

		Optional<Usuario> usuariosaved = usuarioService.saveNewUsuario(usuario);
		Optional<Usuario> usuarioFinded = usuarioService.findOne(usuariosaved.get().getId());

		Assert.assertEquals(usuariosaved.get().getEmail(), usuarioFinded.get().getEmail());
	}

	@After
	public void afterTest() {
		usuarioService.deleteAllRecords();
	}

}
