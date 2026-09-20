package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerfilRepository extends JpaRepository<Perfil, Long> {
}