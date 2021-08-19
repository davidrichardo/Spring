package com.codmind.orderapi.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codmind.orderapi.entities.Order;
import com.codmind.orderapi.entities.OrderLine;
import com.codmind.orderapi.entities.Product;
import com.codmind.orderapi.entities.User;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.OrderLineRepository;
import com.codmind.orderapi.repository.OrderRepository;
import com.codmind.orderapi.repository.ProductRepository;
import com.codmind.orderapi.security.UserPrincipal;
import com.codmind.orderapi.validator.OrderValidator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderService {
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	OrderLineRepository orderLineRepository;
	
	@Autowired
	ProductRepository productRepository;

	public List<Order> findAll(Pageable page){
		try {
			return orderRepository.findAll(page).toList();
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
	
	public 	Order findbyId(Long id) {
		try {
			return orderRepository.findById(id).orElseThrow(()-> new NoDataFoundException("La order no existe"));
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
	
	public void delete(Long id) {
		try {
			Order order =  orderRepository.findById(id).orElseThrow(()->new NoDataFoundException("La orden no existe"));
			orderRepository.delete(order);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
	
	@Transactional
	public Order save(Order order) {
		try {
			OrderValidator.save(order);
			UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userPrincipal.getUser();
			
			
			double total =0;
			
			for(OrderLine line : order.getLines()) {
				Product product = productRepository.findById(line.getProduct().getId())
					.orElseThrow(() -> new NoDataFoundException("No existe el producto " + line.getProduct().getId()));
				line.setPrice(product.getPrice());
				line.setTotal(product.getPrice()* line.getQuantity());
				total += line.getTotal();
				}
			
			order.setTotal(total);
			order.getLines().forEach(line -> line.setOrder(order));
			if(order.getId()== null) {
				order.setUser(user);
				order.setRegDate(LocalDateTime.now());
				return orderRepository.save(order);
			}
			
			Order savedOrder = orderRepository.findById(order.getId())
					.orElseThrow(()->new NoDataFoundException("La orden no existe"));
			order.setRegDate(savedOrder.getRegDate());
			
			List<OrderLine> deletedLines = savedOrder.getLines();
			deletedLines.removeAll(order.getLines());
			orderLineRepository.deleteAll(deletedLines);
			
			return orderRepository.save(order);
		} catch (ValidateServiceException | NoDataFoundException e) {
			log.info(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new GeneralServiceException();
		}
	}
}
