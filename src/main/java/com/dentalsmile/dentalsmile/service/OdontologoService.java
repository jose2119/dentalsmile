package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.OdontologoRequest;
import com.dentalsmile.dentalsmile.entity.Odontologo;
import com.dentalsmile.dentalsmile.exception.BusinessException;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.OdontologoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OdontologoService {

    private final OdontologoRepository repo;

    public OdontologoService(OdontologoRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public Page<Odontologo> listar(String q, String estado, Pageable pageable) {
        return repo.buscar(q, estado, pageable);
    }

    @Transactional(readOnly = true)
    public Odontologo obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Odontólogo", id));
    }

    @Transactional
    public Odontologo crear(OdontologoRequest req) {
        validarColegiatura(req.getNumeroColegiatura(), null);
        return repo.save(mapear(new Odontologo(), req));
    }

    @Transactional
    public Odontologo actualizar(Integer id, OdontologoRequest req) {
        Odontologo o = obtener(id);
        validarColegiatura(req.getNumeroColegiatura(), id);
        return repo.save(mapear(o, req));
    }

    @Transactional
    public void inactivar(Integer id) {
        Odontologo o = obtener(id);
        o.setEstado("Inactivo");
        repo.save(o);
    }

    private void validarColegiatura(String colegiatura, Integer excludeId) {
        if (colegiatura == null || colegiatura.isBlank()) return;
        repo.findByNumeroColegiatura(colegiatura).ifPresent(o -> {
            if (excludeId == null || !o.getOdontologoId().equals(excludeId)) {
                throw new BusinessException("El número de colegiatura '" + colegiatura + "' ya está registrado.");
            }
        });
    }

    private Odontologo mapear(Odontologo o, OdontologoRequest req) {
        o.setTipoDocumento(req.getTipoDocumento());
        o.setNumeroDocumento(req.getNumeroDocumento());
        o.setNombres(req.getNombres());
        o.setApellidos(req.getApellidos());
        o.setNumeroColegiatura(req.getNumeroColegiatura());
        o.setEspecialidad(req.getEspecialidad());
        o.setTelefono(req.getTelefono());
        o.setCorreo(req.getCorreo());
        if (req.getEstado() != null) o.setEstado(req.getEstado());
        return o;
    }
}

