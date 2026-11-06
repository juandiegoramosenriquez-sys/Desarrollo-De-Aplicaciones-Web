package com.gimnasio.membresiaservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PlanDTO(
        Long id,
        String nombre,
        String estado
) {
}
