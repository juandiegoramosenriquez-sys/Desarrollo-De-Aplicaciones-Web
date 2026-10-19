package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Medicamento;
import com.tecsup.farmacia.entity.Presentacion;
import com.tecsup.farmacia.repository.MedicamentoRepository;
import com.tecsup.farmacia.repository.PresentacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/presentaciones")
public class PresentacionWebController {

    @Autowired
    private PresentacionRepository presentacionRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("presentaciones", presentacionRepository.findAll());
        return "presentaciones/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Presentacion presentacion = new Presentacion();
        presentacion.setMedicamento(new Medicamento());
        presentacion.setEstado(true);

        model.addAttribute("presentacion", presentacion);
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        return "presentaciones/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Presentacion presentacion) {
        presentacionRepository.save(presentacion);
        return "redirect:/presentaciones";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Presentacion presentacion = presentacionRepository.findById(id).orElseThrow();
        model.addAttribute("presentacion", presentacion);
        model.addAttribute("medicamentos", medicamentoRepository.findAll());
        return "presentaciones/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Presentacion presentacion = presentacionRepository.findById(id).orElseThrow();
        presentacion.setEstado(!presentacion.isEstado());
        presentacionRepository.save(presentacion);
        return "redirect:/presentaciones";
    }
}