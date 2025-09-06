package com.blog.blog.service;

import com.blog.blog.dto.ComentarioRequestDTO;
import com.blog.blog.dto.BlogResponseDTO;
import com.blog.blog.entity.Comentario;
import com.blog.blog.entity.Blog;
import com.blog.blog.exception.BusinessRuleException;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.repository.ComentarioRepository;
import com.blog.blog.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ComentarioService {

    private final ComentarioRepository comentarioRepository;
    private final BlogRepository blogRepository;
    private final BlogService blogService;

    @Autowired
    public ComentarioService(ComentarioRepository comentarioRepository,
            BlogRepository blogRepository,
            BlogService blogService) {
        this.comentarioRepository = comentarioRepository;
        this.blogRepository = blogRepository;
        this.blogService = blogService;
    }

    public BlogResponseDTO.ComentarioResponseDTO crearComentario(Long blogId,
            ComentarioRequestDTO comentarioRequestDTO) {
        Blog blog = blogService.obtenerBlogEntity(blogId);

        // Validar
        if (!blog.getPermiteComentarios()) {
            throw new BusinessRuleException(
                    "Blog no permite comentarios",
                    "El blog con ID " + blogId + " no acepta comentarios");
        }

        Comentario comentario = new Comentario(
                comentarioRequestDTO.getNombreComentarista(),
                comentarioRequestDTO.getEmailComentarista(),
                comentarioRequestDTO.getPaisComentarista(),
                comentarioRequestDTO.getContenido(),
                comentarioRequestDTO.getPuntuacion(),
                blog);

        Comentario comentarioGuardado = comentarioRepository.save(comentario);

        return convertirAResponseDTO(comentarioGuardado);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO.ComentarioResponseDTO> buscarComentariosPorBlog(Long blogId) {
        blogService.obtenerBlogEntity(blogId);

        List<Comentario> comentarios = comentarioRepository.findByBlogIdOrderByFechaDesc(blogId);
        return comentarios.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BlogResponseDTO.ComentarioResponseDTO buscarComentarioPorId(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", id));

        return convertirAResponseDTO(comentario);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO.ComentarioResponseDTO> buscarComentariosPorEmail(String email) {
        List<Comentario> comentarios = comentarioRepository.findByEmailComentarista(email);
        return comentarios.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO.ComentarioResponseDTO> buscarComentariosPorPuntuacion(Integer puntuacion) {
        List<Comentario> comentarios = comentarioRepository.findByPuntuacion(puntuacion);
        return comentarios.stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BlogResponseDTO.ResumenPuntuacionesDTO obtenerEstadisticasPuntuaciones(Long blogId) {
        blogService.obtenerBlogEntity(blogId);

        Object[] stats = comentarioRepository.findPuntuacionStatsByBlogId(blogId);
        if (stats != null && stats.length == 4 && stats[3] != null && ((Long) stats[3]) > 0) {
            return new BlogResponseDTO.ResumenPuntuacionesDTO(
                    (Integer) stats[0], // mínimo
                    (Integer) stats[1], // máximo
                    (Double) stats[2], // promedio
                    (Long) stats[3] // total
            );
        }

        return new BlogResponseDTO.ResumenPuntuacionesDTO(null, null, null, 0L);
    }

    public void eliminarComentario(Long id) {
        Comentario comentario = comentarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", id));

        comentarioRepository.delete(comentario);
    }

    @Transactional
    public BlogResponseDTO.ComentarioResponseDTO agregarComentario(Long blogId,
            ComentarioRequestDTO comentarioRequestDTO) {
        Blog blog = blogRepository.findById(blogId)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", blogId));

        if (!blog.getPermiteComentarios()) {
            throw new BusinessRuleException("Este blog no permite comentarios");
        }

        Comentario comentario = new Comentario(
                comentarioRequestDTO.getNombreComentarista(),
                comentarioRequestDTO.getEmailComentarista(),
                comentarioRequestDTO.getPaisComentarista(),
                comentarioRequestDTO.getContenido(),
                comentarioRequestDTO.getPuntuacion(),
                blog);

        Comentario comentarioGuardado = comentarioRepository.save(comentario);
        return convertirAResponseDTO(comentarioGuardado);
    }

    @Transactional
    public void eliminarComentario(Long blogId, Long comentarioId) {

        if (!blogRepository.existsById(blogId)) {
            throw new ResourceNotFoundException("Blog", "id", blogId);
        }

        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario", "id", comentarioId));

        if (!comentario.getBlog().getId().equals(blogId)) {
            throw new BusinessRuleException("El comentario no pertenece al blog especificado");
        }

        comentarioRepository.delete(comentario);
    }

    @Transactional(readOnly = true)
    public Long contarComentariosPorBlog(Long blogId) {
        return comentarioRepository.countByBlogId(blogId);
    }

    private BlogResponseDTO.ComentarioResponseDTO convertirAResponseDTO(Comentario comentario) {
        return new BlogResponseDTO.ComentarioResponseDTO(
                comentario.getId(),
                comentario.getNombreComentarista(),
                comentario.getEmailComentarista(),
                comentario.getPaisComentarista(),
                comentario.getContenido(),
                comentario.getPuntuacion(),
                comentario.getFechaComentario());
    }
}
