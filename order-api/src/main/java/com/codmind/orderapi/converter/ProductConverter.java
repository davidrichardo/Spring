package com.codmind.orderapi.converter;

import com.codmind.orderapi.dto.ProductDTO;
import com.codmind.orderapi.entities.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO>{

	@Override
	public ProductDTO fromEntity(Product entity) {
		return ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
	}

	@Override
	public Product fromDTO(ProductDTO dto) {
		return Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.price(dto.getPrice())
				.build();
	}
	
}
