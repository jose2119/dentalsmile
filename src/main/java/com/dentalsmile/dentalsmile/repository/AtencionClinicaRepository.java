package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.AtencionClinica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtencionClinicaRepository extends JpaRepository<AtencionClinica, Long> {

    @Query("""
            SELECT a FROM AtencionClinica a
            WHERE a.historiaClinica.historiaClinicaId = :hcId
            ORDER BY a.fechaHora DESC
            """)
    Page<AtencionClinica> findByHistoriaClinicaId(@Param("hcId") Long hcId, Pageable pageable);
}

