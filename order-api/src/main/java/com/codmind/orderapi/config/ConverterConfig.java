package com.codmind.orderapi.config;

import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.codmind.orderapi.converter.OrderConverter;
import com.codmind.orderapi.converter.ProductConverter;
import com.codmind.orderapi.converter.UserConverter;

@Configuration
public class ConverterConfig {
	
	@Value("${config.dateTimeFormatter}")
	private String dateTimeFormat;

	@Bean
	public ProductConverter getProductConverter() {
		return new ProductConverter(); 
	}
	
	
	@Bean
	public OrderConverter getOrderConverter() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
		return new OrderConverter(formatter,getProductConverter(),getUserConverter());
		
	}
	
	@Bean
	public UserConverter getUserConverter() {
		return new UserConverter();
	}
}
