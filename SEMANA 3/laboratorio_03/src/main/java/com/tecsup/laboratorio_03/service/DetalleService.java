package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Detalle;
import com.tecsup.laboratorio_03.repository.DetalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetalleService {

    @Autowired
    private DetalleRepository dr;

    // Listar
    public List<Detalle> listar() {
        return dr.findAll();
    }

    // Guardar
    public Detalle guardar(Detalle d) {
        return dr.save(d);
    }

    // Buscar
    public Detalle obtener(Long id) {
        return dr.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        dr.deleteById(id);
    }
}