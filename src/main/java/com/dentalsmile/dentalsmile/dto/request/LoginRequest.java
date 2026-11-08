package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Credenciales para iniciar sesión")
public class LoginRequest {

    @NotBlank(message = "El username es obligatorio")
    @Schema(example = "admin", description = "Nombre de usuario")
    private String username;

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(example = "admin123", description = "Contraseña")
    private String password;
}

