package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Diagnostico;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DiagnosticoRepository extends JpaRepository<Diagnostico, Integer> {
    List<Diagnostico> findByActivoTrue();
}

