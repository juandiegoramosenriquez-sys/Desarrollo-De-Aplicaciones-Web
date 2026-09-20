package com.tecsup.laboratorio_04.controllers;

import com.tecsup.laboratorio_04.models.Estudiante;
import com.tecsup.laboratorio_04.models.Curso;
import com.tecsup.laboratorio_04.service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {

    @Autowired
    private EstudianteService service;

    @GetMapping
    public List<Estudiante> listar() {
        return service.listar();
    }

    @PostMapping
    public Estudiante guardar(@RequestBody Estudiante estudiante) {
        return service.guardar(estudiante);
    }

    @GetMapping("/{id}")
    public Estudiante obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante agregarCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return service.agregarCurso(estudianteId, cursoId);
    }

    @DeleteMapping("/{estudianteId}/cursos/{cursoId}")
    public Estudiante quitarCurso(@PathVariable Long estudianteId, @PathVariable Long cursoId) {
        return service.quitarCurso(estudianteId, cursoId);
    }

    @GetMapping("/{id}/cursos")
    public List<Curso> listarCursos(@PathVariable Long id) {
        return service.listarCursosEstudiante(id);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}