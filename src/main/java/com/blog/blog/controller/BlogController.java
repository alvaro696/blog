package com.blog.blog.controller;

import com.blog.blog.dto.BlogRequestDTO;
import com.blog.blog.dto.BlogResponseDTO;
import com.blog.blog.service.BlogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Blogs", description = "API para la gestión de blogs")
public class BlogController {

    private final BlogService blogService;

    @Autowired
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }

    // post
    @PostMapping
    @Operation(summary = "Crear blog", description = "Crea un nuevo blog en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Blog creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<BlogResponseDTO> crearBlog(
            @Parameter(description = "Datos del blog a crear", required = true) @Valid @RequestBody BlogRequestDTO blogRequestDTO) {

        BlogResponseDTO blogCreado = blogService.crearBlog(blogRequestDTO);
        return new ResponseEntity<>(blogCreado, HttpStatus.CREATED);
    }

    // GET todos con info completa
    @GetMapping
    @Operation(summary = "Obtener todos los blogs", description = "Retorna una lista de todos los blogs con información completa incluyendo autor, comentarios y estadísticas")
    @ApiResponse(responseCode = "200", description = "Lista de blogs obtenida exitosamente")
    public ResponseEntity<List<BlogResponseDTO>> obtenerTodosLosBlogs() {
        List<BlogResponseDTO> blogs = blogService.obtenerTodosLosBlogsCompletos();
        return ResponseEntity.ok(blogs);
    }

    // GET por id
    @GetMapping("/{id}")
    @Operation(summary = "Buscar blog por ID", description = "Busca un blog específico por su ID con toda la información: autor, comentarios y resumen de puntuaciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog encontrado"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado")
    })
    public ResponseEntity<BlogResponseDTO> buscarBlogPorId(
            @Parameter(description = "ID del blog", required = true) @PathVariable Long id) {

        BlogResponseDTO blog = blogService.buscarBlogCompletoPorId(id);
        return ResponseEntity.ok(blog);
    }

    // PUT actualizar blog
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar blog", description = "Actualiza un blog existente. Se crea automáticamente una entrada en el historial antes de la actualización")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Blog actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Blog o autor no encontrado")
    })
    public ResponseEntity<BlogResponseDTO> actualizarBlog(
            @Parameter(description = "ID del blog", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del blog", required = true) @Valid @RequestBody BlogRequestDTO blogRequestDTO) {

        BlogResponseDTO blogActualizado = blogService.actualizarBlog(id, blogRequestDTO);
        return ResponseEntity.ok(blogActualizado);
    }

    // DELETE eliminar blog
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar blog", description = "Elimina un blog del sistema junto con sus comentarios e historial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Blog eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado")
    })
    public ResponseEntity<Void> eliminarBlog(
            @Parameter(description = "ID del blog", required = true) @PathVariable Long id) {

        blogService.eliminarBlog(id);
        return ResponseEntity.noContent().build();
    }

    // GET blogs por autor
    @GetMapping("/autor/{autorId}")
    @Operation(summary = "Buscar blogs por autor", description = "Busca todos los blogs de un autor específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de blogs del autor"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<List<BlogResponseDTO>> buscarBlogsPorAutor(
            @Parameter(description = "ID del autor", required = true) @PathVariable Long autorId) {

        List<BlogResponseDTO> blogs = blogService.buscarBlogsPorAutor(autorId);
        return ResponseEntity.ok(blogs);
    }

    // GET resumen de blogs (sin comentarios ni estadísticas)
    @GetMapping("/resumen")
    @Operation(summary = "Obtener resumen de blogs", description = "Retorna una lista de todos los blogs con información básica (sin comentarios ni estadísticas)")
    @ApiResponse(responseCode = "200", description = "Lista de resúmenes de blogs obtenida exitosamente")
    public ResponseEntity<List<BlogResponseDTO>> obtenerResumenBlogs() {
        List<BlogResponseDTO> blogs = blogService.obtenerTodosLosBlogs();
        return ResponseEntity.ok(blogs);
    }
}
