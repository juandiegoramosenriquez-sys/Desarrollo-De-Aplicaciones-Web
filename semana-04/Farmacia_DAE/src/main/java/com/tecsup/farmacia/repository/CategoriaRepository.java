package com.tecsup.farmacia.repository;

import com.tecsup.farmacia.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}