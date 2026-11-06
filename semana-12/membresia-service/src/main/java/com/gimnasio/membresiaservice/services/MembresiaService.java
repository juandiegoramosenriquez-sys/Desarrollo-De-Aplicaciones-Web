package com.gimnasio.membresiaservice.services;

import com.gimnasio.membresiaservice.client.PlanClient;
import com.gimnasio.membresiaservice.client.SocioClient;
import com.gimnasio.membresiaservice.dto.MembresiaRequestDTO;
import com.gimnasio.membresiaservice.dto.MembresiaResponseDTO;
import com.gimnasio.membresiaservice.dto.PlanDTO;
import com.gimnasio.membresiaservice.dto.SocioDTO;
import com.gimnasio.membresiaservice.exception.RecursoNoEncontradoException;
import com.gimnasio.membresiaservice.exception.ReglaNegocioException;
import com.gimnasio.membresiaservice.model.Membresia;
import com.gimnasio.membresiaservice.repository.MembresiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MembresiaService {

    private final MembresiaRepository membresiaRepository;
    private final SocioClient socioClient;
    private final PlanClient planClient;

    public List<MembresiaResponseDTO> listar() {
        return membresiaRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public MembresiaResponseDTO buscarPorId(Long id) {
        return convertirAResponse(obtenerMembresia(id));
    }

    public List<MembresiaResponseDTO> listarPorSocio(Long idSocio) {
        socioClient.buscarSocio(idSocio);
        return membresiaRepository.findByIdSocio(idSocio)
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public MembresiaResponseDTO registrar(MembresiaRequestDTO dto) {
        validarDatos(dto);
        Membresia membresia = new Membresia();
        copiarDatos(dto, membresia);
        return convertirAResponse(membresiaRepository.save(membresia));
    }

    public MembresiaResponseDTO actualizar(Long id, MembresiaRequestDTO dto) {
        Membresia membresia = obtenerMembresia(id);
        validarDatos(dto);
        copiarDatos(dto, membresia);
        return convertirAResponse(membresiaRepository.save(membresia));
    }

    public void eliminar(Long id) {
        membresiaRepository.delete(obtenerMembresia(id));
    }

    private void validarDatos(MembresiaRequestDTO dto) {
        if (!dto.fechaFin().isAfter(dto.fechaInicio())) {
            throw new ReglaNegocioException("La fecha de fin debe ser posterior a la fecha de inicio");
        }

        SocioDTO socio = socioClient.buscarSocio(dto.idSocio());
        if (!"ACTIVO".equals(socio.estado())) {
            throw new ReglaNegocioException("El socio con id " + dto.idSocio() + " no esta ACTIVO");
        }

        PlanDTO plan = planClient.buscarPlan(dto.idPlan());
        if (!"ACTIVO".equals(plan.estado())) {
            throw new ReglaNegocioException("El plan con id " + dto.idPlan() + " no esta ACTIVO");
        }
    }

    private Membresia obtenerMembresia(Long id) {
        return membresiaRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe una membresia con id " + id));
    }

    private void copiarDatos(MembresiaRequestDTO dto, Membresia membresia) {
        membresia.setIdSocio(dto.idSocio());
        membresia.setIdPlan(dto.idPlan());
        membresia.setFechaInicio(dto.fechaInicio());
        membresia.setFechaFin(dto.fechaFin());
        membresia.setEstado(dto.estado());
    }

    private MembresiaResponseDTO convertirAResponse(Membresia membresia) {
        return new MembresiaResponseDTO(
                membresia.getId(),
                membresia.getIdSocio(),
                membresia.getIdPlan(),
                membresia.getFechaInicio(),
                membresia.getFechaFin(),
                membresia.getEstado()
        );
    }
}
