package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "atencion_clinica")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class AtencionClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "atencion_id")
    private Long atencionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "historia_clinica_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_atencion_historia"))
    private HistoriaClinica historiaClinica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cita_id",
            foreignKey = @ForeignKey(name = "fk_atencion_cita"))
    private Cita cita;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "odontologo_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_atencion_odontologo"))
    private Odontologo odontologo;

    @Column(name = "fecha_hora", nullable = false)
    private OffsetDateTime fechaHora;

    @Column(name = "motivo_consulta", nullable = false, columnDefinition = "TEXT")
    private String motivoConsulta;

    @Column(name = "anamnesis", columnDefinition = "TEXT")
    private String anamnesis;

    @Column(name = "examen_extraoral", columnDefinition = "TEXT")
    private String examenExtraoral;

    @Column(name = "examen_intraoral", columnDefinition = "TEXT")
    private String examenIntraoral;

    @Column(name = "diagnostico_clinico", columnDefinition = "TEXT")
    private String diagnosticoClinico;

    @Column(name = "procedimientos", columnDefinition = "TEXT")
    private String procedimientos;

    @Column(name = "plan_tratamiento", columnDefinition = "TEXT")
    private String planTratamiento;

    @Column(name = "indicaciones", columnDefinition = "TEXT")
    private String indicaciones;

    @Column(name = "proxima_cita")
    private LocalDate proximaCita;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "creado_por", length = 128)
    private String creadoPor;

    @PrePersist
    void prePersist() {
        if (fechaHora == null) fechaHora = OffsetDateTime.now();
        if (fechaCreacion == null) fechaCreacion = OffsetDateTime.now();
        if (estado == null) estado = "Finalizada";
    }
}

