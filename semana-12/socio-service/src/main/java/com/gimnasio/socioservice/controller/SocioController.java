package com.gimnasio.socioservice.controller;

import com.gimnasio.socioservice.dto.SocioRequestDTO;
import com.gimnasio.socioservice.dto.SocioResponseDTO;
import com.gimnasio.socioservice.services.SocioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/socios")
@RequiredArgsConstructor
public class SocioController {

    private final SocioService socioService;

    @GetMapping
    public List<SocioResponseDTO> listar() {
        return socioService.listar();
    }

    @GetMapping("/{id}")
    public SocioResponseDTO buscarPorId(@PathVariable Long id) {
        return socioService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<SocioResponseDTO> registrar(@Valid @RequestBody SocioRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(socioService.registrar(dto));
    }

    @PutMapping("/{id}")
    public SocioResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody SocioRequestDTO dto) {
        return socioService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        socioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
