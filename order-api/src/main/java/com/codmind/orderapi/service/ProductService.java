package com.codmind.orderapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.ProductRepository;
import com.codmind.orderapi.validator.ProductValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {
	
	@Autowired
	ProductRepository  productRepository;
	
	
	
	public Product findById(Long id) {
		try {
			Product product = productRepository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el producto"));
			return product;
		} catch (ValidateServiceException  | NoDataFoundException e) {
			log.info(e.getMessage(),e);
			throw e;
		}
		catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}

	}
	
	public List<Product> findAll(Pageable page){
		try {
			List<Product> products = productRepository.findAll(page).toList();
			return products;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}

	}
	
	@Transactional
	public void delete(Long id) {
		try {
			Product product = productRepository.findById(id).orElseThrow(()->new NoDataFoundException("No existe el producto"));
			productRepository.delete(product);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}

	}
	
	@Transactional //EL SERVICIO QUE MODIFICA INFORMACION EN BASE DE DATOS NECESITA SER TRANSACCIONAL PARA QUE EN CASO DE ERROR ME HAGA UN ROLLBACK
	public Product create(Product product) {
		try {
			ProductValidator.save(product);
			Product newProduct = productRepository.save(product);
			return newProduct;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}

	}
	
	@Transactional
	public Product update(Product product) {
		try {
			ProductValidator.save(product);
			Product updateProduct = productRepository.findById(product.getId()).orElseThrow(()->new NoDataFoundException("No existe el producto"));
			updateProduct.setName(product.getName());
			updateProduct.setPrice(product.getPrice());
			productRepository.save(updateProduct);
			return updateProduct;
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}

	}

}
