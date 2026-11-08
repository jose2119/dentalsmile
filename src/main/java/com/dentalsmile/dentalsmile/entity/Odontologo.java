package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Entity
@Table(name = "odontologo")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Odontologo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "odontologo_id")
    private Integer odontologoId;

    @Column(name = "tipo_documento", length = 25)
    private String tipoDocumento;

    @Column(name = "numero_documento", length = 20)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "numero_colegiatura", length = 30)
    private String numeroColegiatura;

    @Column(name = "especialidad", length = 100)
    private String especialidad;

    @Column(name = "telefono", length = 25)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @PrePersist
    void prePersist() {
        if (estado == null) estado = "Activo";
    }
}

