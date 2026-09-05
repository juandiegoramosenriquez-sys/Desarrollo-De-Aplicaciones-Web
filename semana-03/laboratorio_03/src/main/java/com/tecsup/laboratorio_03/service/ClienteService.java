package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Cliente;
import com.tecsup.laboratorio_03.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository cr;

    // Listar
    public List<Cliente> listar() {
        return cr.findAll();
    }

    // Guardar
    public Cliente guardar(Cliente c) {
        return cr.save(c);
    }

    // Buscar
    public Cliente obtener(Long id) {
        return cr.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        cr.deleteById(id);
    }
}