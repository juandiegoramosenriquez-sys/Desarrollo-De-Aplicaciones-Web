package com.gimnasio.planservice.dto;

import com.gimnasio.planservice.model.EstadoPlan;

import java.math.BigDecimal;

public record PlanResponseDTO(
        Long id,
        String nombre,
        String descripcion,
        BigDecimal precio,
        Integer duracionMeses,
        EstadoPlan estado
) {
}
