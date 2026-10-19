package com.tecsup.farmacia.repository;

import com.tecsup.farmacia.entity.Lote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface LoteRepository extends JpaRepository<Lote, Long> {

    List<Lote> findByMedicamentoId(Long medicamentoId);

    // RF-FAR-12 / alertas: lotes que vencen antes de una fecha límite
    @Query("SELECT l FROM Lote l WHERE l.fechaVencimiento <= :fechaLimite ORDER BY l.fechaVencimiento ASC")
    List<Lote> findProximosAVencer(LocalDate fechaLimite);

    boolean existsByNumeroLote(String numeroLote);
}