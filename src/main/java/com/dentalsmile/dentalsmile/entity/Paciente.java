package com.dentalsmile.dentalsmile.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "paciente",
        uniqueConstraints = @UniqueConstraint(name = "uq_paciente_documento",
                columnNames = {"tipo_documento", "numero_documento"}))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "paciente_id")
    private Integer pacienteId;

    @Column(name = "tipo_documento", nullable = false, length = 25)
    private String tipoDocumento;

    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "sexo", length = 25)
    private String sexo;

    @Column(name = "telefono", length = 25)
    private String telefono;

    @Column(name = "correo", length = 150)
    private String correo;

    @Column(name = "direccion", length = 250)
    private String direccion;

    @Column(name = "contacto_emergencia", length = 150)
    private String contactoEmergencia;

    @Column(name = "telefono_emergencia", length = 25)
    private String telefonoEmergencia;

    @Column(name = "fecha_registro", nullable = false, updatable = false)
    private OffsetDateTime fechaRegistro;

    @Column(name = "estado", nullable = false, length = 15)
    private String estado;

    @PrePersist
    void prePersist() {
        if (fechaRegistro == null) fechaRegistro = OffsetDateTime.now();
        if (estado == null) estado = "Activo";
    }
}

