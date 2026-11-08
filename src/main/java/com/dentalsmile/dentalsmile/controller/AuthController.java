package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.LoginRequest;
import com.dentalsmile.dentalsmile.dto.request.RegisterRequest;
import com.dentalsmile.dentalsmile.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Autenticación", description = "Login y registro de usuarios del sistema")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) { this.authService = authService; }

    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión",
               description = "Devuelve un token JWT para usar en el header Authorization.")
    public ResponseEntity<Map<String, Object>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(authService.login(req));
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar usuario",
               description = "Crea un nuevo usuario del sistema (recepcionista, admin, odontólogo).")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.status(201).body(authService.register(req));
    }
}

