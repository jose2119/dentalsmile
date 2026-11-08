package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.OdontogramaRegistro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OdontogramaRegistroRepository extends JpaRepository<OdontogramaRegistro, Long> {
    List<OdontogramaRegistro> findByHistoriaClinica_HistoriaClinicaIdOrderByFechaRegistroDesc(Long historiaClinicaId);
    List<OdontogramaRegistro> findByHistoriaClinica_HistoriaClinicaIdAndPiezaDentalOrderByFechaRegistroDesc(Long historiaClinicaId, String piezaDental);
}

