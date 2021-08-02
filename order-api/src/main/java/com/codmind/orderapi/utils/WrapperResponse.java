package com.codmind.orderapi.utils;

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

}
