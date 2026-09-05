package com.tecsup.service;

import com.tecsup.model.Usuario;
import com.tecsup.repository.IUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {
    @Autowired
    private IUsuarioRepository ur;

    public List<Usuario> listarUsuario(){
        return ur.findAll();
    }

    public Usuario Guardar(Usuario usu){
        return ur.save(usu);
    }
 }
