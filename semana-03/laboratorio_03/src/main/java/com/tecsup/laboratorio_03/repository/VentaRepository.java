package com.tecsup.laboratorio_03.repository;

import com.tecsup.laboratorio_03.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
}