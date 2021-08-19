package com.codmind.orderapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.codmind.orderapi.entities.OrderLine;
import com.codmind.orderapi.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor	
public class OrderDTO {
	
	private Long id;
	
	private String regDate;
	
	private Double total;
	
	private List<OrderLineDTO> lines;
	
	private UserDTO user;

}
