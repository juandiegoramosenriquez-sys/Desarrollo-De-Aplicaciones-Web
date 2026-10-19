package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Presentacion;
import com.tecsup.farmacia.repository.PresentacionRepository;
import com.tecsup.farmacia.entity.Medicamento;
import com.tecsup.farmacia.repository.MedicamentoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PresentacionService {

    private final PresentacionRepository presentacionRepository;
    private final MedicamentoRepository medicamentoRepository;

    public PresentacionService(
            PresentacionRepository presentacionRepository,
            MedicamentoRepository medicamentoRepository) {

        this.presentacionRepository = presentacionRepository;
        this.medicamentoRepository = medicamentoRepository;
    }

    public List<Presentacion> listar() {
        return presentacionRepository.findAll();
    }

    public Optional<Presentacion> buscarPorId(Long id) {
        return presentacionRepository.findById(id);
    }

    public Presentacion guardar(Presentacion presentacion) {

        if (presentacion.getMedicamento() == null ||
                presentacion.getMedicamento().getId() == null) {
            return null;
        }

        Long medicamentoId = presentacion.getMedicamento().getId();

        Optional<Medicamento> medicamento =
                medicamentoRepository.findById(medicamentoId);

        if (medicamento.isEmpty()) {
            return null;
        }

        presentacion.setMedicamento(medicamento.get());

        return presentacionRepository.save(presentacion);
    }

    public Presentacion actualizar(Long id, Presentacion presentacionActualizada) {

        Optional<Presentacion> presentacionExistente =
                presentacionRepository.findById(id);

        if (presentacionExistente.isEmpty()) {
            return null;
        }

        if (presentacionActualizada.getMedicamento() == null ||
                presentacionActualizada.getMedicamento().getId() == null) {
            return null;
        }

        Long medicamentoId =
                presentacionActualizada.getMedicamento().getId();

        Optional<Medicamento> medicamento =
                medicamentoRepository.findById(medicamentoId);

        if (medicamento.isEmpty()) {
            return null;
        }

        Presentacion presentacion = presentacionExistente.get();

        presentacion.setNombre(presentacionActualizada.getNombre());
        presentacion.setDescripcion(presentacionActualizada.getDescripcion());
        presentacion.setEstado(presentacionActualizada.isEstado());
        presentacion.setMedicamento(medicamento.get());

        return presentacionRepository.save(presentacion);
    }

    public boolean eliminar(Long id) {
        if (presentacionRepository.existsById(id)) {
            presentacionRepository.deleteById(id);
            return true;
        }

        return false;
    }
}