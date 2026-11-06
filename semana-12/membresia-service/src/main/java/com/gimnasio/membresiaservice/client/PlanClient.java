package com.gimnasio.membresiaservice.client;

import com.gimnasio.membresiaservice.dto.PlanDTO;
import com.gimnasio.membresiaservice.exception.RecursoNoEncontradoException;
import com.gimnasio.membresiaservice.exception.ServicioNoDisponibleException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Component
public class PlanClient {

    private final RestClient restClient;

    public PlanClient(@Value("${servicios.plan-url}") String planUrl) {
        this.restClient = RestClient.create(planUrl);
    }

    public PlanDTO buscarPlan(Long idPlan) {
        try {
            return restClient.get()
                    .uri("/api/planes/{id}", idPlan)
                    .retrieve()
                    .body(PlanDTO.class);
        } catch (HttpClientErrorException.NotFound ex) {
            throw new RecursoNoEncontradoException("No existe un plan con id " + idPlan);
        } catch (ResourceAccessException ex) {
            throw new ServicioNoDisponibleException("plan-service no esta disponible, verifique que este ejecutandose");
        }
    }
}
