package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Categoria;
import com.tecsup.farmacia.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categorias")
public class CategoriaWebController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "categorias/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("categoria", new Categoria());
        return "categorias/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Categoria categoria) {
        if (categoria.getId() == null) {
            categoria.setEstado(true);
        }
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();
        model.addAttribute("categoria", categoria);
        return "categorias/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Categoria categoria = categoriaRepository.findById(id).orElseThrow();
        boolean nuevoEstado = (categoria.getEstado() == null || !categoria.getEstado());
        categoria.setEstado(nuevoEstado);
        categoriaRepository.save(categoria);
        return "redirect:/categorias";
    }
}