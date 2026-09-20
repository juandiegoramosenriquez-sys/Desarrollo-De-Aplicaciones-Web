package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}