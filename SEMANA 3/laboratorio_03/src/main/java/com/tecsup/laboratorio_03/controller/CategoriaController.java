package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Categoria;
import com.tecsup.laboratorio_03.service.CategoriaService;
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
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService cs;

    // Listar
    @GetMapping
    public List<Categoria> listar() {
        return cs.listar();
    }

    // Guardar
    @PostMapping
    public ResponseEntity<Categoria> guardar(@RequestBody Categoria c) {
        return ResponseEntity.status(201).body(cs.guardar(c));
    }

    // Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        Categoria c = cs.obtener(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria c) {
        Categoria existente = cs.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(c.getNombre());
        existente.setDescripcion(c.getDescripcion());

        return ResponseEntity.ok(cs.guardar(existente));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Categoria c = cs.obtener(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        cs.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}