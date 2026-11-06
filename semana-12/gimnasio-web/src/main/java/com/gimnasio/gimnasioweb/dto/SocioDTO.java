package com.gimnasio.gimnasioweb.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocioDTO {

    private Long id;
    private String dni;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String estado = "ACTIVO";

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }
}
