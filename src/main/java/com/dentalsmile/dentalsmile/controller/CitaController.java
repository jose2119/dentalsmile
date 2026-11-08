package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.CitaRequest;
import com.dentalsmile.dentalsmile.entity.Cita;
import com.dentalsmile.dentalsmile.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/citas")
@Tag(name = "Citas", description = "Agenda de citas dentales con validación de solapamiento")
public class CitaController {

    private final CitaService service;

    public CitaController(CitaService service) { this.service = service; }

    @GetMapping
    @Operation(summary = "Listar / filtrar citas")
    public ResponseEntity<Page<Cita>> listar(
            @Parameter(description = "Filtrar por ID de paciente")  @RequestParam(required = false) Integer pacienteId,
            @Parameter(description = "Filtrar por ID de odontólogo") @RequestParam(required = false) Integer odontologoId,
            @Parameter(description = "Estado de la cita")           @RequestParam(required = false) String estado,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(service.listar(pacienteId, odontologoId, estado, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cita por ID")
    public ResponseEntity<Cita> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Crear cita",
               description = "Verifica solapamiento de horario antes de guardar.")
    public ResponseEntity<Cita> crear(@Valid @RequestBody CitaRequest req,
                                      Authentication auth) {
        Cita creada = service.crear(req, auth.getName());
        return ResponseEntity.created(
                URI.create("/api/citas/" + creada.getCitaId())).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar cita")
    public ResponseEntity<Cita> actualizar(@PathVariable Long id,
                                           @Valid @RequestBody CitaRequest req,
                                           Authentication auth) {
        return ResponseEntity.ok(service.actualizar(id, req, auth.getName()));
    }

    @PatchMapping("/{id}/estado")
    @Operation(summary = "Cambiar estado de la cita",
               description = "Permite transiciones de estado: Programada → Confirmada → EnAtencion → Atendida / Cancelada / NoAsistio")
    public ResponseEntity<Cita> cambiarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(service.cambiarEstado(id, body.get("estado")));
    }
}

