package com.codmind.orderapi.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.converters.ProductConverter;
import com.codmind.orderapi.dtos.ProductDTO;
import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.services.ProductService;

@RestController
public class ProductsController {

	
	@Autowired 
	ProductService prdService;
	
	private ProductConverter prdConverter = new ProductConverter();
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hola Mundo";
	}
	
	@GetMapping(value = "products")
	public ResponseEntity<List<ProductDTO>>  findAll(){
		
		List<Product> products = prdService.findAll();
		List<ProductDTO> productsDTO = prdConverter.fromEntity(products);
		return new ResponseEntity<List<ProductDTO>>(productsDTO,HttpStatus.OK);
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<ProductDTO> findById(@PathVariable("id") Long id){
		Product product = prdService.findById(id);		
		ProductDTO productDTO = prdConverter.fromEntity(product);
		return  new ResponseEntity<ProductDTO>(productDTO, HttpStatus.OK);
	}
	
	@PostMapping(value = "/products")
	public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO product) {
		
		Product newProduct = prdService.create(prdConverter.fromDTO(product));
		ProductDTO productDTO = prdConverter.fromEntity(newProduct);
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
	}
	
	@PutMapping(value = "/products")
	public ResponseEntity<ProductDTO> update(@RequestBody ProductDTO product){

		
		Product updatablePrd =  prdService.update(prdConverter.fromDTO(product));
		ProductDTO productDTO = prdConverter.fromEntity(updatablePrd);
		return new ResponseEntity<ProductDTO>(productDTO,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
		
		prdService.deleteById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
