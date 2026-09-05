package com.tecsup.laboratorio_03.repository;

import com.tecsup.laboratorio_03.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}