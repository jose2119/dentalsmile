package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.TipoCita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TipoCitaRepository extends JpaRepository<TipoCita, Integer> {
    List<TipoCita> findByActivoTrue();
}

