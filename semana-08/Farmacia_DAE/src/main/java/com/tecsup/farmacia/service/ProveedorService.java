package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Proveedor;
import com.tecsup.farmacia.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProveedorService {

    @Autowired
    private ProveedorRepository pr;

    public List<Proveedor> listar() {
        return pr.findAll();
    }

    public Proveedor guardar(Proveedor p) {
        return pr.save(p);
    }

    public Proveedor obtener(Long id) {
        return pr.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        pr.deleteById(id);
    }
}