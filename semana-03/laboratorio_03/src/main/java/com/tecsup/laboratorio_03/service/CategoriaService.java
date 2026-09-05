package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Categoria;
import com.tecsup.laboratorio_03.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository cr;

    // Listar
    public List<Categoria> listar() {
        return cr.findAll();
    }

    // Guardar
    public Categoria guardar(Categoria c) {
        return cr.save(c);
    }

    // Buscar
    public Categoria obtener(Long id) {
        return cr.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        cr.deleteById(id);
    }
}