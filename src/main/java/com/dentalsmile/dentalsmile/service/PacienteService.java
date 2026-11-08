package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.PacienteRequest;
import com.dentalsmile.dentalsmile.entity.Paciente;
import com.dentalsmile.dentalsmile.exception.BusinessException;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.PacienteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PacienteService {

    private final PacienteRepository repo;

    public PacienteService(PacienteRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public Page<Paciente> listar(String q, String estado, Pageable pageable) {
        return repo.buscar(q, estado, pageable);
    }

    @Transactional(readOnly = true)
    public Paciente obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", id));
    }

    @Transactional
    public Paciente crear(PacienteRequest req) {
        repo.findByTipoDocumentoAndNumeroDocumento(req.getTipoDocumento(), req.getNumeroDocumento())
                .ifPresent(p -> { throw new BusinessException(
                        "Ya existe un paciente con " + req.getTipoDocumento() + " " + req.getNumeroDocumento()); });
        return repo.save(mapear(new Paciente(), req));
    }

    @Transactional
    public Paciente actualizar(Integer id, PacienteRequest req) {
        Paciente p = obtener(id);
        // Verificar doc único si cambió
        if (!p.getTipoDocumento().equals(req.getTipoDocumento()) ||
                !p.getNumeroDocumento().equals(req.getNumeroDocumento())) {
            repo.findByTipoDocumentoAndNumeroDocumento(req.getTipoDocumento(), req.getNumeroDocumento())
                    .filter(existing -> !existing.getPacienteId().equals(id))
                    .ifPresent(dup -> { throw new BusinessException(
                            "El documento ya pertenece a otro paciente."); });
        }
        return repo.save(mapear(p, req));
    }

    @Transactional
    public void inactivar(Integer id) {
        Paciente p = obtener(id);
        p.setEstado("Inactivo");
        repo.save(p);
    }

    private Paciente mapear(Paciente p, PacienteRequest req) {
        p.setTipoDocumento(req.getTipoDocumento());
        p.setNumeroDocumento(req.getNumeroDocumento());
        p.setNombres(req.getNombres());
        p.setApellidos(req.getApellidos());
        p.setFechaNacimiento(req.getFechaNacimiento());
        p.setSexo(req.getSexo());
        p.setTelefono(req.getTelefono());
        p.setCorreo(req.getCorreo());
        p.setDireccion(req.getDireccion());
        p.setContactoEmergencia(req.getContactoEmergencia());
        p.setTelefonoEmergencia(req.getTelefonoEmergencia());
        if (req.getEstado() != null) p.setEstado(req.getEstado());
        return p;
    }
}

