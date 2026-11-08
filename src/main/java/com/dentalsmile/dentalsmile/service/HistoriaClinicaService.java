package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.HistoriaClinicaRequest;
import com.dentalsmile.dentalsmile.entity.HistoriaClinica;
import com.dentalsmile.dentalsmile.exception.BusinessException;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.HistoriaClinicaRepository;
import com.dentalsmile.dentalsmile.repository.PacienteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HistoriaClinicaService {

    private final HistoriaClinicaRepository repo;
    private final PacienteRepository        pacienteRepo;

    public HistoriaClinicaService(HistoriaClinicaRepository repo,
                                  PacienteRepository pacienteRepo) {
        this.repo        = repo;
        this.pacienteRepo = pacienteRepo;
    }

    @Transactional(readOnly = true)
    public HistoriaClinica obtenerPorPaciente(Integer pacienteId) {
        return repo.findByPaciente_PacienteId(pacienteId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "HistoriaClinica del paciente " + pacienteId));
    }

    @Transactional(readOnly = true)
    public HistoriaClinica obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("HistoriaClinica", id));
    }

    @Transactional
    public HistoriaClinica crear(HistoriaClinicaRequest req) {
        repo.findByPaciente_PacienteId(req.getPacienteId()).ifPresent(hc -> {
            throw new BusinessException("El paciente ya tiene historia clínica (ID: "
                    + hc.getHistoriaClinicaId() + ").");
        });
        HistoriaClinica hc = mapear(new HistoriaClinica(), req);
        hc.setCreadoPor(usuarioActual());
        return repo.save(hc);
    }

    @Transactional
    public HistoriaClinica actualizar(Long id, HistoriaClinicaRequest req) {
        HistoriaClinica hc = obtener(id);
        mapear(hc, req);
        hc.setActualizadoPor(usuarioActual());
        return repo.save(hc);
    }

    private HistoriaClinica mapear(HistoriaClinica hc, HistoriaClinicaRequest req) {
        hc.setPaciente(pacienteRepo.findById(req.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", req.getPacienteId())));
        hc.setGrupoSanguineo(req.getGrupoSanguineo());
        hc.setAntecedentesMedicos(req.getAntecedentesMedicos());
        hc.setAntecedentesQuirurgicos(req.getAntecedentesQuirurgicos());
        hc.setAntecedentesFamiliares(req.getAntecedentesFamiliares());
        hc.setAntecedentesOdontologicos(req.getAntecedentesOdontologicos());
        hc.setMedicacionActual(req.getMedicacionActual());
        hc.setHabitos(req.getHabitos());
        hc.setObservaciones(req.getObservaciones());
        return hc;
    }

    private String usuarioActual() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "sistema";
    }
}

