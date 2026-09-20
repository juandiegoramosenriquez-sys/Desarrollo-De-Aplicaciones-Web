package com.tecsup.laboratorio_04.service;

import com.tecsup.laboratorio_04.models.Curso;
import com.tecsup.laboratorio_04.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CursoService {

    @Autowired
    private CursoRepository repo;

    public List<Curso> listar() {
        return repo.findAll();
    }

    public Curso guardar(Curso curso) {
        return repo.save(curso);
    }

    public Curso obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}