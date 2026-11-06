package com.gimnasio.gimnasioweb.controller;

import com.gimnasio.gimnasioweb.client.SocioClient;
import com.gimnasio.gimnasioweb.dto.SocioDTO;
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
@RequestMapping("/socios")
@RequiredArgsConstructor
public class SocioWebController {

    private final SocioClient socioClient;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("socios", socioClient.listar());
        return "socios/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("socio", new SocioDTO());
        return "socios/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("socio", socioClient.buscar(id));
        return "socios/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("socio") SocioDTO socio, BindingResult result,
                          Model model, RedirectAttributes flash) {
        try {
            if (socio.getId() == null) {
                socioClient.registrar(socio);
                flash.addFlashAttribute("exito", "Socio registrado correctamente");
            } else {
                socioClient.actualizar(socio.getId(), socio);
                flash.addFlashAttribute("exito", "Socio actualizado correctamente");
            }
            return "redirect:/socios";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error", ErroresApi.pasarAlFormulario(ex, result));
            return "socios/formulario";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        socioClient.eliminar(id);
        flash.addFlashAttribute("exito", "Socio eliminado correctamente");
        return "redirect:/socios";
    }
}
