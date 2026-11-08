package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.OdontologoRequest;
import com.dentalsmile.dentalsmile.entity.Odontologo;
import com.dentalsmile.dentalsmile.service.OdontologoService;
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
@RequestMapping("/api/odontologos")
@Tag(name = "Odontólogos", description = "CRUD de odontólogos/especialistas")
public class OdontologoController {

    private final OdontologoService service;

    public OdontologoController(OdontologoService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Listar odontólogos")
    public ResponseEntity<Page<Odontologo>> listar(
            @Parameter(description = "Búsqueda por nombre, apellido o especialidad")
            @RequestParam(required = false) String q,
            @Parameter(description = "Activo | Inactivo")
            @RequestParam(required = false) String estado,
            @PageableDefault(size = 20, sort = "apellidos") Pageable pageable) {
        return ResponseEntity.ok(service.listar(q, estado, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener odontólogo por ID")
    public ResponseEntity<Odontologo> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear odontólogo")
    public ResponseEntity<Odontologo> crear(@Valid @RequestBody OdontologoRequest req) {
        Odontologo creado = service.crear(req);
        return ResponseEntity.created(
                URI.create("/api/odontologos/" + creado.getOdontologoId())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar odontólogo")
    public ResponseEntity<Odontologo> actualizar(@PathVariable Integer id,
                                                 @Valid @RequestBody OdontologoRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactivar odontólogo (soft delete)")
    public ResponseEntity<Void> inactivar(@PathVariable Integer id) {
        service.inactivar(id);
        return ResponseEntity.noContent().build();
    }
}

