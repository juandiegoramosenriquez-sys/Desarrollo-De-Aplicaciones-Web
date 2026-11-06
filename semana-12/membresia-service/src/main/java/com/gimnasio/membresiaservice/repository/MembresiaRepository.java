package com.gimnasio.membresiaservice.repository;

import com.gimnasio.membresiaservice.model.Membresia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembresiaRepository extends JpaRepository<Membresia, Long> {

    List<Membresia> findByIdSocio(Long idSocio);
}
