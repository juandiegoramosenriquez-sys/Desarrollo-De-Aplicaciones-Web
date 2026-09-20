package com.tecsup.laboratorio_04.repository;

import com.tecsup.laboratorio_04.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}