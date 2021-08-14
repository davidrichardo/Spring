package com.codmind.orderapi.utils;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.codmind.orderapi.dto.OrderDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WrapperResponse<T> {
	boolean ok;
	private String message;
	private T body;

	public ResponseEntity<WrapperResponse<T>> createResponse() {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(this,HttpStatus.OK);
	}
	
	public ResponseEntity<WrapperResponse<T>> createResponse(HttpStatus status) {
		// TODO Auto-generated method stub
		return new ResponseEntity<>(this,status);
	}

}
