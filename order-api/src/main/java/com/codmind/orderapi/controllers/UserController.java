package com.codmind.orderapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.converter.UserConverter;
import com.codmind.orderapi.dto.LoginRequestDTO;
import com.codmind.orderapi.dto.LoginResponseDTO;
import com.codmind.orderapi.dto.SignUpRequestDTO;
import com.codmind.orderapi.dto.UserDTO;
import com.codmind.orderapi.entities.User;
import com.codmind.orderapi.service.UserService;
import com.codmind.orderapi.utils.WrapperResponse;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserConverter userConverter;
	
	@PostMapping(value = "/signup")
	public ResponseEntity<WrapperResponse<UserDTO>> singup(@RequestBody SignUpRequestDTO request){
		
		User user = userService.createUser(userConverter.singup(request));
		
		return new WrapperResponse<>(true,"success",userConverter.fromEntity(user)).createResponse();
	}
	
	@PostMapping(value = "/login")
	public ResponseEntity<WrapperResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request){
		LoginResponseDTO response =  userService.login(request);
		return new WrapperResponse<>(true,"success",response).createResponse();
	}
}
