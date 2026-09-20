package com.tecsup.farmacia.service;

import com.tecsup.farmacia.entity.Lote;
import com.tecsup.farmacia.repository.LoteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LoteService {

    private final LoteRepository loteRepository;

    public LoteService(LoteRepository loteRepository) {
        this.loteRepository = loteRepository;
    }

    public List<Lote> listar() {
        return loteRepository.findAll();
    }

    public Lote obtener(Long id) {
        return loteRepository.findById(id).orElse(null);
    }

    public List<Lote> listarPorMedicamento(Long medicamentoId) {
        return loteRepository.findByMedicamentoId(medicamentoId);
    }

    // RF-FAR-11: registrar lote (con su vencimiento incluido, RF-FAR-12)
    public Lote registrar(Lote lote) {
        if (loteRepository.existsByNumeroLote(lote.getNumeroLote())) {
            throw new RuntimeException("Ya existe un lote con el número: " + lote.getNumeroLote());
        }
        return loteRepository.save(lote);
    }

    public Lote modificar(Long id, Lote datos) {
        Lote existente = obtener(id);
        if (existente == null) {
            return null;
        }
        existente.setMedicamento(datos.getMedicamento());
        existente.setNumeroLote(datos.getNumeroLote());
        existente.setFechaVencimiento(datos.getFechaVencimiento());
        existente.setCantidad(datos.getCantidad());
        existente.setCostoUnitario(datos.getCostoUnitario());
        existente.setProveedor(datos.getProveedor());
        return loteRepository.save(existente);
    }

    public void eliminar(Long id) {
        loteRepository.deleteById(id);
    }

    // Para el dashboard / alertas de vencimiento
    public List<Lote> proximosAVencer(int dias) {
        return loteRepository.findProximosAVencer(LocalDate.now().plusDays(dias));
    }
}