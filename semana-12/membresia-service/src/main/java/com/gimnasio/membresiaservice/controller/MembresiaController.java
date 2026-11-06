package com.gimnasio.membresiaservice.controller;

import com.gimnasio.membresiaservice.dto.MembresiaRequestDTO;
import com.gimnasio.membresiaservice.dto.MembresiaResponseDTO;
import com.gimnasio.membresiaservice.services.MembresiaService;
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
@RequestMapping("/api/membresias")
@RequiredArgsConstructor
public class MembresiaController {

    private final MembresiaService membresiaService;

    @GetMapping
    public List<MembresiaResponseDTO> listar() {
        return membresiaService.listar();
    }

    @GetMapping("/{id}")
    public MembresiaResponseDTO buscarPorId(@PathVariable Long id) {
        return membresiaService.buscarPorId(id);
    }

    @GetMapping("/socio/{idSocio}")
    public List<MembresiaResponseDTO> listarPorSocio(@PathVariable Long idSocio) {
        return membresiaService.listarPorSocio(idSocio);
    }

    @PostMapping
    public ResponseEntity<MembresiaResponseDTO> registrar(@Valid @RequestBody MembresiaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(membresiaService.registrar(dto));
    }

    @PutMapping("/{id}")
    public MembresiaResponseDTO actualizar(@PathVariable Long id, @Valid @RequestBody MembresiaRequestDTO dto) {
        return membresiaService.actualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        membresiaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
