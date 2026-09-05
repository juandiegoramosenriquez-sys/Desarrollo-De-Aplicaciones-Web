package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Detalle;
import com.tecsup.laboratorio_03.service.DetalleService;
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
@RequestMapping("/api/detalles")
public class DetalleController {

    @Autowired
    private DetalleService ds;

    // Listar
    @GetMapping
    public List<Detalle> listar() {
        return ds.listar();
    }

    // Guardar
    @PostMapping
    public ResponseEntity<Detalle> guardar(@RequestBody Detalle d) {
        return ResponseEntity.status(201).body(ds.guardar(d));
    }

    // Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Detalle> obtener(@PathVariable Long id) {
        Detalle d = ds.obtener(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(d);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Detalle> actualizar(@PathVariable Long id, @RequestBody Detalle d) {
        Detalle existente = ds.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setIdVenta(d.getIdVenta());
        existente.setIdProducto(d.getIdProducto());
        existente.setCantidad(d.getCantidad());
        existente.setPrecio(d.getPrecio());
        existente.setSubtotal(d.getSubtotal());

        return ResponseEntity.ok(ds.guardar(existente));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Detalle d = ds.obtener(id);
        if (d == null) {
            return ResponseEntity.notFound().build();
        }
        ds.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}