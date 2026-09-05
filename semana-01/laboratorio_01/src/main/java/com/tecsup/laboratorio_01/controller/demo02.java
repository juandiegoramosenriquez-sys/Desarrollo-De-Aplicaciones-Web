package com.tecsup.laboratorio_01.controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class demo02 {

    @GetMapping("/info")
    public Map<String, Object> info(Model model){

        Map<String, Object> respuesta = new HashMap<>();
        respuesta.put("Nombres","Juan Diego Ramos Enriquez");
        return respuesta;
    }

}
