package com.tecsup.laboratorio_04.controllers;

import com.tecsup.laboratorio_04.models.Curso;
import com.tecsup.laboratorio_04.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    @Autowired
    private CursoService service;

    @GetMapping
    public List<Curso> listar() {
        return service.listar();
    }

    @PostMapping
    public Curso guardar(@RequestBody Curso curso) {
        return service.guardar(curso);
    }

    @GetMapping("/{id}")
    public Curso obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}