package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.*;
import com.tecsup.farmacia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoWebController {

    @Autowired private MedicamentoRepository medicamentoRepository;
    @Autowired private CategoriaRepository categoriaRepository;
    @Autowired private LaboratorioRepository laboratorioRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        return "medicamentos/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Medicamento med = new Medicamento();
        med.setCategoria(new Categoria());
        med.setLaboratorio(new Laboratorio());
        med.setEstado(true); // Inicializado en activo

        model.addAttribute("medicamento", med);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("laboratorios", laboratorioRepository.findAll());
        return "medicamentos/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Medicamento medicamento) {
        medicamentoRepository.save(medicamento);
        return "redirect:/medicamentos";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow();
        model.addAttribute("medicamento", medicamento);
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("laboratorios", laboratorioRepository.findAll());
        return "medicamentos/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Medicamento medicamento = medicamentoRepository.findById(id).orElseThrow();
        medicamento.setEstado(!medicamento.isEstado());
        medicamentoRepository.save(medicamento);
        return "redirect:/medicamentos";
    }
}