package com.lucas.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lucas.project.entities.User;

public interface UserRepository extends JpaRepository<User, Long>{

	
	
}
