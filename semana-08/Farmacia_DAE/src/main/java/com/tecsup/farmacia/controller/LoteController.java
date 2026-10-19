package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Lote;
import com.tecsup.farmacia.service.LoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lotes")
public class LoteController {

    private final LoteService loteService;

    public LoteController(LoteService loteService) {
        this.loteService = loteService;
    }

    // Listar
    @GetMapping
    public List<Lote> listar() {
        return loteService.listar();
    }

    // Buscar por id
    @GetMapping("/{id}")
    public ResponseEntity<Lote> obtener(@PathVariable Long id) {
        Lote lote = loteService.obtener(id);
        if (lote == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(lote);
    }

    // Lotes de un medicamento específico
    @GetMapping("/medicamento/{medicamentoId}")
    public List<Lote> listarPorMedicamento(@PathVariable Long medicamentoId) {
        return loteService.listarPorMedicamento(medicamentoId);
    }

    // Alerta de vencimiento (por defecto 30 días)
    @GetMapping("/proximos-a-vencer")
    public List<Lote> proximosAVencer(@RequestParam(defaultValue = "30") int dias) {
        return loteService.proximosAVencer(dias);
    }

    // RF-FAR-11 + RF-FAR-12: registrar lote con su vencimiento
    @PostMapping
    public ResponseEntity<Lote> registrar(@RequestBody Lote lote) {
        return ResponseEntity.status(201).body(loteService.registrar(lote));
    }

    // Modificar
    @PutMapping("/{id}")
    public ResponseEntity<Lote> modificar(@PathVariable Long id, @RequestBody Lote lote) {
        Lote actualizado = loteService.modificar(id, lote);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        loteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}





