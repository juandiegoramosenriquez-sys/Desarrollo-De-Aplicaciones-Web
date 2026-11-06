package com.gimnasio.gimnasioweb.exception;

import com.gimnasio.gimnasioweb.dto.ErrorResponse;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestClientResponseException;

public final class ErroresApi {

    private ErroresApi() {
    }

    public static String mensaje(RestClientResponseException ex) {
        ErrorResponse error = leer(ex);
        return error != null && error.mensaje() != null ? error.mensaje() : "No se pudo procesar la solicitud";
    }

    public static String pasarAlFormulario(RestClientResponseException ex, BindingResult result) {
        ErrorResponse error = leer(ex);
        if (error != null && error.errores() != null) {
            error.errores().forEach((campo, texto) -> result.rejectValue(campo, "api", texto));
        }
        return mensaje(ex);
    }

    private static ErrorResponse leer(RestClientResponseException ex) {
        try {
            return ex.getResponseBodyAs(ErrorResponse.class);
        } catch (RuntimeException e) {
            return null;
        }
    }
}
