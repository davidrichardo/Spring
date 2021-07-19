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

import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.repositories.ProductRepository;
import com.codmind.orderapi.services.ProductService;

@RestController
public class ProductsController {

	
	@Autowired 
	ProductService prdService;
	
	@GetMapping(value = "/hello")
	public String hello() {
		return "Hola Mundo";
	}
	
	@GetMapping(value = "products")
	public ResponseEntity<List<Product>>  findAll(){
		List<Product> products = prdService.findAll();
		return new ResponseEntity<List<Product>>(products,HttpStatus.OK);
	}
	
	@GetMapping(value = "/products/{id}")
	public ResponseEntity<Product> findById(@PathVariable("id") Long id){
		Product product = prdService.findById(id);		
		return  new ResponseEntity<Product>(product, HttpStatus.OK);
	}
	
	@PostMapping(value = "/products")
	public ResponseEntity<Product> create(@RequestBody Product product) {
		Product newProduct = prdService.create(product);
		return new ResponseEntity<Product>(newProduct,HttpStatus.OK);
	}
	
	@PutMapping(value = "/products")
	public ResponseEntity<Product> update(@RequestBody Product product){
		Product updateProd = prdService.findById(product.getId());
		return new ResponseEntity<Product>(updateProd,HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/products/{id}")
	public ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
		Product product = prdService.findById(id);
		return new ResponseEntity(HttpStatus.OK);
	}
}
