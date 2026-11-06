package com.gimnasio.socioservice.services;

import com.gimnasio.socioservice.dto.SocioRequestDTO;
import com.gimnasio.socioservice.dto.SocioResponseDTO;
import com.gimnasio.socioservice.exception.RecursoNoEncontradoException;
import com.gimnasio.socioservice.exception.ReglaNegocioException;
import com.gimnasio.socioservice.model.Socio;
import com.gimnasio.socioservice.repository.SocioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SocioService {

    private final SocioRepository socioRepository;

    public List<SocioResponseDTO> listar() {
        return socioRepository.findAll()
                .stream()
                .map(this::convertirAResponse)
                .toList();
    }

    public SocioResponseDTO buscarPorId(Long id) {
        return convertirAResponse(obtenerSocio(id));
    }

    public SocioResponseDTO registrar(SocioRequestDTO dto) {
        if (socioRepository.existsByDni(dto.dni())) {
            throw new ReglaNegocioException("Ya existe un socio con el DNI " + dto.dni());
        }
        Socio socio = new Socio();
        copiarDatos(dto, socio);
        return convertirAResponse(socioRepository.save(socio));
    }

    public SocioResponseDTO actualizar(Long id, SocioRequestDTO dto) {
        Socio socio = obtenerSocio(id);
        if (socioRepository.existsByDniAndIdNot(dto.dni(), id)) {
            throw new ReglaNegocioException("Ya existe otro socio con el DNI " + dto.dni());
        }
        copiarDatos(dto, socio);
        return convertirAResponse(socioRepository.save(socio));
    }

    public void eliminar(Long id) {
        socioRepository.delete(obtenerSocio(id));
    }

    private Socio obtenerSocio(Long id) {
        return socioRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe un socio con id " + id));
    }

    private void copiarDatos(SocioRequestDTO dto, Socio socio) {
        socio.setDni(dto.dni());
        socio.setNombres(dto.nombres());
        socio.setApellidos(dto.apellidos());
        socio.setCorreo(dto.correo());
        socio.setTelefono(dto.telefono());
        socio.setEstado(dto.estado());
    }

    private SocioResponseDTO convertirAResponse(Socio socio) {
        return new SocioResponseDTO(
                socio.getId(),
                socio.getDni(),
                socio.getNombres(),
                socio.getApellidos(),
                socio.getCorreo(),
                socio.getTelefono(),
                socio.getEstado()
        );
    }
}
