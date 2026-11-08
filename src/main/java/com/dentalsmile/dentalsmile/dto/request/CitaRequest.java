package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Datos para crear o actualizar una cita")
public class CitaRequest {

    @NotNull @Schema(example = "1")
    private Integer pacienteId;

    @NotNull @Schema(example = "1")
    private Integer odontologoId;

    @Schema(example = "1")
    private Integer tipoCitaId;

    @NotNull @Schema(example = "2026-12-01T10:00:00")
    private LocalDateTime inicio;

    @NotNull @Schema(example = "2026-12-01T10:45:00")
    private LocalDateTime fin;

    @Schema(example = "Dolor en muela del juicio")
    private String motivo;

    @Schema(example = "Programada",
            allowableValues = {"Programada","Confirmada","EnAtencion","Atendida","Cancelada","NoAsistio"})
    private String estado;

    @Schema(example = "Paciente alérgico a la penicilina")
    private String observaciones;
}

