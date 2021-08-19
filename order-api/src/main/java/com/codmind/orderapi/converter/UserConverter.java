package com.codmind.orderapi.converter;

import com.codmind.orderapi.dto.SignUpRequestDTO;
import com.codmind.orderapi.dto.UserDTO;
import com.codmind.orderapi.entities.User;

public class UserConverter extends AbstractConverter<User, UserDTO>{

	@Override
	public UserDTO fromEntity(User entity) {
		// TODO Auto-generated method stub
		if(entity == null) return null;
		return UserDTO.builder()
				.id(entity.getId())
				.username(entity.getUsername())
				.build();
	}

	@Override
	public User fromDTO(UserDTO dto) {
		// TODO Auto-generated method stub
		return User.builder()
				.id(dto.getId())
				.username(dto.getUsername())
				.build();
	}
	
	public User singup(SignUpRequestDTO dto) {
		if(dto == null) return null;
		return User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
	}

}
