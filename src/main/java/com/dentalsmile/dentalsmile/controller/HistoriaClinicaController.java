package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.HistoriaClinicaRequest;
import com.dentalsmile.dentalsmile.entity.HistoriaClinica;
import com.dentalsmile.dentalsmile.service.HistoriaClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/historias-clinicas")
@Tag(name = "Historia Clínica", description = "Gestión de historias clínicas (una por paciente)")
public class HistoriaClinicaController {

    private final HistoriaClinicaService service;

    public HistoriaClinicaController(HistoriaClinicaService service) { this.service = service; }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Obtener historia clínica por ID de paciente")
    public ResponseEntity<HistoriaClinica> obtenerPorPaciente(@PathVariable Integer pacienteId) {
        return ResponseEntity.ok(service.obtenerPorPaciente(pacienteId));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener historia clínica por ID propio")
    public ResponseEntity<HistoriaClinica> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear historia clínica",
               description = "Cada paciente puede tener solo una historia clínica.")
    public ResponseEntity<HistoriaClinica> crear(@Valid @RequestBody HistoriaClinicaRequest req) {
        HistoriaClinica hc = service.crear(req);
        return ResponseEntity.created(
                URI.create("/api/historias-clinicas/" + hc.getHistoriaClinicaId())).body(hc);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar historia clínica")
    public ResponseEntity<HistoriaClinica> actualizar(@PathVariable Long id,
                                                      @Valid @RequestBody HistoriaClinicaRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }
}

