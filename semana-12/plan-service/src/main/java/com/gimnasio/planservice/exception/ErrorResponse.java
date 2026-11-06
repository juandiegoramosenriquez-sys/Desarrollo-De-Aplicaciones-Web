package com.gimnasio.planservice.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse(
        LocalDateTime fecha,
        int status,
        String mensaje,
        String ruta,
        Map<String, String> errores
) {
}
