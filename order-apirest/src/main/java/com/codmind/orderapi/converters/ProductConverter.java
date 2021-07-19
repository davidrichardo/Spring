package com.codmind.orderapi.converters;

import com.codmind.orderapi.dtos.ProductDTO;
import com.codmind.orderapi.entities.Product;

public class ProductConverter extends AbstractConverter<Product, ProductDTO>{

	@Override
	public ProductDTO fromEntity(Product entity) {
		// TODO Auto-generated method stub
		ProductDTO productDTO = ProductDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.price(entity.getPrice())
				.build();
		return productDTO;
	}

	@Override
	public Product fromDTO(ProductDTO dto) {
		// TODO Auto-generated method stub
		Product product = Product.builder()
				.id(dto.getId())
				.name(dto.getName())
				.price(dto.getPrice())
				.build();
		return product;
		
	}
	
	

	
}
