package com.tecsup.laboratorio_03.service;

import com.tecsup.laboratorio_03.model.Empleado;
import com.tecsup.laboratorio_03.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService {

    @Autowired
    private EmpleadoRepository er;

    // Listar
    public List<Empleado> listar() {
        return er.findAll();
    }

    // Guardar
    public Empleado guardar(Empleado e) {
        return er.save(e);
    }

    // Buscar
    public Empleado obtener(Long id) {
        return er.findById(id).orElse(null);
    }

    // Eliminar
    public void eliminar(Long id) {
        er.deleteById(id);
    }
}