package com.codmind.orderapi.entities;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "order_lines")
public class OrderLine {
	
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	


	@ManyToOne
	@JoinColumn(name = "FK_PRODUCT",nullable = false)
	private Product product;
	
	@Column(name = "PRICE",nullable = false)
	private Double price;
	
	@Column(name = "QUANTITY",nullable = false)
	private Double quantity;
	
	@Column(name = "TOTAL",nullable = false)
	private Double total;
	
	@ManyToOne
	@JoinColumn(name = "FK_ORDER",nullable = false)
	private Order order;

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderLine other = (OrderLine) obj;
		return Objects.equals(id, other.id);
	}
	




}
