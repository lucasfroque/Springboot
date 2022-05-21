package com.lucas.project.services;

import com.lucas.project.dto.CategoryDto;
import com.lucas.project.entities.Category;
import com.lucas.project.repositories.CategoryRepository;
import com.lucas.project.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    private static final Long id = 1L;
    private static final String name = "Food";

    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCategory();
    }

    @Test
    void whenFindAllShouldReturnListOfCategory() {
      List<Category> response = service.findAll();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(1, response.size());
    }

    @Test
    public void whenFindByIdShouldReturnResourceNotFoundException(){
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(id));
        try{
            service.findById(id);
        }catch (Exception ex){
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Resource not found. Id " + id, ex.getMessage());
        }
    }

    @Test
    void whenFindByIdShouldReturnCategory() {

        Category category = new Category(id, name);
        when(repository.findById(anyLong())).thenReturn(Optional.of(category));

        Category response = service.findById(id);
        Assertions.assertEquals(response.getClass(), Category.class);
        Assertions.assertEquals(response.getName(), category.getName());

    }



    @Test
    void whenInsertShouldSaveCategory() {
        CategoryDto category = new CategoryDto(name);

        Category response = service.insert(category);

        Assertions.assertEquals(response.getClass(), Category.class);
        Assertions.assertEquals(response.getName(), category.getName());
    }
    @Test
    void whenInsertShouldReturnDataIntegrityViolationException(){
            when(repository.save(new Category())).thenThrow(new DataIntegrityViolationException("Category has already been created."));
            try {
                service.insert(new CategoryDto());
            }catch (Exception ex){
                Assertions.assertEquals(DataIntegrityViolationException.class, ex.getClass());
                Assertions.assertEquals("Category has already been created.", ex.getMessage());
            }

    }


    @Test
    void whenDeleteShouldDeleteCategory() {
        service.delete(id);
        verify(repository, times(1)).deleteById(anyLong());
    }

    @Test
    public void whenDeleteShouldReturnResourceNotFoundException(){
        when(repository.findById(anyLong())).thenThrow(new ResourceNotFoundException(id));
        try{
            service.delete(id);
        }catch (Exception ex){
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Resource not found. Id " + id, ex.getMessage());
        }
    }

    @Test
    void whenUpdateShouldUpdateCategory() {
        Category oldCategory = new Category(1L,"Food");
        CategoryDto newCategory = new CategoryDto("Drink");

        repository.save(oldCategory);

        Category response = service.update(1L, newCategory);
        Assertions.assertEquals(response.getClass(), Category.class);
        Assertions.assertNotEquals(response.getName(), oldCategory.getName());

    }
    @Test
    public void whenUpdateShouldReturnResourceNotFoundException(){
        try{
            service.update(id, new CategoryDto());
        }catch (Exception ex){
            Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
            Assertions.assertEquals("Resource not found. Id " + id, ex.getMessage());
        }
    }

    public void startCategory(){
        when(repository.findAll()).thenReturn(List.of(new Category()));
        when(repository.findById(anyLong())).thenReturn(Optional.of(new Category()));
        when(repository.save(any(Category.class))).thenReturn(new Category());

    }
}