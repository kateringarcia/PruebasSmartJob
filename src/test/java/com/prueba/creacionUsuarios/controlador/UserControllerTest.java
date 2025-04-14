package com.prueba.creacionUsuarios.controlador;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.UUID;

import com.prueba.creacionUsuarios.dto.PhoneRequest;
import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.entidad.User;
import com.prueba.creacionUsuarios.service.UserServiceImpl;
import com.prueba.creacionUsuarios.util.BadRequestException;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserServiceImpl userService;

	@Test
	void testCrearUsuarioDevuelve201() throws Exception {

		when(userService.registerUser(any())).thenReturn(mockResponse());

		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request()))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.email").value("Prueba@correo.com"));
	}

	@Test
	void testEmailYaRegistradoDevuelve400() throws Exception {

		when(userService.registerUser(any())).thenThrow(new BadRequestException("El correo ya se encuenta registrado"));

		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request()))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.mensaje").value("El correo ya se encuenta registrado"));
	}

	@Test
	void testCorreoErroneoDevuelve400() throws Exception {

		when(userService.registerUser(any())).thenThrow(new BadRequestException("Formato de correo inválido"));

		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request()))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.mensaje").value("Formato de correo inválido"));
	}
	
	@Test
	void testContraseñaInvalidaDevuelve400() throws Exception {

		when(userService.registerUser(any())).thenThrow(new BadRequestException("Formato de contraseña inválido"));

		mockMvc.perform(post("/api/users").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request()))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.mensaje").value("Formato de contraseña inválido"));
	}

	public UserRequest request() {
		UserRequest request = new UserRequest();
		request.setName("Prueba");
		request.setEmail("Prueba@correo.com");
		request.setPassword("Password1");
		request.setPhones(List.of(new PhoneRequest("1234567", "1", "57")));
		return request;
	}
	
	public User mockResponse() {
		User response = new User();
		response.setId(UUID.randomUUID());
		response.setEmail("Prueba@correo.com");
		return response;
	}
	
}
