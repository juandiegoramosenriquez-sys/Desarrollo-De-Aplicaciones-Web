package com.tecsup.farmacia.controller;

import com.tecsup.farmacia.service.MedicamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoValidacionController {

    private final MedicamentoService medicamentoService;

    public MedicamentoValidacionController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping("/validar-codigo/{codigo}")
    public ResponseEntity<Map<String, Object>> validarCodigo(@PathVariable String codigo) {

        boolean existe = medicamentoService.codigoExiste(codigo);

        Map<String, Object> respuesta = new HashMap<>();

        if (existe) {
            respuesta.put("codigo", codigo);
            respuesta.put("disponible", false);
            respuesta.put("mensaje", "El código ya se encuentra registrado");
        } else {
            respuesta.put("codigo", codigo);
            respuesta.put("disponible", true);
            respuesta.put("mensaje", "El código está disponible");
        }

        return ResponseEntity.ok(respuesta);
    }
}