package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.PacienteRequest;
import com.dentalsmile.dentalsmile.entity.Paciente;
import com.dentalsmile.dentalsmile.service.PacienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "CRUD completo de pacientes")
public class PacienteController {

    private final PacienteService service;

    public PacienteController(PacienteService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Listar pacientes",
               description = "Búsqueda paginada por nombre, apellido, correo o documento.")
    public ResponseEntity<Page<Paciente>> listar(
            @Parameter(description = "Texto de búsqueda libre") @RequestParam(required = false) String q,
            @Parameter(description = "Filtrar por estado: Activo | Inactivo") @RequestParam(required = false) String estado,
            @PageableDefault(size = 20, sort = "apellidos") Pageable pageable) {
        return ResponseEntity.ok(service.listar(q, estado, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener paciente por ID")
    public ResponseEntity<Paciente> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear paciente")
    public ResponseEntity<Paciente> crear(@Valid @RequestBody PacienteRequest req) {
        Paciente creado = service.crear(req);
        return ResponseEntity.created(
                URI.create("/api/pacientes/" + creado.getPacienteId())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar paciente")
    public ResponseEntity<Paciente> actualizar(@PathVariable Integer id,
                                               @Valid @RequestBody PacienteRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactivar paciente (soft delete)")
    public ResponseEntity<Void> inactivar(@PathVariable Integer id) {
        service.inactivar(id);
        return ResponseEntity.noContent().build();
    }
}

