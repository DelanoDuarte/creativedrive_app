/**
 * 
 */
package com.creativedrive.test.resource;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.creativedrive.config.SecurityConstants;
import com.creativedrive.domain.Usuario;
import com.creativedrive.service.impl.UsuarioService;

/**
 * @author Delano Jr
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UsuarioResourceTest {

	private final String AUTH_URL = "http://localhost:8080/creativedrive_users_app/api/auth";
	private final String APP_URL = "http://localhost:8080/creativedrive_users_app/api";

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UsuarioService usuarioService;

	private HttpHeaders headers;

	@Before
	public void setUp() {

		usuarioService.saveNewUsuario(
				new Usuario("Jonh Jones", "jonh.jones@email.com", "jonh12345", "Endereco ABC", "8851561651", "ADMIN"));

		headers = new HttpHeaders();
		ResponseEntity<String> token = restTemplate.postForEntity(AUTH_URL,
				new Usuario("jonh.jones@email.com", "jonh12345"), String.class);

		headers.add(SecurityConstants.HEADER_STRING, "Bearer " + token.getBody());
	}

	@Test
	public void testSaveNewUsuario() {
		String emailSaved = "jamal.jones@email.com";
		HttpEntity<Usuario> usuarioEntity = new HttpEntity<Usuario>(
				new Usuario("Jamal Jones", "jamal.jones@email.com", "jamal123", "Endereco DEF", "8746545461", "ADMIN"),
				headers);

		ResponseEntity<Usuario> usuario = restTemplate.postForEntity(APP_URL + "/usuario", usuarioEntity,
				Usuario.class);

		Assert.assertEquals(emailSaved, usuario.getBody().getEmail());

	}

	@Test
	public void testFindUsuarioById() {
		String emailFinded = "jamal.jones@email.com";

		HttpEntity<Usuario> usuarioEntity = new HttpEntity<Usuario>(
				new Usuario("Jamal Jones", "jamal.jones@email.com", "jamal123", "Endereco DEF", "8746545461", "ADMIN"),
				headers);

		ResponseEntity<Usuario> newUsuario = restTemplate.postForEntity(APP_URL + "/usuario", usuarioEntity,
				Usuario.class);

		ResponseEntity<Usuario> usuario = restTemplate
				.getForEntity(APP_URL + "/usuario/" + newUsuario.getBody().getId(), null, Usuario.class);
		Assert.assertEquals(emailFinded, usuario.getBody().getEmail());
	}

	@Test
	public void testFindUsuariosByExample() {
		String email = "jonh.jones@email.com";
		HttpEntity<Usuario> usuarioEntity = new HttpEntity<Usuario>(new Usuario("jonh.jones@email.com"), headers);

		ResponseEntity<Usuario> usuario = restTemplate.postForEntity(APP_URL + "/usuario/find", usuarioEntity,
				Usuario.class);
		Assert.assertEquals(email, usuario.getBody().getEmail());
	}

	@After
	public void afterSetUp() {
		usuarioService.deleteAllRecords();
	}
}
