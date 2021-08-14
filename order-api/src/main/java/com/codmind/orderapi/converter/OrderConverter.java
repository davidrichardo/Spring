package com.codmind.orderapi.converter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.format.annotation.DateTimeFormat;

import com.codmind.orderapi.dto.OrderDTO;
import com.codmind.orderapi.dto.OrderLineDTO;
import com.codmind.orderapi.entities.Order;
import com.codmind.orderapi.entities.OrderLine;

public class OrderConverter extends AbstractConverter<Order, OrderDTO>{

	
	private static final DateTimeFormatter datetimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss");
	private ProductConverter productConverter = new ProductConverter();
	
	
	@Override
	public OrderDTO fromEntity(Order entity) {
		if(entity == null) return null;
		List<OrderLineDTO> lines = fromOrderLineEntity(entity.getLines());
		
		// TODO Auto-generated method stub
		return OrderDTO.builder()
				.id(entity.getId())
				.lines(lines)
				.regDate(entity.getRegDate().format(datetimeFormatter))
				.total(entity.getTotal())
				.build();
	}

	@Override
	public Order fromDTO(OrderDTO dto) {
		
		if(dto==null) return null;
		
		List<OrderLine> lines = fromOrderLineDTO(dto.getLines());
		
		return Order.builder()
				.id(dto.getId())
				.lines(lines)
				.total(dto.getTotal())
				.build();
		// TODO Auto-generated method stub
	}
	
	private List<OrderLineDTO> fromOrderLineEntity(List<OrderLine> lines) {
		if(lines==null) return null;
		return lines.stream().map(line ->{
			return OrderLineDTO.builder()
			.id(line.getId())
			.price(line.getPrice())
			.product(productConverter.fromEntity(line.getProduct()))
			.quantity(line.getQuantity())
			.total(line.getTotal())
			.build();
		})
				.collect(Collectors.toList());
		
	}
	private List<OrderLine> fromOrderLineDTO(List<OrderLineDTO> lines) {
		if(lines == null) return null;
		return lines.stream().map(line->{
			return OrderLine.builder()
					.id(line.getId())
					.price(line.getPrice())
					.product(productConverter.fromDTO(line.getProduct()))
					.quantity(line.getQuantity())
					.total(line.getTotal())
					.build();
		})
				.collect(Collectors.toList());

	}

}
