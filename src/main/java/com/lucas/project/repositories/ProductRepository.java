package com.lucas.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.project.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
