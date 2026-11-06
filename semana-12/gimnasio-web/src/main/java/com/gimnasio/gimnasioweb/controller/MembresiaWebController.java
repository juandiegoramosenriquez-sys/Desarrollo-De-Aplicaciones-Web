package com.gimnasio.gimnasioweb.controller;

import com.gimnasio.gimnasioweb.client.MembresiaClient;
import com.gimnasio.gimnasioweb.client.PlanClient;
import com.gimnasio.gimnasioweb.client.SocioClient;
import com.gimnasio.gimnasioweb.dto.MembresiaDTO;
import com.gimnasio.gimnasioweb.dto.PlanDTO;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/membresias")
@RequiredArgsConstructor
public class MembresiaWebController {

    private final MembresiaClient membresiaClient;
    private final SocioClient socioClient;
    private final PlanClient planClient;

    @GetMapping
    public String listar(@RequestParam(required = false) Long idSocio, Model model) {
        List<MembresiaDTO> membresias = idSocio == null
                ? membresiaClient.listar()
                : membresiaClient.listarPorSocio(idSocio);
        List<SocioDTO> socios = socioClient.listar();
        List<PlanDTO> planes = planClient.listar();

        model.addAttribute("membresias", membresias);
        model.addAttribute("socios", socios);
        model.addAttribute("idSocio", idSocio);
        model.addAttribute("nombresSocios", socios.stream()
                .collect(Collectors.toMap(SocioDTO::getId, SocioDTO::getNombreCompleto)));
        model.addAttribute("nombresPlanes", planes.stream()
                .collect(Collectors.toMap(PlanDTO::getId, PlanDTO::getNombre)));
        return "membresias/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(@RequestParam(required = false) Long idSocio, Model model) {
        MembresiaDTO membresia = new MembresiaDTO();
        membresia.setIdSocio(idSocio);
        model.addAttribute("membresia", membresia);
        cargarListas(model);
        return "membresias/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("membresia", membresiaClient.buscar(id));
        cargarListas(model);
        return "membresias/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("membresia") MembresiaDTO membresia, BindingResult result,
                          Model model, RedirectAttributes flash) {
        try {
            if (membresia.getId() == null) {
                membresiaClient.registrar(membresia);
                flash.addFlashAttribute("exito", "Membresia registrada correctamente");
            } else {
                membresiaClient.actualizar(membresia.getId(), membresia);
                flash.addFlashAttribute("exito", "Membresia actualizada correctamente");
            }
            return "redirect:/membresias";
        } catch (HttpClientErrorException ex) {
            model.addAttribute("error", ErroresApi.pasarAlFormulario(ex, result));
            cargarListas(model);
            return "membresias/formulario";
        }
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        membresiaClient.eliminar(id);
        flash.addFlashAttribute("exito", "Membresia eliminada correctamente");
        return "redirect:/membresias";
    }

    private void cargarListas(Model model) {
        model.addAttribute("socios", socioClient.listar());
        model.addAttribute("planes", planClient.listar());
    }
}
