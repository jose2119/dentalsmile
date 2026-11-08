package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.HistoriaClinica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HistoriaClinicaRepository extends JpaRepository<HistoriaClinica, Long> {
    Optional<HistoriaClinica> findByPaciente_PacienteId(Integer pacienteId);
}

