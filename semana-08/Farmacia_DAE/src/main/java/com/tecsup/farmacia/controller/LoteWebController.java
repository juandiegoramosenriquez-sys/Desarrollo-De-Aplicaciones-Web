package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.*;
import com.tecsup.farmacia.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lotes")
public class LoteWebController {

    @Autowired private LoteRepository loteRepository;
    @Autowired private MedicamentoRepository medicamentoRepository;
    @Autowired private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("lotes", loteRepository.findAll());
        return "lotes/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Lote lote = new Lote();
        lote.setMedicamento(new Medicamento());

        model.addAttribute("lote", lote);
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "lotes/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Lote lote) {
        loteRepository.save(lote);
        return "redirect:/lotes";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Lote lote = loteRepository.findById(id).orElseThrow();
        if (lote.getMedicamento() == null) lote.setMedicamento(new Medicamento());

        model.addAttribute("lote", lote);
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "lotes/form";
    }

    @GetMapping("/desactivar/{id}")
    public String desactivar(@PathVariable Long id) {
        loteRepository.deleteById(id); // O cambiar estado si tienes atributo 'estado'
        return "redirect:/lotes";
    }
}