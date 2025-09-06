package com.blog.blog.service;

import com.blog.blog.dto.BlogRequestDTO;
import com.blog.blog.dto.BlogResponseDTO;
import com.blog.blog.dto.AutorResponseDTO;
import com.blog.blog.entity.Blog;
import com.blog.blog.entity.Autor;
import com.blog.blog.entity.HistorialBlog;
import com.blog.blog.entity.Comentario;
import com.blog.blog.exception.ResourceNotFoundException;
import com.blog.blog.repository.BlogRepository;
import com.blog.blog.repository.ComentarioRepository;
import com.blog.blog.repository.HistorialBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BlogService {

    private final BlogRepository blogRepository;
    private final ComentarioRepository comentarioRepository;
    private final HistorialBlogRepository historialBlogRepository;
    private final AutorService autorService;

    @Autowired
    public BlogService(BlogRepository blogRepository,
            ComentarioRepository comentarioRepository,
            HistorialBlogRepository historialBlogRepository,
            AutorService autorService) {
        this.blogRepository = blogRepository;
        this.comentarioRepository = comentarioRepository;
        this.historialBlogRepository = historialBlogRepository;
        this.autorService = autorService;
    }

    public BlogResponseDTO crearBlog(BlogRequestDTO blogRequestDTO) {
        // Obtener autor
        Autor autor = autorService.obtenerAutorEntity(blogRequestDTO.getAutorId());

        // Crear
        Blog blog = new Blog(
                blogRequestDTO.getTitulo(),
                blogRequestDTO.getTema(),
                blogRequestDTO.getContenido(),
                blogRequestDTO.getPeriodicidad(),
                blogRequestDTO.getPermiteComentarios(),
                autor);

        // Guardar
        Blog blogGuardado = blogRepository.save(blog);

        return convertirAResponseDTO(blogGuardado, false);
    }

    @Transactional(readOnly = true)
    public BlogResponseDTO buscarBlogCompletoPorId(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        return convertirAResponseDTOSeguro(blog, true);
    }

    @Transactional(readOnly = true)
    public BlogResponseDTO buscarBlogPorId(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        return convertirAResponseDTO(blog, false);
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO> obtenerTodosLosBlogsCompletos() {
        List<Blog> blogs = blogRepository.findAllWithAutor();
        return blogs.stream()
                .map(blog -> convertirAResponseDTOSeguro(blog, true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO> obtenerTodosLosBlogs() {
        List<Blog> blogs = blogRepository.findAllWithAutor();
        return blogs.stream()
                .map(blog -> convertirAResponseDTOSeguro(blog, true))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<BlogResponseDTO> buscarBlogsPorAutor(Long autorId) {
        List<Blog> blogs = blogRepository.findByAutorId(autorId);
        return blogs.stream()
                .map(blog -> convertirAResponseDTO(blog, false))
                .collect(Collectors.toList());
    }

    public BlogResponseDTO actualizarBlog(Long id, BlogRequestDTO blogRequestDTO) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        // Crear
        crearEntradaHistorial(blog);

        // Verificar
        if (!blog.getAutor().getId().equals(blogRequestDTO.getAutorId())) {
            Autor nuevoAutor = autorService.obtenerAutorEntity(blogRequestDTO.getAutorId());
            blog.setAutor(nuevoAutor);
        }

        // Actualizar
        blog.setTitulo(blogRequestDTO.getTitulo());
        blog.setTema(blogRequestDTO.getTema());
        blog.setContenido(blogRequestDTO.getContenido());
        blog.setPeriodicidad(blogRequestDTO.getPeriodicidad());
        blog.setPermiteComentarios(blogRequestDTO.getPermiteComentarios());

        Blog blogActualizado = blogRepository.save(blog);
        return convertirAResponseDTO(blogActualizado, false);
    }

    public void eliminarBlog(Long id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));

        blogRepository.delete(blog);
    }

    @Transactional(readOnly = true)
    public Blog obtenerBlogEntity(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog", "id", id));
    }

    private void crearEntradaHistorial(Blog blog) {
        Integer siguienteVersion = historialBlogRepository.getNextVersionForBlog(blog.getId());

        HistorialBlog historial = new HistorialBlog(
                blog,
                siguienteVersion,
                blog.getTitulo(),
                blog.getTema(),
                blog.getContenido(),
                blog.getPeriodicidad(),
                blog.getPermiteComentarios(),
                "Actualización de blog");

        historialBlogRepository.save(historial);
    }

    private BlogResponseDTO convertirAResponseDTOSeguro(Blog blog, boolean incluirDetallesCompletos) {
        BlogResponseDTO dto = new BlogResponseDTO(
                blog.getId(),
                blog.getTitulo(),
                blog.getTema(),
                blog.getContenido(),
                blog.getPeriodicidad(),
                blog.getPermiteComentarios(),
                blog.getFechaCreacion(),
                blog.getFechaActualizacion());

        // Agregar
        if (blog.getAutor() != null) {
            AutorResponseDTO autorDTO = new AutorResponseDTO(
                    blog.getAutor().getId(),
                    blog.getAutor().getNombres(),
                    blog.getAutor().getApellidoPaterno(),
                    blog.getAutor().getApellidoMaterno(),
                    blog.getAutor().getFechaNacimiento(),
                    blog.getAutor().getPaisResidencia(),
                    blog.getAutor().getEmail(),
                    blog.getAutor().getFechaCreacion());
            dto.setAutor(autorDTO);
        }

        if (incluirDetallesCompletos) {
            try {
                // Cargar comentarios
                List<Comentario> comentarios = comentarioRepository.findByBlogIdOrderByFechaDesc(blog.getId());
                if (comentarios != null && !comentarios.isEmpty()) {
                    List<BlogResponseDTO.ComentarioResponseDTO> comentariosDTO = comentarios.stream()
                            .map(comentario -> new BlogResponseDTO.ComentarioResponseDTO(
                                    comentario.getId(),
                                    comentario.getNombreComentarista(),
                                    comentario.getEmailComentarista(),
                                    comentario.getPaisComentarista(),
                                    comentario.getContenido(),
                                    comentario.getPuntuacion(),
                                    comentario.getFechaComentario()))
                            .collect(Collectors.toList());
                    dto.setComentarios(comentariosDTO);

                    dto.setTotalComentarios(comentarios.size());
                } else {
                    dto.setComentarios(List.of());
                    dto.setTotalComentarios(0);
                }
            } catch (Exception e) {
                dto.setComentarios(List.of());
                dto.setTotalComentarios(0);
            }

            // Agregar resumen
            try {
                Object[] stats = comentarioRepository.findPuntuacionStatsByBlogId(blog.getId());
                if (stats != null && stats.length == 4 && stats[3] != null && ((Long) stats[3]) > 0) {
                    BlogResponseDTO.ResumenPuntuacionesDTO resumen = new BlogResponseDTO.ResumenPuntuacionesDTO(
                            (Integer) stats[0], // mínimo
                            (Integer) stats[1], // máximo
                            (Double) stats[2], // promedio
                            (Long) stats[3] // total
                    );
                    dto.setResumenPuntuaciones(resumen);
                } else {
                    // Si no hay comentarios o estadísticas, usar valores por defecto
                    Long totalComentarios = comentarioRepository.countByBlogId(blog.getId());
                    if (totalComentarios > 0) {
                        // Recalcular
                        List<Comentario> comentarios = comentarioRepository.findByBlogIdOrderByFechaDesc(blog.getId());
                        if (!comentarios.isEmpty()) {
                            int min = comentarios.stream().mapToInt(Comentario::getPuntuacion).min().orElse(0);
                            int max = comentarios.stream().mapToInt(Comentario::getPuntuacion).max().orElse(0);
                            double avg = comentarios.stream().mapToInt(Comentario::getPuntuacion).average().orElse(0.0);

                            BlogResponseDTO.ResumenPuntuacionesDTO resumen = new BlogResponseDTO.ResumenPuntuacionesDTO(
                                    min, max, avg, totalComentarios);
                            dto.setResumenPuntuaciones(resumen);
                        }
                    }
                }
            } catch (Exception e) {
            }
        }

        return dto;
    }

    private BlogResponseDTO convertirAResponseDTO(Blog blog, boolean incluirDetallesCompletos) {
        BlogResponseDTO dto = new BlogResponseDTO(
                blog.getId(),
                blog.getTitulo(),
                blog.getTema(),
                blog.getContenido(),
                blog.getPeriodicidad(),
                blog.getPermiteComentarios(),
                blog.getFechaCreacion(),
                blog.getFechaActualizacion());

        if (blog.getAutor() != null) {
            AutorResponseDTO autorDTO = new AutorResponseDTO(
                    blog.getAutor().getId(),
                    blog.getAutor().getNombres(),
                    blog.getAutor().getApellidoPaterno(),
                    blog.getAutor().getApellidoMaterno(),
                    blog.getAutor().getFechaNacimiento(),
                    blog.getAutor().getPaisResidencia(),
                    blog.getAutor().getEmail(),
                    blog.getAutor().getFechaCreacion());
            dto.setAutor(autorDTO);
        }

        if (incluirDetallesCompletos) {
            if (blog.getComentarios() != null) {
                List<BlogResponseDTO.ComentarioResponseDTO> comentariosDTO = blog.getComentarios().stream()
                        .map(comentario -> new BlogResponseDTO.ComentarioResponseDTO(
                                comentario.getId(),
                                comentario.getNombreComentarista(),
                                comentario.getEmailComentarista(),
                                comentario.getPaisComentarista(),
                                comentario.getContenido(),
                                comentario.getPuntuacion(),
                                comentario.getFechaComentario()))
                        .collect(Collectors.toList());
                dto.setComentarios(comentariosDTO);
                dto.setTotalComentarios(comentariosDTO.size());
            } else {
                dto.setComentarios(List.of());
                dto.setTotalComentarios(0);
            }

            Object[] stats = comentarioRepository.findPuntuacionStatsByBlogId(blog.getId());
            if (stats != null && stats.length == 4 && stats[3] != null && ((Long) stats[3]) > 0) {
                BlogResponseDTO.ResumenPuntuacionesDTO resumen = new BlogResponseDTO.ResumenPuntuacionesDTO(
                        (Integer) stats[0], // mínimo
                        (Integer) stats[1], // máximo
                        (Double) stats[2], // promedio
                        (Long) stats[3] // total
                );
                dto.setResumenPuntuaciones(resumen);
            } else {
                Long totalComentarios = comentarioRepository.countByBlogId(blog.getId());
                if (totalComentarios > 0) {
                    List<Comentario> comentarios = comentarioRepository.findByBlogIdOrderByFechaDesc(blog.getId());
                    if (!comentarios.isEmpty()) {
                        int min = comentarios.stream().mapToInt(Comentario::getPuntuacion).min().orElse(0);
                        int max = comentarios.stream().mapToInt(Comentario::getPuntuacion).max().orElse(0);
                        double avg = comentarios.stream().mapToInt(Comentario::getPuntuacion).average().orElse(0.0);

                        BlogResponseDTO.ResumenPuntuacionesDTO resumen = new BlogResponseDTO.ResumenPuntuacionesDTO(
                                min, max, avg, totalComentarios);
                        dto.setResumenPuntuaciones(resumen);
                    }
                }
            }
        }

        return dto;
    }
}
