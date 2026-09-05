package com.tecsup.laboratorio_03.repository;

import com.tecsup.laboratorio_03.model.Detalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleRepository extends JpaRepository<Detalle, Long> {
}