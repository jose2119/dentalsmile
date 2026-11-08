package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Paciente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PacienteRepository extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findByTipoDocumentoAndNumeroDocumento(String tipoDocumento,
                                                             String numeroDocumento);

    @Query("""
            SELECT p FROM Paciente p
            WHERE (:estado IS NULL OR p.estado = :estado)
              AND (:q IS NULL OR LOWER(p.nombres)   LIKE LOWER(CONCAT('%',:q,'%'))
                             OR LOWER(p.apellidos)  LIKE LOWER(CONCAT('%',:q,'%'))
                             OR LOWER(p.correo)     LIKE LOWER(CONCAT('%',:q,'%'))
                             OR p.numeroDocumento   LIKE CONCAT('%',:q,'%'))
            """)
    Page<Paciente> buscar(@Param("q") String q,
                          @Param("estado") String estado,
                          Pageable pageable);
}

