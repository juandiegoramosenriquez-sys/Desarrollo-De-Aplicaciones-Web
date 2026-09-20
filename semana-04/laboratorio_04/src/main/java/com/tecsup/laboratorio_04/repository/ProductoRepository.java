package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoriaId(Long categoriaId);
}