package com.gimnasio.membresiaservice.client;

import com.gimnasio.membresiaservice.dto.SocioDTO;
import com.gimnasio.membresiaservice.exception.RecursoNoEncontradoException;
import com.gimnasio.membresiaservice.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class SocioClient {

    private final RestClient restClient;

    public SocioClient(@Value("${servicios.socio-url}") String socioUrl) {
        this.restClient = RestClient.create(socioUrl);
    }

    public SocioDTO buscarSocio(Long idSocio) {
        try {
            return restClient.get()
                    .uri("/api/socios/{id}", idSocio)
                    .retrieve()
                    .body(SocioDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNoEncontradoException("No existe un socio con id " + idSocio);
        } catch (ResourceAccessException ex) {
            throw new ServicioNoDisponibleException("socio-service no esta disponible, verifique que este ejecutandose");
        }
    }
}
