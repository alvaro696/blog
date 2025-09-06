package com.blog.blog.controller;

import com.blog.blog.dto.ComentarioRequestDTO;
import com.blog.blog.dto.BlogResponseDTO;
import com.blog.blog.service.ComentarioService;
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

@RestController
@RequestMapping("/api/blogs")
@Tag(name = "Comentarios", description = "API para la gestión de comentarios en blogs")
public class ComentarioController {

    private final ComentarioService comentarioService;

    @Autowired
    public ComentarioController(ComentarioService comentarioService) {
        this.comentarioService = comentarioService;
    }

    // crear
    @PostMapping("/{blogId}/comentarios")
    @Operation(summary = "Agregar comentario", description = "Agrega un nuevo comentario a un blog específico. El blog debe permitir comentarios.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comentario agregado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos o reglas de negocio violadas"),
            @ApiResponse(responseCode = "404", description = "Blog no encontrado")
    })
    public ResponseEntity<BlogResponseDTO.ComentarioResponseDTO> agregarComentario(
            @Parameter(description = "ID del blog al cual agregar el comentario", required = true) @PathVariable Long blogId,
            @Parameter(description = "Datos del comentario a agregar", required = true) @Valid @RequestBody ComentarioRequestDTO comentarioRequestDTO) {

        BlogResponseDTO.ComentarioResponseDTO comentarioCreado = comentarioService.agregarComentario(blogId,
                comentarioRequestDTO);
        return new ResponseEntity<>(comentarioCreado, HttpStatus.CREATED);
    }

    // eliminar
    @DeleteMapping("/{blogId}/comentarios/{comentarioId}")
    @Operation(summary = "Eliminar comentario", description = "Elimina un comentario específico de un blog")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Blog o comentario no encontrado")
    })
    public ResponseEntity<Void> eliminarComentario(
            @Parameter(description = "ID del blog", required = true) @PathVariable Long blogId,
            @Parameter(description = "ID del comentario", required = true) @PathVariable Long comentarioId) {

        comentarioService.eliminarComentario(blogId, comentarioId);
        return ResponseEntity.noContent().build();
    }
}
