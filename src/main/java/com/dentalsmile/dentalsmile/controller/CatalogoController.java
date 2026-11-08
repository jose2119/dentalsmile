package com.dentalsmile.dentalsmile.controller;

import com.dentalsmile.dentalsmile.entity.Diagnostico;
import com.dentalsmile.dentalsmile.entity.Tratamiento;
import com.dentalsmile.dentalsmile.repository.DiagnosticoRepository;
import com.dentalsmile.dentalsmile.repository.TratamientoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/catalogos")
@Tag(name = "Catálogos", description = "Gestión de Diagnósticos y Tratamientos odontológicos")
public class CatalogoController {

    private final DiagnosticoRepository diagRepo;
    private final TratamientoRepository tratRepo;

    public CatalogoController(DiagnosticoRepository diagRepo, TratamientoRepository tratRepo) {
        this.diagRepo = diagRepo;
        this.tratRepo = tratRepo;
    }

    // --- Diagnósticos ---
    @GetMapping("/diagnosticos")
    @Operation(summary = "Listar diagnósticos activos")
    public ResponseEntity<List<Diagnostico>> listarDiagnosticos() {
        return ResponseEntity.ok(diagRepo.findByActivoTrue());
    }

    @PostMapping("/diagnosticos")
    @Operation(summary = "Crear diagnóstico")
    public ResponseEntity<Diagnostico> crearDiagnostico(@RequestBody Diagnostico d) {
        Diagnostico guardado = diagRepo.save(d);
        return ResponseEntity.created(URI.create("/api/catalogos/diagnosticos/" + guardado.getDiagnosticoId())).body(guardado);
    }

    // --- Tratamientos ---
    @GetMapping("/tratamientos")
    @Operation(summary = "Listar tratamientos activos")
    public ResponseEntity<List<Tratamiento>> listarTratamientos() {
        return ResponseEntity.ok(tratRepo.findByActivoTrue());
    }

    @PostMapping("/tratamientos")
    @Operation(summary = "Crear tratamiento")
    public ResponseEntity<Tratamiento> crearTratamiento(@RequestBody Tratamiento t) {
        Tratamiento guardado = tratRepo.save(t);
        return ResponseEntity.created(URI.create("/api/catalogos/tratamientos/" + guardado.getTratamientoId())).body(guardado);
    }
}

