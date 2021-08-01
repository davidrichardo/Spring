package com.codmind.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codmind.orderapi.entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
