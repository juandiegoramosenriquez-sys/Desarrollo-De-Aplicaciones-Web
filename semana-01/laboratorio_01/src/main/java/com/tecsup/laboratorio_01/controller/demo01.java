package com.tecsup.laboratorio_01.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class demo01 {
    @GetMapping("/informacion")
    public String Saludar(){
        return "nosotros";
    }
}
