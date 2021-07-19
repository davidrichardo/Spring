package com.codmind.orderapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.repositories.ProductRepository;

@Service
public class ProductService {

	@Autowired
	ProductRepository prdRepository;
	
	public Product findById(Long id) {
		Product product = prdRepository.findById(id).orElseThrow(() -> new RuntimeException("No Existe el Producto"));
		return product;
	}
	
	public List<Product> findAll(){
		List<Product> products = prdRepository.findAll();
		return products;
	}
	
	public Product create(Product product) {
		Product newProduct = prdRepository.save(product);
		return newProduct;
	}
	
	public Product update (Product product) {
		Product updateProd = prdRepository.findById(product.getId()).orElseThrow(()-> new RuntimeException("El producto ṕor ese ID no existe"));

		updateProd.setName(product.getName());
		updateProd.setPrice(product.getPrice());
		prdRepository.save(updateProd);
		return updateProd;
	}
	
	public void deleteById(Long id) {
		Product product = prdRepository.findById(id).orElseThrow(()-> new RuntimeException("El ID no se encuentra en Base de datos"));		
		prdRepository.delete(product);
	}
}
