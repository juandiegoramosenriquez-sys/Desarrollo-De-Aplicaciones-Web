package com.gimnasio.gimnasioweb.client;

import com.gimnasio.gimnasioweb.dto.MembresiaDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
public class MembresiaClient {

    private final RestClient restClient;

    public MembresiaClient(@Value("${servicios.membresia-url}") String membresiaUrl) {
        this.restClient = ClienteRest.crear(membresiaUrl, "membresia-service");
    }

    public List<MembresiaDTO> listar() {
        return restClient.get()
                .uri("/api/membresias")
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public List<MembresiaDTO> listarPorSocio(Long idSocio) {
        return restClient.get()
                .uri("/api/membresias/socio/{idSocio}", idSocio)
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
    }

    public MembresiaDTO buscar(Long id) {
        return restClient.get()
                .uri("/api/membresias/{id}", id)
                .retrieve()
                .body(MembresiaDTO.class);
    }

    public void registrar(MembresiaDTO membresia) {
        restClient.post()
                .uri("/api/membresias")
                .contentType(MediaType.APPLICATION_JSON)
                .body(membresia)
                .retrieve()
                .toBodilessEntity();
    }

    public void actualizar(Long id, MembresiaDTO membresia) {
        restClient.put()
                .uri("/api/membresias/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(membresia)
                .retrieve()
                .toBodilessEntity();
    }

    public void eliminar(Long id) {
        restClient.delete()
                .uri("/api/membresias/{id}", id)
                .retrieve()
                .toBodilessEntity();
    }
}
