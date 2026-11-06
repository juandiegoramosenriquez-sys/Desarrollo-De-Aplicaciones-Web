package com.gimnasio.planservice.dto;

import com.gimnasio.planservice.model.EstadoPlan;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record PlanRequestDTO(

        @NotBlank(message = "El nombre del plan es obligatorio")
        @Size(max = 60, message = "El nombre no debe superar 60 caracteres")
        String nombre,

        @Size(max = 255, message = "La descripcion no debe superar 255 caracteres")
        String descripcion,

        @NotNull(message = "El precio es obligatorio")
        @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
        @Digits(integer = 8, fraction = 2, message = "El precio debe tener maximo 2 decimales")
        BigDecimal precio,

        @NotNull(message = "La duracion en meses es obligatoria")
        @Min(value = 1, message = "La duracion minima es 1 mes")
        @Max(value = 24, message = "La duracion maxima es 24 meses")
        Integer duracionMeses,

        @NotNull(message = "El estado es obligatorio: ACTIVO o INACTIVO")
        EstadoPlan estado
) {
}
