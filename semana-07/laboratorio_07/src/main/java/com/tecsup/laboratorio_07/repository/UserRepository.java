package com.tecsup.laboratorio_07.repository;

import com.tecsup.laboratorio_07.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
