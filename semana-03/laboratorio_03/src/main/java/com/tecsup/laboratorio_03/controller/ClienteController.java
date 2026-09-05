package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Cliente;
import com.tecsup.laboratorio_03.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteService cs;

    // Listar
    @GetMapping
    public List<Cliente> listar() {
        return cs.listar();
    }

    // Guardar
    @PostMapping
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente c) {
        return ResponseEntity.status(201).body(cs.guardar(c));
    }

    // Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Cliente> obtener(@PathVariable Long id) {
        Cliente c = cs.obtener(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> actualizar(@PathVariable Long id, @RequestBody Cliente c) {
        Cliente existente = cs.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(c.getNombre());
        existente.setApellido(c.getApellido());
        existente.setTelefono(c.getTelefono());
        existente.setEmail(c.getEmail());

        return ResponseEntity.ok(cs.guardar(existente));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Cliente c = cs.obtener(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        cs.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}