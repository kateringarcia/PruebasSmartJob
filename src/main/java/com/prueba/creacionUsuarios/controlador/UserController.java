package com.prueba.creacionUsuarios.controlador;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prueba.creacionUsuarios.dto.UserRequest;
import com.prueba.creacionUsuarios.service.UserService;
import com.prueba.creacionUsuarios.util.BadRequestException;
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
    public ResponseEntity<?> register(@RequestBody UserRequest request) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(request));
        } catch (BadRequestException e) {
            return ResponseEntity.badRequest().body(Map.of("mensaje", e.getMessage()));
        }
    }
}
