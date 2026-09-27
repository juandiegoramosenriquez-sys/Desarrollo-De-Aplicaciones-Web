package com.tecsup.controller;

import java.util.List;

import com.tecsup.dto.ProductoDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tecsup.model.Producto;
import com.tecsup.service.ProductoService;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService service;

    @GetMapping
    public ResponseEntity<List<Producto>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscarPorNombre(@RequestParam String nombre) {

        List<Producto> resultado = service.buscarPorNombre(nombre);

        if (resultado.isEmpty()) {
            return ResponseEntity.status(404).body("No se encontraron productos con ese nombre");
        }

        return ResponseEntity.ok(resultado);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@Valid @RequestBody ProductoDTO dto) {

        Producto p = new Producto();
        p.setNombre(dto.getNombre());
        p.setPrecio(dto.getPrecio());
        p.setStock(dto.getStock());
        p.setCategoria(dto.getCategoria());

        Producto guardado = service.guardar(p);

        return ResponseEntity.status(201).body(guardado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {

        Producto p = service.obtener(id);

        if (p == null) {
            return ResponseEntity.status(404).body("Producto no encontrado");
        }

        return ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @Valid @RequestBody ProductoDTO dto) {

        Producto datos = new Producto();
        datos.setNombre(dto.getNombre());
        datos.setPrecio(dto.getPrecio());
        datos.setStock(dto.getStock());
        datos.setCategoria(dto.getCategoria());

        try {
            return ResponseEntity.ok(service.actualizar(id, datos));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {

        Producto p = service.obtener(id);

        if (p == null) {
            return ResponseEntity.status(404).body("No existe");
        }

        service.eliminar(id);

        return ResponseEntity.ok("Eliminado correctamente");
    }
}