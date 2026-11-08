package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "alergia")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Alergia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alergia_id")
    private Integer alergiaId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "paciente_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_alergia_paciente"))
    private Paciente paciente;

    @Column(name = "sustancia", nullable = false, length = 150)
    private String sustancia;

    @Column(name = "tipo_reaccion", length = 250)
    private String tipoReaccion;

    @Column(name = "severidad", length = 20)
    private String severidad;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private OffsetDateTime fechaRegistro;

    @Column(name = "activa", nullable = false)
    private Boolean activa;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) fechaRegistro = OffsetDateTime.now();
        if (activa == null) activa = true;
    }
}

