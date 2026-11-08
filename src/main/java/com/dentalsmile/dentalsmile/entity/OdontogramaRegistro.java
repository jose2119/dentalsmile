package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "odontograma_registro")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OdontogramaRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odontograma_registro_id")
    private Long odontogramaRegistroId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historia_clinica_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_odontograma_historia"))
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atencion_id",
            foreignKey = @ForeignKey(name = "fk_odontograma_atencion"))
    private AtencionClinica atencion;

    @Column(name = "pieza_dental", nullable = false, length = 5)
    private String piezaDental;

    @Column(name = "superficie", length = 20)
    private String superficie;

    @Column(name = "estado_pieza", nullable = false, length = 80)
    private String estadoPieza;

    @Column(name = "hallazgo", length = 500)
    private String hallazgo;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private OffsetDateTime fechaRegistro;

    @Column(name = "registrado_por", length = 128)
    private String registradoPor;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) fechaRegistro = OffsetDateTime.now();
    }
}

