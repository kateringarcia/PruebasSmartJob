package com.prueba.creacionUsuarios.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.prueba.creacionUsuarios.dto.PhoneRequest;
import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.entidad.User;
import com.prueba.creacionUsuarios.jwt.JwtUtil;
import com.prueba.creacionUsuarios.repositorio.UserRepository;
import com.prueba.creacionUsuarios.util.BadRequestException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private JwtUtil jwtUtil;

	@InjectMocks
	private UserServiceImpl userService;

	@Test
	void testCrearUsuario_Exitoso() {

		userService.setPattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
		when(jwtUtil.generarToken(any())).thenReturn(
				"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJLYXRoQGZlcm5hbmRlei5jbCIsImlhdCI6MTc0NDYyNDcxMywiZXhwIjoxNzQ0NjI4MzEzfQ.198qMzNF1CdKVPESU2V287gdvouj7vO86bhEk6XnJI4n_g5H7DAtuXwaANw74tdUG4ee_1_ojpOQH-aM34w6FA");
		when(userRepository.save(any())).thenReturn(userMock());
		User response = userService.registerUser(userMockRequest());
		assertNotNull(response);
		assertEquals("Prueba", response.getName());
	}

	// Test para validar el formato de correo electrónico
	@Test
	void testRegisterUserInvalidEmail() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("invalid-email");
		userRequest.setPassword("Valid1Password");

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			userService.registerUser(userRequest);
		});

		assertEquals("Formato de correo inválido", exception.getMessage());
	}

	// Test para validar el formato de la contraseña
	@Test
	void testRegisterUserInvalidPassword() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("valid@correo.com");
		userRequest.setPassword("short");

		userService.setPattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
		
		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			userService.registerUser(userRequest);
		});

		assertEquals("Formato de contraseña inválido", exception.getMessage());
	}

	// Test para verificar si el correo ya está registrado
	@Test
	void testRegisterUserEmailAlreadyExists() {
		UserRequest userRequest = new UserRequest();
		userRequest.setEmail("existing@correo.com");
		userRequest.setPassword("Valid1Password");

		userService.setPattern("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
		
		when(userRepository.findByEmail(userRequest.getEmail())).thenReturn(java.util.Optional.of(new User()));

		BadRequestException exception = assertThrows(BadRequestException.class, () -> {
			userService.registerUser(userRequest);
		});

		assertEquals("El correo ya se encuentra registrado", exception.getMessage());
	}

	private User userMock() {
		User userMock = new User();
		userMock.setId(UUID.randomUUID());
		userMock.setName("Prueba");
		userMock.setEmail("Prueba@correo.com");
		return userMock;
	}

	private UserRequest userMockRequest() {
		UserRequest request = new UserRequest();
		request.setName("Prueba");
		request.setEmail("Prueba@correo.com");
		request.setPassword("Password1");
		request.setPhones(List.of(new PhoneRequest("1234567", "1", "57")));
		return request;
	}

}
