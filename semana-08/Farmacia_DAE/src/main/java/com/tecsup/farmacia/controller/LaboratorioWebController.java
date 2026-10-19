package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Laboratorio;
import com.tecsup.farmacia.repository.LaboratorioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/laboratorios")
public class LaboratorioWebController {

    @Autowired
    private LaboratorioRepository laboratorioRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("laboratorios", laboratorioRepository.findAll());
        return "laboratorios/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        model.addAttribute("laboratorio", new Laboratorio());
        return "laboratorios/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Laboratorio laboratorio) {
        if (laboratorio.getId() == null) {
            laboratorio.setEstado(true);
        }
        laboratorioRepository.save(laboratorio);
        return "redirect:/laboratorios";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Laboratorio laboratorio = laboratorioRepository.findById(id).orElseThrow();
        model.addAttribute("laboratorio", laboratorio);
        return "laboratorios/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Laboratorio laboratorio = laboratorioRepository.findById(id).orElseThrow();
        boolean nuevoEstado = (laboratorio.getEstado() == null || !laboratorio.getEstado());
        laboratorio.setEstado(nuevoEstado);
        laboratorioRepository.save(laboratorio);
        return "redirect:/laboratorios";
    }
}