package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Laboratorio;
import com.tecsup.farmacia.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LaboratorioService {

    @Autowired
    private LaboratorioRepository lr;

    public List<Laboratorio> listar() {
        return lr.findAll();
    }

    public Laboratorio guardar(Laboratorio l) {
        return lr.save(l);
    }

    public Laboratorio obtener(Long id) {
        return lr.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        lr.deleteById(id);
    }
}