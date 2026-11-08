package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.AlergiaRequest;
import com.dentalsmile.dentalsmile.entity.Alergia;
import com.dentalsmile.dentalsmile.service.AlergiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/alergias")
@Tag(name = "Alergias", description = "Registro y consulta de alergias por paciente")
public class AlergiaController {

    private final AlergiaService service;

    public AlergiaController(AlergiaService service) { this.service = service; }

    @GetMapping("/paciente/{pacienteId}")
    @Operation(summary = "Listar alergias de un paciente")
    public ResponseEntity<List<Alergia>> listar(
            @PathVariable Integer pacienteId,
            @RequestParam(defaultValue = "false") boolean soloActivas) {
        return ResponseEntity.ok(service.listarPorPaciente(pacienteId, soloActivas));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener alergia por ID")
    public ResponseEntity<Alergia> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Registrar alergia")
    public ResponseEntity<Alergia> crear(@Valid @RequestBody AlergiaRequest req) {
        Alergia creada = service.crear(req);
        return ResponseEntity.created(
                URI.create("/api/alergias/" + creada.getAlergiaId())).body(creada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar alergia")
    public ResponseEntity<Alergia> actualizar(@PathVariable Integer id,
                                              @Valid @RequestBody AlergiaRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Inactivar alergia (soft delete)")
    public ResponseEntity<Void> inactivar(@PathVariable Integer id) {
        service.inactivar(id);
        return ResponseEntity.noContent().build();
    }
}

