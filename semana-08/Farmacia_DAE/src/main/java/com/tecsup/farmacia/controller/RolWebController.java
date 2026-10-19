package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Rol;
import com.tecsup.farmacia.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/roles")
public class RolWebController {

    @Autowired
    private RolRepository rolRepository;

    @GetMapping
    public String listarRoles(Model model) {
        model.addAttribute("roles", rolRepository.findAll());
        return "roles/list";
    }

    @GetMapping("/nuevo")
    public String nuevoRolForm(Model model) {
        model.addAttribute("rol", new Rol());
        return "roles/form";
    }

    @PostMapping("/guardar")
    public String guardarRol(@ModelAttribute Rol rol) {
        if (rol.getId() == null) {
            rol.setEstado(true);
        }
        rolRepository.save(rol);
        return "redirect:/roles";
    }

    @GetMapping("/editar/{id}")
    public String editarRolForm(@PathVariable Long id, Model model) {
        Rol rol = rolRepository.findById(id).orElseThrow();
        model.addAttribute("rol", rol);
        return "roles/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Rol rol = rolRepository.findById(id).orElseThrow();
        rol.setEstado(!rol.getEstado());
        rolRepository.save(rol);
        return "redirect:/roles";
    }
}