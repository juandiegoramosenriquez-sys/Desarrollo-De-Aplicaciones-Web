package com.tecsup.laboratorio_04.service;

import com.tecsup.laboratorio_04.models.Estudiante;
import com.tecsup.laboratorio_04.models.Curso;
import com.tecsup.laboratorio_04.repository.EstudianteRepository;
import com.tecsup.laboratorio_04.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }

    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepo.save(estudiante);
    }

    public Estudiante obtener(Long id) {
        return estudianteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }

    public Estudiante agregarCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = obtener(estudianteId);
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar que no esté repetido
        if (!estudiante.getCursos().contains(curso)) {
            estudiante.getCursos().add(curso);
            estudianteRepo.save(estudiante);
        }

        return estudiante;
    }

    public Estudiante quitarCurso(Long estudianteId, Long cursoId) {
        Estudiante estudiante = obtener(estudianteId);
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        estudiante.getCursos().remove(curso);
        estudianteRepo.save(estudiante);

        return estudiante;
    }

    public List<Curso> listarCursosEstudiante(Long estudianteId) {
        Estudiante estudiante = obtener(estudianteId);
        return estudiante.getCursos();
    }

    public void eliminar(Long id) {
        estudianteRepo.deleteById(id);
    }
}