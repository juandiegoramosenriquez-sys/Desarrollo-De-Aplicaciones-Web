package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Presentacion;
import com.tecsup.farmacia.service.PresentacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/presentaciones")
public class PresentacionController {

    private final PresentacionService presentacionService;

    public PresentacionController(PresentacionService presentacionService) {
        this.presentacionService = presentacionService;
    }

    @GetMapping
    public List<Presentacion> listar() {
        return presentacionService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Presentacion> buscarPorId(@PathVariable Long id) {
        return presentacionService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Presentacion presentacion) {

        Presentacion nueva = presentacionService.guardar(presentacion);

        if (nueva == null) {
            return ResponseEntity.badRequest()
                    .body("El medicamento indicado no existe");
        }

        return ResponseEntity.ok(nueva);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Presentacion> actualizar(
            @PathVariable Long id,
            @RequestBody Presentacion presentacion) {

        Presentacion actualizada = presentacionService.actualizar(id, presentacion);

        if (actualizada != null) {
            return ResponseEntity.ok(actualizada);
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {

        boolean eliminado = presentacionService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}