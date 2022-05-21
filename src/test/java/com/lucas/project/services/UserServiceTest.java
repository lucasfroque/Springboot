package com.lucas.project.services;

import com.lucas.project.dto.UserDto;
import com.lucas.project.entities.User;
import com.lucas.project.repositories.UserRepository;
import com.lucas.project.services.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
public class UserServiceTest {

	private static final long id = 1L;
	private static final String name = "James White";
	private static final String email = "james@gmail.com";
	private static final String phone = "977777777";
	private static final String password = "123456";

	@Mock
	private UserRepository repository;

	private UserDto userDto;

	@InjectMocks
	private UserService service;

	@BeforeEach
	public void setUp(){
		MockitoAnnotations.openMocks(this);
		startUser();

	}

	@Test
	public void whenFindAllShouldReturnListOfUser(){
		List<User> response = service.findAll();
		Assertions.assertNotNull(response);
		Assertions.assertEquals(1, response.size());
	}


	@Test
	public void whenFindByIdShouldReturnUser() {

		User user = new User(id, name, email, phone, password);

		doReturn(Optional.of(user)).when(repository).findById(id);

		User response = service.findById(id);

		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(id, response.getId());
		Assertions.assertEquals(name, response.getName());
		Assertions.assertEquals(email, response.getEmail());
		Assertions.assertEquals(phone, response.getPhone());
		Assertions.assertEquals(password, response.getPassword());
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
	public void whenInsertShouldSaveUser() {
		UserDto user = new UserDto(name, email, phone, password);

		User response = service.insert(user);

		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertEquals(user.getName(), response.getName());
	}

	@Test
	public void whenDeleteShouldDeleteUser() {
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
	public void whenUpdateShouldUpdateUser(){
		User oldUser = new User(1L, "Old user", "olduser@gmail.com", "2222222", "2222223455");
		UserDto newUser = new UserDto("New user", "newuser@gmail.com", "212123123", "1283823");

		repository.save(oldUser);
		User response = service.update(1L, newUser);

		Assertions.assertEquals(User.class, response.getClass());
		Assertions.assertNotEquals(oldUser.getName(), response.getName());

	}

	@Test
	public void whenUpdateShouldReturnResourceNotFoundException(){
		UserDto user = new UserDto(name, email, phone, password);

		try{
			service.update(id, user);
		}catch (Exception ex){
			Assertions.assertEquals(ResourceNotFoundException.class, ex.getClass());
			Assertions.assertEquals("Resource not found. Id " + id, ex.getMessage());
		}
	}

	public void startUser(){
		when(repository.findAll()).thenReturn(List.of(new User()));
		when(repository.findById(anyLong())).thenReturn(Optional.of(new User()));
		when(repository.save(any(User.class))).thenReturn(new User());
	}
}
