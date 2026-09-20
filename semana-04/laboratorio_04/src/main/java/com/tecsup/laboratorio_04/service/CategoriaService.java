package com.tecsup.laboratorio_04.service;

import com.tecsup.laboratorio_04.models.Categoria;
import com.tecsup.laboratorio_04.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> listar() {
        return repo.findAll();
    }

    public Categoria guardar(Categoria categoria) {
        return repo.save(categoria);
    }

    public Categoria obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}