package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.entity.TipoCita;
import com.dentalsmile.dentalsmile.service.TipoCitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-cita")
@Tag(name = "Tipos de Cita", description = "Catálogo de tipos de atención dental")
public class TipoCitaController {

    private final TipoCitaService service;

    public TipoCitaController(TipoCitaService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Listar todos los tipos de cita")
    public ResponseEntity<List<TipoCita>> listar(
            @RequestParam(defaultValue = "true") boolean soloActivos) {
        return ResponseEntity.ok(soloActivos ? service.listarActivos() : service.listarTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener tipo de cita por ID")
    public ResponseEntity<TipoCita> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear tipo de cita")
    public ResponseEntity<TipoCita> crear(@RequestBody TipoCita tc) {
        TipoCita creado = service.crear(tc);
        return ResponseEntity.created(
                URI.create("/api/tipos-cita/" + creado.getTipoCitaId())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar tipo de cita")
    public ResponseEntity<TipoCita> actualizar(@PathVariable Integer id,
                                               @RequestBody TipoCita datos) {
        return ResponseEntity.ok(service.actualizar(id, datos));
    }
}

