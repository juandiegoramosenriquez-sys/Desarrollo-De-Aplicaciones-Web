package com.tecsup.farmacia.repository;

import com.tecsup.farmacia.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    boolean existsByCodigo(String codigo);
}