package com.tecsup.farmacia.service;

import com.tecsup.farmacia.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DashboardService {

    private final MedicamentoRepository medicamentoRepository;

    public DashboardService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public Map<String, Object> obtenerIndicadores() {

        Map<String, Object> indicadores = new HashMap<>();

        indicadores.put(
                "medicamentosRegistrados",
                medicamentoRepository.count()
        );

        indicadores.put("stockDisponible", 0);
        indicadores.put("stockBajo", 0);
        indicadores.put("proximosAVencer", 0);
        indicadores.put("recetasPendientes", 0);
        indicadores.put("dispensacionesDelDia", 0);

        return indicadores;
    }
}