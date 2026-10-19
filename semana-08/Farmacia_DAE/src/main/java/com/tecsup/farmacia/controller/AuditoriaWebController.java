package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaWebController {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("auditorias", auditoriaRepository.findAll(Sort.by(Sort.Direction.DESC, "fechaHora")));
        return "auditoria/list";
    }
}