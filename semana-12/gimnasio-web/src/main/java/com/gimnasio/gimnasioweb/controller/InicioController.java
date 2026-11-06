package com.gimnasio.gimnasioweb.controller;

import com.gimnasio.gimnasioweb.client.MembresiaClient;
import com.gimnasio.gimnasioweb.client.PlanClient;
import com.gimnasio.gimnasioweb.client.SocioClient;
import com.gimnasio.gimnasioweb.exception.ServicioNoDisponibleException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.function.Supplier;

@Controller
@RequiredArgsConstructor
public class InicioController {

    private final SocioClient socioClient;
    private final PlanClient planClient;
    private final MembresiaClient membresiaClient;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("totalSocios", contar(socioClient::listar));
        model.addAttribute("totalPlanes", contar(planClient::listar));
        model.addAttribute("totalMembresias", contar(membresiaClient::listar));
        return "inicio";
    }

    private Integer contar(Supplier<List<?>> consulta) {
        try {
            return consulta.get().size();
        } catch (ServicioNoDisponibleException ex) {
            return null;
        }
    }
}
