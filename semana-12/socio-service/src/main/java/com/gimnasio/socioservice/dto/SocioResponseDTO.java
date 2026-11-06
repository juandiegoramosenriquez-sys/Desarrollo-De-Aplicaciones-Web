package com.gimnasio.socioservice.dto;

import com.gimnasio.socioservice.model.EstadoSocio;

public record SocioResponseDTO(
        Long id,
        String dni,
        String nombres,
        String apellidos,
        String correo,
        String telefono,
        EstadoSocio estado
) {
}
