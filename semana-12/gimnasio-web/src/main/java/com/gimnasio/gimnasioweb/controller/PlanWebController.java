package com.gimnasio.gimnasioweb.controller;

import com.gimnasio.gimnasioweb.client.PlanClient;
import com.gimnasio.gimnasioweb.dto.PlanDTO;
import com.gimnasio.gimnasioweb.exception.ErroresApi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/planes")
@RequiredArgsConstructor
public class PlanWebController {

    private final PlanClient planClient;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("planes", planClient.listar());
        return "planes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("plan", new PlanDTO());
        return "planes/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("plan", planClient.buscar(id));
        return "planes/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("plan") PlanDTO plan, BindingResult result,
                          Model model, RedirectAttributes flash) {
        try {
            if (plan.getId() == null) {
                planClient.registrar(plan);
                flash.addFlashAttribute("exito", "Plan registrado correctamente");
            } else {
                planClient.actualizar(plan.getId(), plan);
                flash.addFlashAttribute("exito", "Plan actualizado correctamente");
            }
            return "redirect:/planes";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error", ErroresApi.pasarAlFormulario(ex, result));
            return "planes/formulario";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        planClient.eliminar(id);
        flash.addFlashAttribute("exito", "Plan eliminado correctamente");
        return "redirect:/planes";
    }
}
