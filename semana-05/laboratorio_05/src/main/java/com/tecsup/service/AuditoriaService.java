package com.tecsup.service;

import com.tecsup.model.AuditoriaLog;
import com.tecsup.repository.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    @Autowired
    private AuditoriaRepository repo;

    public void registrar(String accion, String metodo, String detalle) {
        if (detalle != null && detalle.length() > 255) {
            detalle = detalle.substring(0, 255);
        }
        AuditoriaLog log = new AuditoriaLog(accion, metodo, detalle);
        repo.save(log);
    }
}