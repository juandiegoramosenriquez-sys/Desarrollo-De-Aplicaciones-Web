package com.gimnasio.planservice.controller;

import com.gimnasio.planservice.dto.PlanRequestDTO;
import com.gimnasio.planservice.dto.PlanResponseDTO;
import com.gimnasio.planservice.services.PlanService;
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
@RequestMapping("/api/planes")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @GetMapping
    public List<PlanResponseDTO> listar() {
        return planService.listar();
    }

    @GetMapping("/{id}")
    public PlanResponseDTO buscarPorId(@PathVariable Long id) {
        return planService.buscarPorId(id);
    }

    @PostMapping
    public ResponseEntity<PlanResponseDTO> registrar(@Valid @RequestBody PlanRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(planService.registrar(dto));
    }

    @PutMapping("/{id}")
    public PlanResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody PlanRequestDTO dto) {
        return planService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        planService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
