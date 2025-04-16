package com.prueba.creacionUsuarios.controlador;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.entidad.User;
import com.prueba.creacionUsuarios.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Crea un nuevo usuario", 
               responses = {
                   @ApiResponse(responseCode = "201", description = "Usuario creado correctamente"),
                   @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
                   @ApiResponse(responseCode = "409", description = "Correo ya registrado")
               })
    public ResponseEntity<User> register(@RequestBody UserRequest request) {
    	 User usuario = userService.registerUser(request);
         return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }
}
