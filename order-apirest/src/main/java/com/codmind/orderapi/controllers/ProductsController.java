package com.codmind.orderapi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsController {

	@GetMapping(value = "/hello")
	public String hello() {
		return "Hola Mundo";
	}
}
