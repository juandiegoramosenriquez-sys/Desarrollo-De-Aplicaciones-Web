package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}