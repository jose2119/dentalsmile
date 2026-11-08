package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "Datos para crear o actualizar un odontólogo")
public class OdontologoRequest {

    @Schema(example = "DNI")
    private String tipoDocumento;

    @Schema(example = "87654321")
    private String numeroDocumento;

    @NotBlank @Schema(example = "Carlos")
    private String nombres;

    @NotBlank @Schema(example = "Rodríguez Vega")
    private String apellidos;

    @Schema(example = "COP-12345")
    private String numeroColegiatura;

    @Schema(example = "Ortodoncia")
    private String especialidad;

    @Schema(example = "991234567")
    private String telefono;

    @Schema(example = "crodriguez@happysmile.pe")
    private String correo;

    @Schema(example = "Activo", allowableValues = {"Activo","Inactivo"})
    private String estado;
}

