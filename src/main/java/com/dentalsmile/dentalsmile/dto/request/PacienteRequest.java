package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos para crear o actualizar un paciente")
public class PacienteRequest {

    @NotBlank @Schema(example = "DNI")
    private String tipoDocumento;

    @NotBlank @Schema(example = "12345678")
    private String numeroDocumento;

    @NotBlank @Schema(example = "María")
    private String nombres;

    @NotBlank @Schema(example = "García López")
    private String apellidos;

    @Schema(example = "1990-05-15")
    private LocalDate fechaNacimiento;

    @Schema(example = "Femenino", allowableValues = {"Femenino","Masculino","No especificado","Prefiere no indicar"})
    private String sexo;

    @Schema(example = "987654321")
    private String telefono;

    @Schema(example = "mgarcia@correo.com")
    private String correo;

    @Schema(example = "Av. Principal 123, Lima")
    private String direccion;

    @Schema(example = "Carlos García")
    private String contactoEmergencia;

    @Schema(example = "999888777")
    private String telefonoEmergencia;

    @Schema(example = "Activo", allowableValues = {"Activo","Inactivo"})
    private String estado;
}

