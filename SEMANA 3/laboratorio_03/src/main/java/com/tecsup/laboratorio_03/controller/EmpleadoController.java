package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Empleado;
import com.tecsup.laboratorio_03.service.EmpleadoService;
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
@RequestMapping("/api/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService es;

    // Listar
    @GetMapping
    public List<Empleado> listar() {
        return es.listar();
    }

    // Guardar
    @PostMapping
    public ResponseEntity<Empleado> guardar(@RequestBody Empleado e) {
        return ResponseEntity.status(201).body(es.guardar(e));
    }

    // Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> obtener(@PathVariable Long id) {
        Empleado e = es.obtener(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(e);
    }

    // Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Empleado> actualizar(@PathVariable Long id, @RequestBody Empleado e) {
        Empleado existente = es.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(e.getNombre());
        existente.setApellido(e.getApellido());
        existente.setCargo(e.getCargo());
        existente.setTelefono(e.getTelefono());

        return ResponseEntity.ok(es.guardar(existente));
    }

    // Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Empleado e = es.obtener(id);
        if (e == null) {
            return ResponseEntity.notFound().build();
        }
        es.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}