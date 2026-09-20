package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Categoria;
import com.tecsup.farmacia.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService cs;

    @GetMapping
    public List<Categoria> lista() {
        return cs.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Categoria categoria) {
        return ResponseEntity.status(201).body(cs.guardar(categoria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtener(@PathVariable Long id) {
        Categoria c = cs.obtener(id);
        if (c == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizar(@PathVariable Long id, @RequestBody Categoria c) {
        Categoria existente = cs.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(c.getNombre());
        existente.setDescripcion(c.getDescripcion());
        existente.setEstado(c.getEstado());
        return ResponseEntity.ok(cs.guardar(existente));
    }

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