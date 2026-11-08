package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tratamiento")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Tratamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tratamiento_id")
    private Integer tratamientoId;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", length = 500)
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @PrePersist
    void prePersist() {
        if (activo == null) activo = true;
    }
}

