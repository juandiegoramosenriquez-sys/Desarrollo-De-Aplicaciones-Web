package com.tecsup.laboratorio_04.controllers;

import com.tecsup.laboratorio_04.models.Categoria;
import com.tecsup.laboratorio_04.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

    @GetMapping
    public List<Categoria> listar() {
        return service.listar();
    }

    @PostMapping
    public Categoria guardar(@RequestBody Categoria categoria) {
        return service.guardar(categoria);
    }

    @GetMapping("/{id}")
    public Categoria obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}