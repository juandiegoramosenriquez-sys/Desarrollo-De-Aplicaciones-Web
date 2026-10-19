package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Proveedor;
import com.tecsup.farmacia.repository.ProveedorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/proveedores")
public class ProveedorWebController {

    @Autowired
    private ProveedorRepository proveedorRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorRepository.findAll());
        return "proveedores/list";
    }

    @GetMapping("/nuevo")
    public String nuevoForm(Model model) {
        Proveedor proveedor = new Proveedor();
        proveedor.setEstado(true);
        model.addAttribute("proveedor", proveedor);
        return "proveedores/form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Proveedor proveedor) {
        if (proveedor.getId() == null && proveedor.getEstado() == null) {
            proveedor.setEstado(true);
        }
        proveedorRepository.save(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/editar/{id}")
    public String editarForm(@PathVariable Long id, Model model) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        model.addAttribute("proveedor", proveedor);
        return "proveedores/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Proveedor proveedor = proveedorRepository.findById(id).orElseThrow();
        boolean nuevoEstado = (proveedor.getEstado() == null || !proveedor.getEstado());
        proveedor.setEstado(nuevoEstado);
        proveedorRepository.save(proveedor);
        return "redirect:/proveedores";
    }
}