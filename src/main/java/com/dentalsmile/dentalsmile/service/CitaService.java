package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.CitaRequest;
import com.dentalsmile.dentalsmile.entity.Cita;
import com.dentalsmile.dentalsmile.exception.BusinessException;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.CitaRepository;
import com.dentalsmile.dentalsmile.repository.OdontologoRepository;
import com.dentalsmile.dentalsmile.repository.PacienteRepository;
import com.dentalsmile.dentalsmile.repository.TipoCitaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CitaService {

    private final CitaRepository      citaRepo;
    private final PacienteRepository  pacienteRepo;
    private final OdontologoRepository odontologoRepo;
    private final TipoCitaRepository  tipoCitaRepo;

    public CitaService(CitaRepository citaRepo, PacienteRepository pacienteRepo,
                       OdontologoRepository odontologoRepo, TipoCitaRepository tipoCitaRepo) {
        this.citaRepo       = citaRepo;
        this.pacienteRepo   = pacienteRepo;
        this.odontologoRepo = odontologoRepo;
        this.tipoCitaRepo   = tipoCitaRepo;
    }

    @Transactional(readOnly = true)
    public Page<Cita> listar(Integer pacienteId, Integer odontologoId,
                             String estado, Pageable pageable) {
        return citaRepo.buscar(pacienteId, odontologoId, estado, pageable);
    }

    @Transactional(readOnly = true)
    public Cita obtener(Long id) {
        return citaRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cita", id));
    }

    @Transactional
    public Cita crear(CitaRequest req, String creadoPor) {
        validarSolapamiento(req.getOdontologoId(), req.getInicio().toString(),
                req.getFin().toString(), null);
        Cita cita = mapear(new Cita(), req);
        cita.setCreadoPor(creadoPor);
        return citaRepo.save(cita);
    }

    @Transactional
    public Cita actualizar(Long id, CitaRequest req, String actualizadoPor) {
        Cita cita = obtener(id);
        validarSolapamiento(req.getOdontologoId(), req.getInicio().toString(),
                req.getFin().toString(), id);
        mapear(cita, req);
        cita.setCreadoPor(actualizadoPor);
        return citaRepo.save(cita);
    }

    @Transactional
    public Cita cambiarEstado(Long id, String nuevoEstado) {
        Cita cita = obtener(id);
        List<String> estadosValidos =
                List.of("Programada","Confirmada","EnAtencion","Atendida","Cancelada","NoAsistio");
        if (!estadosValidos.contains(nuevoEstado)) {
            throw new BusinessException("Estado inválido: " + nuevoEstado);
        }
        cita.setEstado(nuevoEstado);
        return citaRepo.save(cita);
    }

    // ── Validación de solapamiento en Java (refuerza la restricción EXCLUDE de PG) ──
    private void validarSolapamiento(Integer odontologoId, String inicioStr,
                                     String finStr, Long excludeId) {
        java.time.LocalDateTime inicio = java.time.LocalDateTime.parse(inicioStr);
        java.time.LocalDateTime fin    = java.time.LocalDateTime.parse(finStr);
        if (!fin.isAfter(inicio)) {
            throw new BusinessException("La hora de fin debe ser posterior al inicio.");
        }
        List<Cita> solapadas = citaRepo.findSolapadas(odontologoId, inicio, fin);
        solapadas.stream()
                .filter(c -> excludeId == null || !c.getCitaId().equals(excludeId))
                .findFirst()
                .ifPresent(c -> { throw new BusinessException(
                        "El odontólogo ya tiene una cita en ese horario (ID: " + c.getCitaId() + ")"); });
    }

    private Cita mapear(Cita cita, CitaRequest req) {
        cita.setPaciente(pacienteRepo.findById(req.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", req.getPacienteId())));
        cita.setOdontologo(odontologoRepo.findById(req.getOdontologoId())
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", req.getOdontologoId())));
        if (req.getTipoCitaId() != null) {
            cita.setTipoCita(tipoCitaRepo.findById(req.getTipoCitaId())
                    .orElseThrow(() -> new ResourceNotFoundException("TipoCita", req.getTipoCitaId())));
        }
        cita.setInicio(req.getInicio());
        cita.setFin(req.getFin());
        cita.setMotivo(req.getMotivo());
        cita.setObservaciones(req.getObservaciones());
        if (req.getEstado() != null) cita.setEstado(req.getEstado());
        return cita;
    }
}

