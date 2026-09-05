package com.tecsup.laboratorio_03.controller;

import com.tecsup.laboratorio_03.model.Producto;
import com.tecsup.laboratorio_03.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    @Autowired
    private ProductoService pr;


    //Listas
    @GetMapping
    public List<Producto> listar() {
        return pr.listar();
    }

    //Guardar
    @PostMapping
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto) {
        return ResponseEntity.status(201).body(pr.guardar(producto));
    }


    //Buscar
    @GetMapping("/{id}")
    public ResponseEntity<Producto> obtener(@PathVariable Long id) {
        Producto p = pr.obtener(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(p);
    }

    //Actualizar
    @PutMapping("/{id}")
    public ResponseEntity<Producto> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        Producto existente = pr.obtener(id);
        if (existente == null) {
            return ResponseEntity.notFound().build();
        }
        existente.setNombre(p.getNombre());
        existente.setDescripcion(p.getDescripcion());
        existente.setPrecio(p.getPrecio());
        existente.setStock(p.getStock());
        existente.setIdCategoria(p.getIdCategoria());

        return ResponseEntity.ok(pr.guardar(existente));
    }

    //Eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        Producto p = pr.obtener(id);
        if (p == null) {
            return ResponseEntity.notFound().build();
        }
        pr.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}

