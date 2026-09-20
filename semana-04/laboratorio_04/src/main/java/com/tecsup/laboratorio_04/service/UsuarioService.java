package com.tecsup.laboratorio_04.service;

import com.tecsup.laboratorio_04.models.Usuario;
import com.tecsup.laboratorio_04.models.Perfil;
import com.tecsup.laboratorio_04.repository.UsuarioRepository;
import com.tecsup.laboratorio_04.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @Autowired
    private PerfilRepository perfilRepo;

    public List<Usuario> listar() {
        return usuarioRepo.findAll();
    }

    public Usuario guardar(Usuario usuario) {
        Usuario usuarioGuardado = usuarioRepo.save(usuario);

        if (usuario.getPerfil() != null) {
            usuario.getPerfil().setUsuario(usuarioGuardado);
            perfilRepo.save(usuario.getPerfil());
        }

        return usuarioGuardado;
    }

    public Usuario obtener(Long id) {
        return usuarioRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public void eliminar(Long id) {
        usuarioRepo.deleteById(id);
    }
}