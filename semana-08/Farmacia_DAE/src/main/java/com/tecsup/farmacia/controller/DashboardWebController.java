package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.repository.LoteRepository;
import com.tecsup.farmacia.repository.MedicamentoRepository;
import com.tecsup.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;

@Controller
@RequestMapping("/dashboard")
public class DashboardWebController {

    @Autowired private MedicamentoRepository medicamentoRepository;
    @Autowired private LoteRepository loteRepository;
    @Autowired private UsuarioRepository usuarioRepository;

    @GetMapping
    public String index(Model model) {
        long totalMedicamentos = medicamentoRepository.count();

        long totalLotes = loteRepository.count();

        LocalDate fechaLimite = LocalDate.now().plusDays(30);
        long proximosVencer = loteRepository.findProximosAVencer(fechaLimite).size();

        long totalUsuarios = usuarioRepository.count();

        model.addAttribute("totalMedicamentos", totalMedicamentos);
        model.addAttribute("totalLotes", totalLotes);
        model.addAttribute("proximosVencer", proximosVencer);
        model.addAttribute("totalUsuarios", totalUsuarios);

        return "dashboard/index";
    }
}