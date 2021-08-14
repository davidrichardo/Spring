package com.codmind.orderapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codmind.orderapi.entities.OrderLine;

public interface OrderLineRepository extends JpaRepository<OrderLine, Long> {

}
