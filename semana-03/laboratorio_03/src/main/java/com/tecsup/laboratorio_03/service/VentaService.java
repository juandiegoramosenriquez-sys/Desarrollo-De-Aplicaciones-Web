package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Venta;
import com.tecsup.laboratorio_03.repository.VentaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VentaService {

    @Autowired
    private VentaRepository vr;

    // Listar
    public List<Venta> listar() {
        return vr.findAll();
    }

    // Guardar
    public Venta guardar(Venta v) {
        return vr.save(v);
    }

    // Buscar
    public Venta obtener(Long id) {
        return vr.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        vr.deleteById(id);
    }
}