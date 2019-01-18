package com.creativedrive.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.creativedrive.service.impl.UsuarioService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UsuarioService usuarioService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf()
				.disable()
				.authorizeRequests()
				.antMatchers(HttpMethod.POST, SecurityConstants.AUTH_URL).permitAll()
		/*		.antMatchers(HttpMethod.POST, "/usuario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/usuario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/usuario/**").hasRole("ADMIN")*/
				.anyRequest()
				.authenticated()
				.and()
				.addFilterBefore(new AuthenticationFilter(SecurityConstants.AUTH_URL ,authenticationManager()), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(new AuthorizationFilter(authenticationManager(), usuarioService), UsernamePasswordAuthenticationFilter.class);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(usuarioService).passwordEncoder(bCryptPasswordEncoder());
	}

}
