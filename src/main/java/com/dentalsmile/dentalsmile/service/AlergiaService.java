package com.dentalsmile.dentalsmile.service;

import com.dentalsmile.dentalsmile.dto.request.AlergiaRequest;
import com.dentalsmile.dentalsmile.entity.Alergia;
import com.dentalsmile.dentalsmile.exception.ResourceNotFoundException;
import com.dentalsmile.dentalsmile.repository.AlergiaRepository;
import com.dentalsmile.dentalsmile.repository.PacienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlergiaService {

    private final AlergiaRepository  repo;
    private final PacienteRepository pacienteRepo;

    public AlergiaService(AlergiaRepository repo, PacienteRepository pacienteRepo) {
        this.repo        = repo;
        this.pacienteRepo = pacienteRepo;
    }

    @Transactional(readOnly = true)
    public List<Alergia> listarPorPaciente(Integer pacienteId, boolean soloActivas) {
        return soloActivas
                ? repo.findByPaciente_PacienteIdAndActivaTrue(pacienteId)
                : repo.findByPaciente_PacienteId(pacienteId);
    }

    @Transactional(readOnly = true)
    public Alergia obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alergia", id));
    }

    @Transactional
    public Alergia crear(AlergiaRequest req) {
        Alergia a = mapear(new Alergia(), req);
        return repo.save(a);
    }

    @Transactional
    public Alergia actualizar(Integer id, AlergiaRequest req) {
        return repo.save(mapear(obtener(id), req));
    }

    @Transactional
    public void inactivar(Integer id) {
        Alergia a = obtener(id);
        a.setActiva(false);
        repo.save(a);
    }

    private Alergia mapear(Alergia a, AlergiaRequest req) {
        a.setPaciente(pacienteRepo.findById(req.getPacienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente", req.getPacienteId())));
        a.setSustancia(req.getSustancia());
        a.setTipoReaccion(req.getTipoReaccion());
        a.setSeveridad(req.getSeveridad());
        if (req.getActiva() != null) a.setActiva(req.getActiva());
        return a;
    }
}

