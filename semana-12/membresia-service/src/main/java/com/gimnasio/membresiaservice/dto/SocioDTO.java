package com.gimnasio.membresiaservice.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SocioDTO(
        Long id,
        String nombres,
        String apellidos,
        String estado
) {
}
