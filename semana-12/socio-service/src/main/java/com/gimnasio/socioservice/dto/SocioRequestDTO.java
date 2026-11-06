package com.gimnasio.socioservice.dto;

import com.gimnasio.socioservice.model.EstadoSocio;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SocioRequestDTO(

        @NotBlank(message = "El DNI es obligatorio")
        @Pattern(regexp = "\\d{8}", message = "El DNI debe tener 8 digitos")
        String dni,

        @NotBlank(message = "Los nombres son obligatorios")
        @Size(max = 80, message = "Los nombres no deben superar 80 caracteres")
        String nombres,

        @NotBlank(message = "Los apellidos son obligatorios")
        @Size(max = 80, message = "Los apellidos no deben superar 80 caracteres")
        String apellidos,

        @NotBlank(message = "El correo es obligatorio")
        @Email(message = "El correo no tiene un formato valido")
        String correo,

        @Pattern(regexp = "9\\d{8}", message = "El telefono debe tener 9 digitos y empezar con 9")
        String telefono,

        @NotNull(message = "El estado es obligatorio: ACTIVO o INACTIVO")
        EstadoSocio estado
) {
}
