package com.gimnasio.gimnasioweb.exception;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.client.RestClientResponseException;

@ControllerAdvice
public class WebExceptionHandler {

    @InitBinder
    public void limpiarTextos(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ExceptionHandler(ServicioNoDisponibleException.class)
    public String servicioNoDisponible(ServicioNoDisponibleException ex, Model model) {
        return mostrarAviso(model, 503, ex.getMessage());
    }

    @ExceptionHandler(RestClientResponseException.class)
    public String errorDeApi(RestClientResponseException ex, Model model) {
        return mostrarAviso(model, ex.getStatusCode().value(), ErroresApi.mensaje(ex));
    }

    private String mostrarAviso(Model model, int status, String mensaje) {
        model.addAttribute("status", status);
        model.addAttribute("mensaje", mensaje);
        return "aviso";
    }
}
