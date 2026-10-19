package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Medicamento;
import com.tecsup.farmacia.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    public boolean codigoExiste(String codigo) {
        return medicamentoRepository.existsByCodigo(codigo);
    }

    public List<Medicamento> listar() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> obtener(Long id) {
        return medicamentoRepository.findById(id);
    }

    public Medicamento registrar(Medicamento medicamento) {
        if (codigoExiste(medicamento.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un medicamento registrado con el codigo " + medicamento.getCodigo());
        }
        medicamento.setEstado(true);
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento modificar(Long id, Medicamento datosNuevos) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicamento no encontrado: " + id));

        if (!medicamento.getCodigo().equals(datosNuevos.getCodigo()) && codigoExiste(datosNuevos.getCodigo())) {
            throw new IllegalArgumentException(
                    "Ya existe un medicamento registrado con el codigo " + datosNuevos.getCodigo());
        }

        medicamento.setCodigo(datosNuevos.getCodigo());
        medicamento.setNombreComercial(datosNuevos.getNombreComercial());
        medicamento.setConcentracion(datosNuevos.getConcentracion());
        medicamento.setFormaFarmaceutica(datosNuevos.getFormaFarmaceutica());
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento cambiarEstado(Long id, boolean activo) {
        Medicamento medicamento = medicamentoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Medicamento no encontrado: " + id));
        medicamento.setEstado(activo);
        return medicamentoRepository.save(medicamento);
    }
}