package com.codmind.orderapi.dto;

import com.codmind.orderapi.entities.Order;
import com.codmind.orderapi.entities.Product;

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
public class OrderLineDTO {

	private Long id;
	private ProductDTO product;
	private Double price;
	private Double quantity;
	private Double total;
	
}
