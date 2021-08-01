package com.codmind.orderapi.controllers;

import java.util.logging.Logger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.codmind.orderapi.entities.Product;
import lombok.extern.slf4j.Slf4j;



@Slf4j //Lombok para llamadas de Logger
@RestController
public class HelloWorldController {
	

	
	@GetMapping(value = "hello")
	public String hello() {
		log.info("Metodo de Hello World");
		return "Hello World 1";
	}
	

}
