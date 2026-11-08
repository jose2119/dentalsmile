package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Datos para registrar una atención clínica")
public class AtencionClinicaRequest {

    @NotNull @Schema(example = "1")
    private Long historiaClinicaId;

    @Schema(example = "1", description = "ID de la cita (opcional)")
    private Long citaId;

    @NotNull @Schema(example = "1")
    private Integer odontologoId;

    @NotBlank @Schema(example = "Dolor intenso en molar inferior derecho")
    private String motivoConsulta;

    @Schema(example = "Dolor de 3 días de evolución, EVA 8/10")
    private String anamnesis;

    @Schema(example = "Sin asimetría facial")
    private String examenExtraoral;

    @Schema(example = "Caries profunda en pieza 46")
    private String examenIntraoral;

    @Schema(example = "Caries con compromiso pulpar pieza 46")
    private String diagnosticoClinico;

    @Schema(example = "Radiografía periapical, test de vitalidad")
    private String procedimientos;

    @Schema(example = "Tratamiento de conducto pieza 46")
    private String planTratamiento;

    @Schema(example = "Ibuprofeno 400mg c/8h, dieta blanda, control en 7 días")
    private String indicaciones;

    @Schema(example = "2026-12-10")
    private LocalDate proximaCita;

    @Schema(example = "Finalizada", allowableValues = {"EnCurso","Finalizada","Derivada"})
    private String estado;
}

