package com.gimnasio.gimnasioweb.client;

import com.gimnasio.gimnasioweb.dto.SocioDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class SocioClient {

    private final RestClient restClient;

    public SocioClient(@Value("${servicios.socio-url}") String socioUrl) {
        this.restClient = ClienteRest.crear(socioUrl, "socio-service");
    }

    public List<SocioDTO> listar() {
        return restClient.get()
                .uri("/api/socios")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public SocioDTO buscar(Long id) {
        return restClient.get()
                .uri("/api/socios/{id}", id)
                .retrieve()
                .body(SocioDTO.class);
    }

    public void registrar(SocioDTO socio) {
        restClient.post()
                .uri("/api/socios")
                .contentType(MediaType.APPLICATION_JSON)
                .body(socio)
                .retrieve()
                .toBodilessEntity();
    }

    public void actualizar(Long id, SocioDTO socio) {
        restClient.put()
                .uri("/api/socios/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(socio)
                .retrieve()
                .toBodilessEntity();
    }

    public void eliminar(Long id) {
        restClient.delete()
                .uri("/api/socios/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
