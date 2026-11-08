package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_cita")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoCita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_cita_id")
    private Integer tipoCitaId;

    @Column(name = "nombre", nullable = false, unique = true, length = 80)
    private String nombre;

    @Column(name = "duracion_minutos", nullable = false)
    private Short duracionMinutos;

    @Column(name = "descripcion", length = 250)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    void prePersist() {
        if (activo == null) activo = true;
    }
}

