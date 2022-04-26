package com.lucas.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.project.entities.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

	
	
}
