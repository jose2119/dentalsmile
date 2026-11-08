package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Tratamiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TratamientoRepository extends JpaRepository<Tratamiento, Integer> {
    List<Tratamiento> findByActivoTrue();
}

