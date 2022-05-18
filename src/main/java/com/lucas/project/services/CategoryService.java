package com.lucas.project.services;

import com.lucas.project.dto.CategoryDto;
import com.lucas.project.entities.Category;
import com.lucas.project.repositories.CategoryRepository;
import com.lucas.project.services.exceptions.DatabaseException;
import com.lucas.project.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
	
	@Autowired
	private CategoryRepository repository;
	
	public List<Category> findAll(){
		return repository.findAll();
	}
	
	public Category findById(Long id) {
		Optional<Category> obj = repository.findById(id);
		return obj.get();
	}

	public Category insert(CategoryDto categoryDto){
		Category category = new Category();
		category.setName(categoryDto.getName());
		repository.save(category);

		return category;
	}

	public void delete(Long id){
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		}catch(DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	public Category update(Long id, CategoryDto categoryDto){
		try {
			Category entity = findById(id);
			updateData(entity, categoryDto);
			repository.save(entity);
			return entity;
		}catch (EntityNotFoundException e){
			throw new ResourceNotFoundException(id);
		}
	}

	public void updateData(Category entity, CategoryDto categoryDto){
		entity.setName(categoryDto.getName());
	}

}
