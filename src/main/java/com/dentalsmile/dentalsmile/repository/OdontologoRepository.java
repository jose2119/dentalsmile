package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Odontologo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OdontologoRepository extends JpaRepository<Odontologo, Integer> {

    Optional<Odontologo> findByNumeroColegiatura(String numeroColegiatura);

    @Query("""
            SELECT o FROM Odontologo o
            WHERE (:estado IS NULL OR o.estado = :estado)
              AND (:q IS NULL OR LOWER(o.nombres)    LIKE LOWER(CONCAT('%',:q,'%'))
                             OR LOWER(o.apellidos)   LIKE LOWER(CONCAT('%',:q,'%'))
                             OR LOWER(o.especialidad) LIKE LOWER(CONCAT('%',:q,'%')))
            """)
    Page<Odontologo> buscar(@Param("q") String q,
                            @Param("estado") String estado,
                            Pageable pageable);
}

