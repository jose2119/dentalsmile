package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos para registrar una alergia de un paciente")
public class AlergiaRequest {

    @NotNull @Schema(example = "1")
    private Integer pacienteId;

    @NotBlank @Schema(example = "Penicilina")
    private String sustancia;

    @Schema(example = "Urticaria y dificultad respiratoria")
    private String tipoReaccion;

    @Schema(example = "Grave", allowableValues = {"Leve","Moderada","Grave","Desconocida"})
    private String severidad;

    @Schema(example = "true")
    private Boolean activa;
}

