package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.dto.request.OdontogramaRequest;
import com.dentalsmile.dentalsmile.entity.OdontogramaRegistro;
import com.dentalsmile.dentalsmile.service.OdontogramaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/odontograma")
@Tag(name = "Odontograma", description = "Gestión del odontograma y evolución de piezas dentales")
public class OdontogramaController {

    private final OdontogramaService service;

    public OdontogramaController(OdontogramaService service) {
        this.service = service;
    }

    @GetMapping("/historia/{hcId}")
    @Operation(summary = "Obtener odontograma por historia clínica",
               description = "Devuelve el historial cronológico de hallazgos en piezas dentales")
    public ResponseEntity<List<OdontogramaRegistro>> listarPorHistoria(@PathVariable Long hcId) {
        return ResponseEntity.ok(service.listarPorHistoria(hcId));
    }

    @GetMapping("/historia/{hcId}/pieza/{pieza}")
    @Operation(summary = "Obtener registros de una pieza dental específica")
    public ResponseEntity<List<OdontogramaRegistro>> listarPorPieza(
            @PathVariable Long hcId,
            @PathVariable String pieza) {
        return ResponseEntity.ok(service.listarPorPieza(hcId, pieza));
    }

    @PostMapping
    @Operation(summary = "Registrar hallazgo en el odontograma")
    public ResponseEntity<OdontogramaRegistro> registrar(@Valid @RequestBody OdontogramaRequest req) {
        OdontogramaRegistro guardado = service.registrar(req);
        return ResponseEntity.created(
                URI.create("/api/odontograma/" + guardado.getOdontogramaRegistroId())).body(guardado);
    }
}

