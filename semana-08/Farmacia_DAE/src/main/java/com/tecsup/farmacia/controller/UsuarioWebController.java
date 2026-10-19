package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.entity.Auditoria;
import com.tecsup.farmacia.entity.Rol;
import com.tecsup.farmacia.entity.Usuario;
import com.tecsup.farmacia.repository.AuditoriaRepository;
import com.tecsup.farmacia.repository.RolRepository;
import com.tecsup.farmacia.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @GetMapping
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioRepository.findAll());
        return "usuarios/list";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuarioForm(Model model) {
        Usuario usuario = new Usuario();
        usuario.setRol(new Rol());
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "usuarios/form";
    }

    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") Usuario usuario) {
        boolean esNuevo = (usuario.getId() == null);

        if (!esNuevo && (usuario.getPassword() == null || usuario.getPassword().trim().isEmpty())) {
            Usuario usuarioExistente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (usuarioExistente != null) {
                usuario.setPassword(usuarioExistente.getPassword());
            }
        } else {
            if (usuario.getPassword() != null && !usuario.getPassword().startsWith("{noop}")) {
                usuario.setPassword("{noop}" + usuario.getPassword());
            }
        }

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        try {
            String operacion = esNuevo ? "REGISTRO" : "MODIFICACION";
            auditoriaRepository.save(new Auditoria("ADMINISTRADOR", operacion, "Usuario", usuarioGuardado.getId()));
        } catch (Exception e) {
            System.err.println("Error guardando auditoría: " + e.getMessage());
        }

        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editarUsuarioForm(@PathVariable Long id, Model model) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        model.addAttribute("usuario", usuario);
        model.addAttribute("roles", rolRepository.findAll());
        return "usuarios/form";
    }

    @GetMapping("/cambiar-estado/{id}")
    public String cambiarEstado(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();

        boolean nuevoEstado = usuario.getEstado() == null || !usuario.getEstado();
        usuario.setEstado(nuevoEstado);
        usuarioRepository.save(usuario);

        try {
            Auditoria auditoria = new Auditoria("ADMINISTRADOR", "MODIFICACION_ESTADO", "Usuario", usuario.getId());
            auditoriaRepository.save(auditoria);
        } catch (Exception e) {
            System.err.println("Error guardando auditoría: " + e.getMessage());
        }

        return "redirect:/usuarios";
    }
}