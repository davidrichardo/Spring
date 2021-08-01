package com.codmind.orderapi.controllers;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;

	List<Product> products = new ArrayList<>();
	
	
	public ProductController() {
		
		for(int i=0;i<10;i++) {
		products.add(Product.builder().
				id(i+1L).
				name("Product "+ (i+1))
				.build());
		}
	}
	
	
	@GetMapping(value = "/products")
	public ResponseEntity<List<Product>>findAll() {
		List<Product> products= productRepository.findAll();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id) {
		Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe el producto por ese ID"));
			
		
		return new ResponseEntity<Product>(product,HttpStatus.OK);
	}
	
	@PutMapping(value = "/products")
	public ResponseEntity<Product> update(@RequestBody Product productUpdate) {
		Product existProduct = productRepository.findById(productUpdate.getId()).orElseThrow(()-> new RuntimeException("No existe el producto"));
		existProduct.setName(productUpdate.getName());
		existProduct.setPrice(productUpdate.getPrice());
		productRepository.save(existProduct);
		return new ResponseEntity<Product>(existProduct,HttpStatus.OK);
	}
//	
	@PostMapping(value = "/products")
	public ResponseEntity<Product>  create(@RequestBody Product product) {
		Product newProduct = productRepository.save(product);	
		return new ResponseEntity<Product>(newProduct,HttpStatus.CREATED);
	}
//	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") Long productDelete) {
		Product product = productRepository.findById(productDelete).orElseThrow(()->new RuntimeException("No existe el producto"));
		productRepository.delete(product);
		
		return new ResponseEntity<Void>(HttpStatus.OK);
		
	}
	
}
