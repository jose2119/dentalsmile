package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.AtencionClinicaRequest;
import com.dentalsmile.dentalsmile.entity.AtencionClinica;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AtencionClinicaService {

    private final AtencionClinicaRepository repo;
    private final HistoriaClinicaRepository hcRepo;
    private final CitaRepository            citaRepo;
    private final OdontologoRepository      odontologoRepo;

    public AtencionClinicaService(AtencionClinicaRepository repo,
                                  HistoriaClinicaRepository hcRepo,
                                  CitaRepository citaRepo,
                                  OdontologoRepository odontologoRepo) {
        this.repo          = repo;
        this.hcRepo        = hcRepo;
        this.citaRepo      = citaRepo;
        this.odontologoRepo = odontologoRepo;
    }

    @Transactional(readOnly = true)
    public Page<AtencionClinica> listarPorHistoria(Long hcId, Pageable pageable) {
        return repo.findByHistoriaClinicaId(hcId, pageable);
    }

    @Transactional(readOnly = true)
    public AtencionClinica obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AtencionClinica", id));
    }

    @Transactional
    public AtencionClinica crear(AtencionClinicaRequest req) {
        AtencionClinica ac = mapear(new AtencionClinica(), req);
        ac.setCreadoPor(usuarioActual());
        return repo.save(ac);
    }

    @Transactional
    public AtencionClinica actualizar(Long id, AtencionClinicaRequest req) {
        return repo.save(mapear(obtener(id), req));
    }

    private AtencionClinica mapear(AtencionClinica ac, AtencionClinicaRequest req) {
        ac.setHistoriaClinica(hcRepo.findById(req.getHistoriaClinicaId())
                .orElseThrow(() -> new ResourceNotFoundException("HistoriaClinica", req.getHistoriaClinicaId())));
        if (req.getCitaId() != null) {
            ac.setCita(citaRepo.findById(req.getCitaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Cita", req.getCitaId())));
        }
        ac.setOdontologo(odontologoRepo.findById(req.getOdontologoId())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", req.getOdontologoId())));
        ac.setMotivoConsulta(req.getMotivoConsulta());
        ac.setAnamnesis(req.getAnamnesis());
        ac.setExamenExtraoral(req.getExamenExtraoral());
        ac.setExamenIntraoral(req.getExamenIntraoral());
        ac.setDiagnosticoClinico(req.getDiagnosticoClinico());
        ac.setProcedimientos(req.getProcedimientos());
        ac.setPlanTratamiento(req.getPlanTratamiento());
        ac.setIndicaciones(req.getIndicaciones());
        ac.setProximaCita(req.getProximaCita());
        if (req.getEstado() != null) ac.setEstado(req.getEstado());
        return ac;
    }

    private String usuarioActual() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "sistema";
    }
}

