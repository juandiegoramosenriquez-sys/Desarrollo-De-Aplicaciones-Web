package com.tecsup.laboratorio_01.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class informacion02 {

    @GetMapping("/info")
    public Map<String, Object> info(Model model)  {

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Nombre", "Juan Diego");
        respuesta.put("Apellido", "Ramos Enriquez");
        respuesta.put("Direccion", "Av. Los Ficus 123, Lima");
        respuesta.put("Correo", "juandiego@email.com");
        respuesta.put("Numero", "987654321");
        respuesta.put("Nacionalidad", "Peruana");

        return respuesta;
    }
}