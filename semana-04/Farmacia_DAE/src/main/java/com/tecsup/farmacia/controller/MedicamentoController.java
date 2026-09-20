package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Medicamento;
import com.tecsup.farmacia.service.MedicamentoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    private final MedicamentoService medicamentoService;

    public MedicamentoController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public ResponseEntity<List<Medicamento>> listar() {
        return ResponseEntity.ok(medicamentoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> obtener(@PathVariable Long id) {
        return medicamentoService.obtener(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody Medicamento medicamento) {
        try {
            Medicamento creado = medicamentoService.registrar(medicamento);
            return ResponseEntity.status(HttpStatus.CREATED).body(creado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> modificar(@PathVariable Long id, @RequestBody Medicamento medicamento) {
        try {
            return ResponseEntity.ok(medicamentoService.modificar(id, medicamento));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, Boolean> body) {
        Boolean estado = body.get("estado");
        if (estado == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Debe indicar el campo 'estado' (true/false)"));
        }
        try {
            return ResponseEntity.ok(medicamentoService.cambiarEstado(id, estado));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}