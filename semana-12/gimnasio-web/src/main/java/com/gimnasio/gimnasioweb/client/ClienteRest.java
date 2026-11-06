package com.gimnasio.gimnasioweb.client;

import com.gimnasio.gimnasioweb.exception.ServicioNoDisponibleException;
import org.springframework.web.client.RestClient;

import java.io.IOException;

public final class ClienteRest {

    private ClienteRest() {
    }

    public static RestClient crear(String url, String nombreServicio) {
        return RestClient.builder()
                .baseUrl(url)
                .requestInterceptor((request, body, execution) -> {
                    try {
                        return execution.execute(request, body);
                    } catch (IOException ex) {
                        throw new ServicioNoDisponibleException(
                                nombreServicio + " no esta disponible en " + url + ", verifique que este ejecutandose");
                    }
                })
                .build();
    }
}
