package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Datos para crear o actualizar la historia clínica de un paciente")
public class HistoriaClinicaRequest {

    @NotNull @Schema(example = "1")
    private Integer pacienteId;

    @Schema(example = "O+")
    private String grupoSanguineo;

    @Schema(example = "Hipertensión arterial controlada")
    private String antecedentesMedicos;

    @Schema(example = "Apendicectomía 2010")
    private String antecedentesQuirurgicos;

    @Schema(example = "Padre diabético")
    private String antecedentesFamiliares;

    @Schema(example = "Caries múltiples en infancia")
    private String antecedentesOdontologicos;

    @Schema(example = "Losartán 50mg/día")
    private String medicacionActual;

    @Schema(example = "Fumador social, poco ejercicio")
    private String habitos;

    @Schema(example = "Sin observaciones adicionales")
    private String observaciones;
}

