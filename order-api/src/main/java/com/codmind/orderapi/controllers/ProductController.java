package com.codmind.orderapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.converter.ProductConverter;
import com.codmind.orderapi.dto.ProductDTO;
import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	private ProductConverter productConverter = new ProductConverter();

//	List<Product> products = new ArrayList<>();
	
	
//	public ProductController() {
//		
//		for(int i=0;i<10;i++) {
//		products.add(Product.builder().
//				id(i+1L).
//				name("Product "+ (i+1))
//				.build());
//		}
//	}
	
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<ProductDTO>>findAll(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
			@RequestParam (value ="pageSize",defaultValue = "5",required = false ) int pageSize
			) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		List<Product> products= productService.findAll(page);
		List<ProductDTO> productsDTO = productConverter.fromEntity(products);
		return new ResponseEntity<List<ProductDTO>>(productsDTO,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id) {
		//Desde el servicio la idea es convertir a DTO y posteriormente a Entities
		
		Product product = productService.findById(id);
		ProductDTO productDTO =  productConverter.fromEntity(product);
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
	}
	
	@PutMapping(value = "/products")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO productUpdate) {
		Product updateProduct = productService.update(productConverter.fromDTO(productUpdate));//Convertimos el DTO A ENTITY PARA QUE TRABAJE CON EL SERVICIO
		ProductDTO productDto = productConverter.fromEntity(updateProduct);//CONVERTIMOS A DTO LA RESPUESTA DE LA ENTIDAD PARA DAR LA RESPUESTA
		return new ResponseEntity<ProductDTO>(productDto,HttpStatus.OK);
	}
//	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO>  create(@RequestBody ProductDTO product) {
		
		Product newProduct = productService.create(productConverter.fromDTO(product));//Convertimos el DTO A ENTITY PARA QUE TRABAJE CON EL SERVICIO
		ProductDTO productDto = productConverter.fromEntity(newProduct);//CONVERTIMOS A DTO LA RESPUESTA DE LA ENTIDAD PARA DAR LA RESPUESTA
		return new ResponseEntity<ProductDTO>(productDto,HttpStatus.CREATED);
	}
//	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long productDelete) {
		productService.delete(productDelete);
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
}
