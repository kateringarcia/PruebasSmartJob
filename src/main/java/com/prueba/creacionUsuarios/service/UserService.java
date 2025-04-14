package com.prueba.creacionUsuarios.service;

import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.entidad.User;

public interface UserService {
	
	public User registerUser(UserRequest userRequest);

}
