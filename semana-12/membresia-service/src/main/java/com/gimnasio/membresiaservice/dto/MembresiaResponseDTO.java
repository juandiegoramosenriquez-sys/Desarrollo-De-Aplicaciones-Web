package com.gimnasio.membresiaservice.dto;

import com.gimnasio.membresiaservice.model.EstadoMembresia;

import java.time.LocalDate;

public record MembresiaResponseDTO(
        Long id,
        Long idSocio,
        Long idPlan,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        EstadoMembresia estado
) {
}
