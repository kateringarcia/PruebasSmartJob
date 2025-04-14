package com.prueba.creacionUsuarios.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.entidad.User;
import com.prueba.creacionUsuarios.jwt.JwtUtil;
import com.prueba.creacionUsuarios.repositorio.UserRepository;
import com.prueba.creacionUsuarios.util.BadRequestException;

@Service
public class UserServiceImpl implements UserService {

    @Value("${app.password.regex}")
    private String passwordRegex;

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public UserServiceImpl(UserRepository repo, JwtUtil jwtUtil) {
        this.userRepository = repo;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public User registerUser(UserRequest userRequest) {
        if (!userRequest.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            throw new BadRequestException("Formato de correo inválido");
        }

        if (!userRequest.getPassword().matches(passwordRegex)) {
            throw new BadRequestException("Formato de contraseña inválido");
        }

        if (userRepository.findByEmail(userRequest.getEmail()).isPresent()) {
            throw new BadRequestException("El correo ya se encuentra registrado");
        }

        User user = new ModelMapper().map(userRequest, User.class);
        user.setToken(jwtUtil.generarToken(user.getEmail()));

        return userRepository.save(user);
    }
    
    public void setPattern(String pattern) {
        this.passwordRegex = pattern;
    }
}

