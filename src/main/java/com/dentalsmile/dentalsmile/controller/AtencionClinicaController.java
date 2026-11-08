package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.AtencionClinicaRequest;
import com.dentalsmile.dentalsmile.entity.AtencionClinica;
import com.dentalsmile.dentalsmile.service.AtencionClinicaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/atenciones")
@Tag(name = "Atenciones Clínicas", description = "Registro de cada consulta/atención odontológica")
public class AtencionClinicaController {

    private final AtencionClinicaService service;

    public AtencionClinicaController(AtencionClinicaService service) { this.service = service; }

    @GetMapping("/historia/{hcId}")
    @Operation(summary = "Listar atenciones de una historia clínica",
               description = "Devuelve las atenciones ordenadas por fecha descendente, paginadas.")
    public ResponseEntity<Page<AtencionClinica>> listar(
            @PathVariable Long hcId,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(service.listarPorHistoria(hcId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener atención por ID")
    public ResponseEntity<AtencionClinica> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    @Operation(summary = "Registrar atención clínica")
    public ResponseEntity<AtencionClinica> crear(@Valid @RequestBody AtencionClinicaRequest req) {
        AtencionClinica ac = service.crear(req);
        return ResponseEntity.created(
                URI.create("/api/atenciones/" + ac.getAtencionId())).body(ac);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar atención clínica")
    public ResponseEntity<AtencionClinica> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody AtencionClinicaRequest req) {
        return ResponseEntity.ok(service.actualizar(id, req));
    }
}

