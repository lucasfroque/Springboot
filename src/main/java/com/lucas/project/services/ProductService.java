package com.lucas.project.services;

import com.lucas.project.dto.ProductDto;
import com.lucas.project.entities.Category;
import com.lucas.project.entities.Product;
import com.lucas.project.repositories.CategoryRepository;
import com.lucas.project.repositories.ProductRepository;
import com.lucas.project.services.exceptions.DatabaseException;
import com.lucas.project.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private CategoryRepository categoryRepository;

	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product insert(ProductDto productDto) {
		Product product = new Product();
		product.setName(productDto.getName());
		product.setDescription(productDto.getDescription());
		product.setPrice(productDto.getPrice());
		product.setImgUrl(productDto.getImgUrl());
		product.setCategories(getCategories(productDto));

		productRepository.save(product);
		return product;
	}

	public Product findById(Long id) {
		Optional<Product> obj = productRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public void delete(Long id) {
		try {
			productRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

	public Product update(Long id, ProductDto obj) {
		try {
			Product entity = productRepository.findById(id).get();
			updateData(entity, obj);
			productRepository.save(entity);
			return entity;
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(id);
		}
	}

	private void updateData(Product entity, ProductDto obj) {
		entity.setName(obj.getName());
		entity.setPrice(obj.getPrice());
		entity.setImgUrl(obj.getImgUrl());
		entity.setDescription(obj.getDescription());
		entity.setCategories(getCategories(obj));
	}

	private Set<Category> getCategories(ProductDto productDto) {
		String[] categories = productDto.getCategories().split(", ");
		Set<Category> categoriesList = new HashSet<>();

		for (String categoriesName : categories) {
			Category getCategory = categoryRepository.findByName(categoriesName);
			System.out.println(getCategory);
			categoriesList.add(getCategory);
		}
		return categoriesList;
	}

}
