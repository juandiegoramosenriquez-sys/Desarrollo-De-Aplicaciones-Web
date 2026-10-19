package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Categoria;
import com.tecsup.farmacia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository cr;

    public List<Categoria> listar() {
        return cr.findAll();
    }

    public Categoria guardar(Categoria c) {
        return cr.save(c);
    }

    public Categoria obtener(Long id) {
        return cr.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        cr.deleteById(id);
    }
}