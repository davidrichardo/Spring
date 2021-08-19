package com.codmind.orderapi.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.codmind.orderapi.converter.UserConverter;
import com.codmind.orderapi.dto.LoginRequestDTO;
import com.codmind.orderapi.dto.LoginResponseDTO;
import com.codmind.orderapi.entities.User;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.UserRepository;
import com.codmind.orderapi.validator.UserValidator;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserConverter userConverter;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Value("${jwt.password}")
	private String jwtSecret;
	
	public User createUser(User user) {
		try {
			
			UserValidator.signup(user);
			
			User existUser = userRepository.findByUsername(user.getUsername())
					.orElse(null);
			if(existUser !=null) throw new ValidateServiceException("El nombre del usuario ya existe");
			
			String encoder = passwordEncoder.encode(user.getPassword());
			user.setPassword(encoder);
			
			return userRepository.save(user);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
	
	public LoginResponseDTO login(LoginRequestDTO request) {
		try {
			User user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> new ValidateServiceException("Usuario o Password es incorrecto"));
			if(!passwordEncoder.matches(request.getPassword(), user.getPassword())) throw new ValidateServiceException("Usuario o Password es incorrecto");

			
			String token = createToken(user);
			
			return new LoginResponseDTO(userConverter.fromEntity(user),token);
			
			
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
	
	public String createToken(User user) {
		Date now = new Date();
		Date expiryDate = new Date(now.getTime()+(1000*60*60));
		return Jwts.builder().setSubject(user.getUsername())
				.setIssuedAt(now)
				.setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public Boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return true;
		} catch (UnsupportedJwtException e) {
			// TODO: handle exception
			log.error("Exception thrown when receiving a JWT in a particular format/configuration that does not match the format expected\n"
					+ " * by the application.");
			
		}
		catch (MalformedJwtException e) {
			// TODO: handle exception
			log.error("Exception indicating that a JWT was not correctly constructed and should be rejected.");
			
		}
		catch (SignatureException e) {
			// TODO: handle exception
			log.error("Exception indicating that either calculating a signature or verifying an existing signature of a JWT failed.");
			
		}
		catch (ExpiredJwtException e) {
			// TODO: handle exception
			log.error("Exception indicating that a JWT was accepted after it expired and must be rejected.");
			
		}
		catch (IllegalArgumentException e) {
			// TODO: handle exception
			log.error("Thrown to indicate that a method has been passed an illegal or\n"
					+ " * inappropriate argument.");
			
		}
		return false;

	}
	
	public String getUsernameFromToken(String jwt) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(),e);
			throw new ValidateServiceException("Invalid Token");
		}
		
	}
}
