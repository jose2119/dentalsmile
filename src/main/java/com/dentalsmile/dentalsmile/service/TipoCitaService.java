package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.entity.TipoCita;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.TipoCitaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoCitaService {

    private final TipoCitaRepository repo;

    public TipoCitaService(TipoCitaRepository repo) { this.repo = repo; }

    @Transactional(readOnly = true)
    public List<TipoCita> listarActivos() { return repo.findByActivoTrue(); }

    @Transactional(readOnly = true)
    public List<TipoCita> listarTodos() { return repo.findAll(); }

    @Transactional(readOnly = true)
    public TipoCita obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TipoCita", id));
    }

    @Transactional
    public TipoCita crear(TipoCita tc) { return repo.save(tc); }

    @Transactional
    public TipoCita actualizar(Integer id, TipoCita datos) {
        TipoCita tc = obtener(id);
        tc.setNombre(datos.getNombre());
        tc.setDuracionMinutos(datos.getDuracionMinutos());
        tc.setDescripcion(datos.getDescripcion());
        tc.setActivo(datos.getActivo());
        return repo.save(tc);
    }
}

