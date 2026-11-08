package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Alergia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlergiaRepository extends JpaRepository<Alergia, Integer> {
    List<Alergia> findByPaciente_PacienteId(Integer pacienteId);
    List<Alergia> findByPaciente_PacienteIdAndActivaTrue(Integer pacienteId);
}

