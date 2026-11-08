package com.dentalsmile.dentalsmile.repository;

import com.dentalsmile.dentalsmile.entity.Cita;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    @Query("""
            SELECT c FROM Cita c
            WHERE c.odontologo.odontologoId = :odontologoId
              AND c.estado NOT IN ('Cancelada','NoAsistio')
              AND c.inicio < :fin
              AND c.fin    > :inicio
            """)
    List<Cita> findSolapadas(@Param("odontologoId") Integer odontologoId,
                             @Param("inicio") LocalDateTime inicio,
                             @Param("fin") LocalDateTime fin);

    @Query("""
            SELECT c FROM Cita c
            WHERE (:pacienteId   IS NULL OR c.paciente.pacienteId   = :pacienteId)
              AND (:odontologoId IS NULL OR c.odontologo.odontologoId = :odontologoId)
              AND (:estado       IS NULL OR c.estado = :estado)
            ORDER BY c.inicio DESC
            """)
    Page<Cita> buscar(@Param("pacienteId")   Integer pacienteId,
                      @Param("odontologoId") Integer odontologoId,
                      @Param("estado")       String  estado,
                      Pageable pageable);
}

