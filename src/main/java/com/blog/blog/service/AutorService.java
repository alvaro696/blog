package com.blog.blog.service;

import com.blog.blog.dto.AutorRequestDTO;
import com.blog.blog.dto.AutorResponseDTO;
import com.blog.blog.entity.Autor;
import com.blog.blog.exception.BusinessRuleException;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public AutorResponseDTO crearAutor(AutorRequestDTO autorRequestDTO) {
        // Validar que el email no exista
        if (autorRepository.existsByEmail(autorRequestDTO.getEmail())) {
            throw new BusinessRuleException(
                    "Email duplicado",
                    "Ya existe un autor con el email: " + autorRequestDTO.getEmail());
        }

        // Crear
        Autor autor = new Autor(
                autorRequestDTO.getNombres(),
                autorRequestDTO.getApellidoPaterno(),
                autorRequestDTO.getApellidoMaterno(),
                autorRequestDTO.getFechaNacimiento(),
                autorRequestDTO.getPaisResidencia(),
                autorRequestDTO.getEmail());

        // Guardar
        Autor autorGuardado = autorRepository.save(autor);

        return convertirAResponseDTO(autorGuardado);
    }

    @Transactional(readOnly = true)
    public AutorResponseDTO buscarAutorPorId(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "id", id));

        return convertirAResponseDTO(autor);
    }

    @Transactional(readOnly = true)
    public AutorResponseDTO buscarAutorPorEmail(String email) {
        Autor autor = autorRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "email", email));

        return convertirAResponseDTO(autor);
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> obtenerTodosLosAutores() {
        List<Autor> autores = autorRepository.findAll();
        return autores.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> buscarAutoresPorPais(String pais) {
        List<Autor> autores = autorRepository.findByPaisResidencia(pais);
        return autores.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<AutorResponseDTO> buscarAutoresPorNombre(String termino) {
        List<Autor> autores = autorRepository.findByNombreContaining(termino);
        return autores.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public AutorResponseDTO actualizarAutor(Long id, AutorRequestDTO autorRequestDTO) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "id", id));

        // Validar email único si se está cambiando
        if (!autor.getEmail().equals(autorRequestDTO.getEmail())) {
            if (autorRepository.existsByEmail(autorRequestDTO.getEmail())) {
                throw new BusinessRuleException(
                        "Email duplicado",
                        "Ya existe otro autor con el email: " + autorRequestDTO.getEmail());
            }
        }

        // Actualizar
        autor.setNombres(autorRequestDTO.getNombres());
        autor.setApellidoPaterno(autorRequestDTO.getApellidoPaterno());
        autor.setApellidoMaterno(autorRequestDTO.getApellidoMaterno());
        autor.setFechaNacimiento(autorRequestDTO.getFechaNacimiento());
        autor.setPaisResidencia(autorRequestDTO.getPaisResidencia());
        autor.setEmail(autorRequestDTO.getEmail());

        Autor autorActualizado = autorRepository.save(autor);
        return convertirAResponseDTO(autorActualizado);
    }

    public void eliminarAutor(Long id) {
        Autor autor = autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "id", id));

        // Verificar que no tenga blogs
        if (!autor.getBlogs().isEmpty()) {
            throw new BusinessRuleException(
                    "Autor con blogs asociados",
                    "No se puede eliminar un autor que tiene blogs publicados");
        }

        autorRepository.delete(autor);
    }

    @Transactional(readOnly = true)
    public Autor obtenerAutorEntity(Long id) {
        return autorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Autor", "id", id));
    }

    private AutorResponseDTO convertirAResponseDTO(Autor autor) {
        AutorResponseDTO dto = new AutorResponseDTO(
                autor.getId(),
                autor.getNombres(),
                autor.getApellidoPaterno(),
                autor.getApellidoMaterno(),
                autor.getFechaNacimiento(),
                autor.getPaisResidencia(),
                autor.getEmail(),
                autor.getFechaCreacion());

        // Establecer total
        dto.setTotalBlogs(autor.getBlogs() != null ? autor.getBlogs().size() : 0);

        return dto;
    }
}
