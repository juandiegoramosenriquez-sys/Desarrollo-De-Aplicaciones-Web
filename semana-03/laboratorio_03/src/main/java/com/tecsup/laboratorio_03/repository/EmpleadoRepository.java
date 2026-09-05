package com.tecsup.laboratorio_03.repository;

import com.tecsup.laboratorio_03.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
}