package com.tecsup.laboratorio_01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class informacion01 {

    @GetMapping("/personas")
    public String verInfo(Model model) {

        model.addAttribute("nombre", "Juan Diego");
        model.addAttribute("apellido", "Ramos Enriquez");
        model.addAttribute("direccion", "Av. Los Ficus 123, Lima");
        model.addAttribute("correo", "juandiego@email.com");
        model.addAttribute("numero", "987654321");
        model.addAttribute("nacionalidad", "Peruana");

        return "personas"; // busca personas.html en templates/
    }
}