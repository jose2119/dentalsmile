package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "historia_clinica")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class HistoriaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "historia_clinica_id")
    private Long historiaClinicaId;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true,
            foreignKey = @ForeignKey(name = "fk_hc_paciente"))
    private Paciente paciente;

    @Column(name = "grupo_sanguineo", length = 5)
    private String grupoSanguineo;

    @Column(name = "antecedentes_medicos", columnDefinition = "TEXT")
    private String antecedentesMedicos;

    @Column(name = "antecedentes_quirurgicos", columnDefinition = "TEXT")
    private String antecedentesQuirurgicos;

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "antecedentes_odontologicos", columnDefinition = "TEXT")
    private String antecedentesOdontologicos;

    @Column(name = "medicacion_actual", columnDefinition = "TEXT")
    private String medicacionActual;

    @Column(name = "habitos", columnDefinition = "TEXT")
    private String habitos;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;

    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private OffsetDateTime fechaCreacion;

    @Column(name = "creado_por", length = 128)
    private String creadoPor;

    @Column(name = "fecha_actualizacion")
    private OffsetDateTime fechaActualizacion;

    @Column(name = "actualizado_por", length = 128)
    private String actualizadoPor;

    @PrePersist
    void prePersist() {
        if (fechaCreacion == null) fechaCreacion = OffsetDateTime.now();
    }

    @PreUpdate
    void preUpdate() {
        fechaActualizacion = OffsetDateTime.now();
    }
}

