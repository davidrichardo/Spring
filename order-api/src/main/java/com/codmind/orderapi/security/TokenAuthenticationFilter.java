package com.codmind.orderapi.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codmind.orderapi.entities.User;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.repository.UserRepository;
import com.codmind.orderapi.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter{

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			// TODO Auto-generated method stub
			String jwt = getJwtFromRequest(request);
			log.info("TOKEN ->"+jwt);
			if(StringUtils.hasText(jwt) && userService.validateToken(jwt)) {
				String username = userService.getUsernameFromToken(jwt);
				log.info(username);
				User user = userRepository.findByUsername(username)
						.orElseThrow(() -> new NoDataFoundException("No existe el usuario") );
				
				UserPrincipal principal = UserPrincipal.create(user);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principal,null,principal.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Error al autenticar el Usuario",e);
			// TODO: handle exception
		}
		filterChain.doFilter(request, response);

		
	}
	
	public String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if(StringUtils.hasText(bearerToken ) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7,bearerToken.length());
		}
		return null;
	}

}
