package com.tecsup.laboratorio_04.service;

import com.tecsup.laboratorio_04.models.Perfil;
import com.tecsup.laboratorio_04.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository repo;

    public List<Perfil> listar() {
        return repo.findAll();
    }

    public Perfil guardar(Perfil perfil) {
        return repo.save(perfil);
    }

    public Perfil obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Perfil no encontrado"));
    }

    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}