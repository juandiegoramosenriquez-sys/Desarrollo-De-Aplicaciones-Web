package com.gimnasio.gimnasioweb.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorResponse(
        int status,
        String mensaje,
        Map<String, String> errores
) {
}
