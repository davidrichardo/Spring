package com.codmind.orderapi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codmind.orderapi.entities.Product;

@RestController
public class ProductController {

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
	public List<Product> findAll() {
		return this.products;
	}
	
	@GetMapping(value = "/products/{id}")
	public Product findById(@PathVariable("id") Long id) {
		for(Product product:this.products) {
			if(product.getId().longValue()==id.longValue()) {
				return product;
			}
			
		}
		return null;
	}
	
	@PutMapping(value = "/products")
	public Product update(@RequestBody Product productUpdate) {
		for(Product product:this.products) {
			if(product.getId().longValue()==productUpdate.getId().longValue()) {
				product.setName(productUpdate.getName());
				return product;
			}
		}throw new RuntimeException("NO existe el producto");
	}
//	
	@PostMapping(value = "/products")
	public Product create(@RequestBody Product product) {
		this.products.add(product);
		return product;
	}
//	
	@DeleteMapping(value = "/products/{id}")
	public void delete(@PathVariable("id") Long productDelete) {
		Product deleteProduct = null;
		for(Product product:this.products) {
			if(product.getId().longValue() == productDelete.longValue()) {
				deleteProduct = product;
				break;
			}
		}
		
		if(deleteProduct == null) throw new RuntimeException("NO existe el producto");
		
		this.products.remove(deleteProduct);
		
		
	}
	
}
