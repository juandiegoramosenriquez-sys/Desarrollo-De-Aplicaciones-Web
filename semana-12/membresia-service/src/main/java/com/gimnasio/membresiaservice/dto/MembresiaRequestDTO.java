package com.gimnasio.membresiaservice.dto;

import com.gimnasio.membresiaservice.model.EstadoMembresia;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record MembresiaRequestDTO(

        @NotNull(message = "El idSocio es obligatorio")
        @Positive(message = "El idSocio debe ser mayor a 0")
        Long idSocio,

        @NotNull(message = "El idPlan es obligatorio")
        @Positive(message = "El idPlan debe ser mayor a 0")
        Long idPlan,

        @NotNull(message = "La fecha de inicio es obligatoria (yyyy-MM-dd)")
        LocalDate fechaInicio,

        @NotNull(message = "La fecha de fin es obligatoria (yyyy-MM-dd)")
        LocalDate fechaFin,

        @NotNull(message = "El estado es obligatorio: ACTIVA, VENCIDA o CANCELADA")
        EstadoMembresia estado
) {
}
