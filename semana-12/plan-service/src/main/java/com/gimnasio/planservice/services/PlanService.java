package com.gimnasio.planservice.services;

import com.gimnasio.planservice.dto.PlanRequestDTO;
import com.gimnasio.planservice.dto.PlanResponseDTO;
import com.gimnasio.planservice.exception.RecursoNoEncontradoException;
import com.gimnasio.planservice.exception.ReglaNegocioException;
import com.gimnasio.planservice.model.Plan;
import com.gimnasio.planservice.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {

    private final PlanRepository planRepository;

    public List<PlanResponseDTO> listar() {
        return planRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public PlanResponseDTO buscarPorId(Long id) {
        return convertirAResponse(obtenerPlan(id));
    }

    public PlanResponseDTO registrar(PlanRequestDTO dto) {
        if (planRepository.existsByNombreIgnoreCase(dto.nombre())) {
            throw new ReglaNegocioException("Ya existe un plan con el nombre " + dto.nombre());
        }
        Plan plan = new Plan();
        copiarDatos(dto, plan);
        return convertirAResponse(planRepository.save(plan));
    }

    public PlanResponseDTO actualizar(Long id, PlanRequestDTO dto) {
        Plan plan = obtenerPlan(id);
        if (planRepository.existsByNombreIgnoreCaseAndIdNot(dto.nombre(), id)) {
            throw new ReglaNegocioException("Ya existe otro plan con el nombre " + dto.nombre());
        }
        copiarDatos(dto, plan);
        return convertirAResponse(planRepository.save(plan));
    }

    public void eliminar(Long id) {
        planRepository.delete(obtenerPlan(id));
    }

    private Plan obtenerPlan(Long id) {
        return planRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un plan con id " + id));
    }

    private void copiarDatos(PlanRequestDTO dto, Plan plan) {
        plan.setNombre(dto.nombre());
        plan.setDescripcion(dto.descripcion());
        plan.setPrecio(dto.precio());
        plan.setDuracionMeses(dto.duracionMeses());
        plan.setEstado(dto.estado());
    }

    private PlanResponseDTO convertirAResponse(Plan plan) {
        return new PlanResponseDTO(
                plan.getId(),
                plan.getNombre(),
                plan.getDescripcion(),
                plan.getPrecio(),
                plan.getDuracionMeses(),
                plan.getEstado()
        );
    }
}
