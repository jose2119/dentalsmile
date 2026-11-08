package com.dentalsmile.dentalsmile.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Registro de estado o hallazgo sobre una pieza dental en el odontograma")
public class OdontogramaRequest {

    @NotNull
    @Schema(example = "1")
    private Long historiaClinicaId;

    @Schema(example = "1")
    private Long atencionId;

    @NotBlank
    @Schema(example = "18", description = "Número de pieza dental según nomenclatura FDI (ej. 11 a 48)")
    private String piezaDental;

    @Schema(example = "Oclusal", description = "Superficie: Oclusal, Mesial, Distal, Vestibular, Lingual, Palatino")
    private String superficie;

    @NotBlank
    @Schema(example = "Caries dental", description = "Estado o condición: Sano, Caries dental, Obturado, Ausente, Corona, etc.")
    private String estadoPieza;

    @Schema(example = "Caries en esmalte y dentina superficial")
    private String hallazgo;
}

