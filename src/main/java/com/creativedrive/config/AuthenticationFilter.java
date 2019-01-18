/**
 * 
 */
package com.creativedrive.config;

import java.io.IOException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.creativedrive.domain.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Delano Jr
 *
 */
public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private AuthenticationManager authenticationManager;

	protected AuthenticationFilter(String url, AuthenticationManager authManager) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {

		try {

			Usuario usuario = new Usuario();
			usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha(), new ArrayList<>()));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		ZonedDateTime expirationTime = ZonedDateTime.now(ZoneOffset.UTC).plus(SecurityConstants.EXPIRATION_TIME,
				ChronoUnit.MILLIS);

		String token = Jwts
				.builder()
				.setSubject(new ObjectMapper().writeValueAsString(authResult.getPrincipal()))
				.setExpiration(Date.from(expirationTime.toInstant()))
				.signWith(SignatureAlgorithm.HS256, SecurityConstants.SECRET).compact();

		response.setHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
		response.getWriter().write(token);
	}
}
