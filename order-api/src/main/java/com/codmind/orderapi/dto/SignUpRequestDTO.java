package com.codmind.orderapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class SignUpRequestDTO {
	private String username;
	private String password;
}
