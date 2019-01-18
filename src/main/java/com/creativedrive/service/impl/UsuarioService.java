/**
 * 
 */
package com.creativedrive.service.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.creativedrive.domain.Usuario;

/**
 * @author Delano Jr
 *
 */
@Service
public class UsuarioService extends BaseService<Usuario, String> implements UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UsuarioService(MongoRepository<Usuario, String> repository) {
		super(repository);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {

			Optional<Usuario> usuario = this.findByExample(new Usuario(email));
			User user = new User(usuario.get().getNome(), bCryptPasswordEncoder.encode(usuario.get().getSenha()),
					Arrays.asList(new SimpleGrantedAuthority(usuario.get().getPerfil())));

			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
