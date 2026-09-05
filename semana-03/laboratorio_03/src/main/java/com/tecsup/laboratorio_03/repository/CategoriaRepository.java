package com.tecsup.laboratorio_03.repository;

import com.tecsup.laboratorio_03.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}