package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Venta;
import com.tecsup.laboratorio_03.service.VentaService;
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
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService vs;

    // Listar
    @GetMapping
    public List<Venta> listar() {
        return vs.listar();
    }

    // Guardar
    @PostMapping
    public ResponseEntity<Venta> guardar(@RequestBody Venta v) {
        return ResponseEntity.status(201).body(vs.guardar(v));
    }

    // Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Venta> obtener(@PathVariable Long id) {
        Venta v = vs.obtener(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(v);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Venta> actualizar(@PathVariable Long id, @RequestBody Venta v) {
        Venta existente = vs.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setFecha(v.getFecha());
        existente.setTotal(v.getTotal());
        existente.setIdCliente(v.getIdCliente());
        existente.setIdEmpleado(v.getIdEmpleado());

        return ResponseEntity.ok(vs.guardar(existente));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Venta v = vs.obtener(id);
        if (v == null) {
            return ResponseEntity.notFound().build();
        }
        vs.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}