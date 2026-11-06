package com.gimnasio.gimnasioweb.client;

import com.gimnasio.gimnasioweb.dto.PlanDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class PlanClient {

    private final RestClient restClient;

    public PlanClient(@Value("${servicios.plan-url}") String planUrl) {
        this.restClient = ClienteRest.crear(planUrl, "plan-service");
    }

    public List<PlanDTO> listar() {
        return restClient.get()
                .uri("/api/planes")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public PlanDTO buscar(Long id) {
        return restClient.get()
                .uri("/api/planes/{id}", id)
                .retrieve()
                .body(PlanDTO.class);
    }

    public void registrar(PlanDTO plan) {
        restClient.post()
                .uri("/api/planes")
                .contentType(MediaType.APPLICATION_JSON)
                .body(plan)
                .retrieve()
                .toBodilessEntity();
    }

    public void actualizar(Long id, PlanDTO plan) {
        restClient.put()
                .uri("/api/planes/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(plan)
                .retrieve()
                .toBodilessEntity();
    }

    public void eliminar(Long id) {
        restClient.delete()
                .uri("/api/planes/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
