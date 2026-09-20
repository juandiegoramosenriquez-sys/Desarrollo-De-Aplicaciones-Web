package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Proveedor;
import com.tecsup.farmacia.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService ps;

    @GetMapping
    public List<Proveedor> lista() {
        return ps.listar();
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Proveedor proveedor) {
        return ResponseEntity.status(201).body(ps.guardar(proveedor));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Proveedor> obtener(@PathVariable Long id) {
        Proveedor p = ps.obtener(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Proveedor> actualizar(@PathVariable Long id, @RequestBody Proveedor p) {
        Proveedor existente = ps.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setRuc(p.getRuc());
        existente.setRazonSocial(p.getRazonSocial());
        existente.setContacto(p.getContacto());
        existente.setTelefono(p.getTelefono());
        existente.setEmail(p.getEmail());
        existente.setDireccion(p.getDireccion());
        existente.setEstado(p.getEstado());
        return ResponseEntity.ok(ps.guardar(existente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Proveedor p = ps.obtener(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        ps.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}