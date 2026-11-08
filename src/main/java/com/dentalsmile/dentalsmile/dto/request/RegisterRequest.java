package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Datos para registrar un nuevo usuario del sistema")
public class RegisterRequest {

    @NotBlank @Size(min = 3, max = 80)
    @Schema(example = "jperez")
    private String username;

    @NotBlank @Size(min = 6)
    @Schema(example = "Secreto123!")
    private String password;

    @Schema(example = "Juan Pérez")
    private String nombreCompleto;

    @Email
    @Schema(example = "jperez@happysmile.pe")
    private String correo;

    /** ADMIN | RECEPCIONISTA | ODONTOLOGO */
    @Schema(example = "RECEPCIONISTA", allowableValues = {"ADMIN","RECEPCIONISTA","ODONTOLOGO"})
    private String rol;
}

