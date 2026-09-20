package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Laboratorio;
import com.tecsup.farmacia.service.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/laboratorios")
public class LaboratorioController {

    @Autowired
    private LaboratorioService ls;

    @GetMapping
    public List<Laboratorio> lista() {
        return ls.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Laboratorio laboratorio) {
        return ResponseEntity.status(201).body(ls.guardar(laboratorio));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Laboratorio> obtener(@PathVariable Long id) {
        Laboratorio l = ls.obtener(id);
        if (l == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(l);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratorio> actualizar(@PathVariable Long id, @RequestBody Laboratorio l) {
        Laboratorio existente = ls.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setCodigo(l.getCodigo());
        existente.setRazonSocial(l.getRazonSocial());
        existente.setRuc(l.getRuc());
        existente.setDireccion(l.getDireccion());
        existente.setTelefono(l.getTelefono());
        existente.setEmail(l.getEmail());
        existente.setEstado(l.getEstado());
        return ResponseEntity.ok(ls.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Laboratorio l = ls.obtener(id);
        if (l == null) {
            return ResponseEntity.notFound().build();
        }
        ls.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}