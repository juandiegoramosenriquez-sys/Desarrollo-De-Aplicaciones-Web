package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}