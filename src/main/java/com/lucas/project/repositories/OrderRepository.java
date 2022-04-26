package com.lucas.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.project.entities.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
