package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.OdontogramaRequest;
import com.dentalsmile.dentalsmile.entity.OdontogramaRegistro;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.AtencionClinicaRepository;
import com.dentalsmile.dentalsmile.repository.HistoriaClinicaRepository;
import com.dentalsmile.dentalsmile.repository.OdontogramaRegistroRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OdontogramaService {

    private final OdontogramaRegistroRepository repo;
    private final HistoriaClinicaRepository hcRepo;
    private final AtencionClinicaRepository atencionRepo;

    public OdontogramaService(OdontogramaRegistroRepository repo,
                              HistoriaClinicaRepository hcRepo,
                              AtencionClinicaRepository atencionRepo) {
        this.repo = repo;
        this.hcRepo = hcRepo;
        this.atencionRepo = atencionRepo;
    }

    @Transactional(readOnly = true)
    public List<OdontogramaRegistro> listarPorHistoria(Long historiaClinicaId) {
        return repo.findByHistoriaClinica_HistoriaClinicaIdOrderByFechaRegistroDesc(historiaClinicaId);
    }

    @Transactional(readOnly = true)
    public List<OdontogramaRegistro> listarPorPieza(Long historiaClinicaId, String piezaDental) {
        return repo.findByHistoriaClinica_HistoriaClinicaIdAndPiezaDentalOrderByFechaRegistroDesc(historiaClinicaId, piezaDental);
    }

    @Transactional
    public OdontogramaRegistro registrar(OdontogramaRequest req) {
        OdontogramaRegistro registro = OdontogramaRegistro.builder()
                .historiaClinica(hcRepo.findById(req.getHistoriaClinicaId())
                        .orElseThrow(() -> new ResourceNotFoundException("HistoriaClinica", req.getHistoriaClinicaId())))
                .piezaDental(req.getPiezaDental())
                .superficie(req.getSuperficie())
                .estadoPieza(req.getEstadoPieza())
                .hallazgo(req.getHallazgo())
                .registradoPor(usuarioActual())
                .build();

        if (req.getAtencionId() != null) {
            registro.setAtencion(atencionRepo.findById(req.getAtencionId())
                    .orElseThrow(() -> new ResourceNotFoundException("AtencionClinica", req.getAtencionId())));
        }

        return repo.save(registro);
    }

    private String usuarioActual() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null ? auth.getName() : "sistema";
    }
}

