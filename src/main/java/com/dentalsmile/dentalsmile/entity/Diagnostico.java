package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "diagnostico")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Diagnostico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diagnostico_id")
    private Integer diagnosticoId;

    @Column(name = "codigo", length = 30)
    private String codigo;

    @Column(name = "nombre", nullable = false, length = 200)
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

