/**
 * 
 */
package com.creativedrive.config;

import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.creativedrive.service.impl.UsuarioService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * @author Delano Jr
 *
 */
public class AuthorizationFilter extends BasicAuthenticationFilter {

	private final UsuarioService usuarioService;

	public AuthorizationFilter(AuthenticationManager authenticationManager, UsuarioService userService) {
		super(authenticationManager);
		this.usuarioService = userService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		UsernamePasswordAuthenticationToken authentication = getAuthenticationToken(request);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request)
			throws ExpiredJwtException, JsonParseException, JsonMappingException, UnsupportedJwtException,
			MalformedJwtException, SignatureException, IllegalArgumentException, IOException {

		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if (token == null)
			return null;

		Object user = new ObjectMapper().readValue(
				Jwts.parser().setSigningKey(SecurityConstants.SECRET)
						.parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, "")).getBody().getSubject(),
				Object.class);
		
		return user != null ? new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList()) : null;
	}
}
