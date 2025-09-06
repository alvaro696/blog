package com.blog.blog.controller;

import com.blog.blog.dto.AutorRequestDTO;
import com.blog.blog.dto.AutorResponseDTO;
import com.blog.blog.service.AutorService;
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
@RequestMapping("/api/autores")
@Tag(name = "Autores", description = "API para la gestión de autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // post
    @PostMapping
    @Operation(summary = "Crear autor", description = "Crea un nuevo autor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "409", description = "Email ya existe en el sistema")
    })
    public ResponseEntity<AutorResponseDTO> crearAutor(
            @Parameter(description = "Datos del autor a crear", required = true) @Valid @RequestBody AutorRequestDTO autorRequestDTO) {

        AutorResponseDTO autorCreado = autorService.crearAutor(autorRequestDTO);
        return new ResponseEntity<>(autorCreado, HttpStatus.CREATED);
    }

    // GET todos
    @GetMapping
    @Operation(summary = "Obtener todos los autores", description = "Retorna una lista de todos los autores registrados")
    @ApiResponse(responseCode = "200", description = "Lista de autores obtenida exitosamente")
    public ResponseEntity<List<AutorResponseDTO>> obtenerTodosLosAutores() {
        List<AutorResponseDTO> autores = autorService.obtenerTodosLosAutores();
        return ResponseEntity.ok(autores);
    }

    // GET por id
    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor por ID", description = "Busca un autor específico por su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<AutorResponseDTO> buscarAutorPorId(
            @Parameter(description = "ID del autor", required = true) @PathVariable Long id) {

        AutorResponseDTO autor = autorService.buscarAutorPorId(id);
        return ResponseEntity.ok(autor);
    }

    // GET por email
    @GetMapping("/email/{email}")
    @Operation(summary = "Buscar autor por email", description = "Busca un autor específico por su email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<AutorResponseDTO> buscarAutorPorEmail(
            @Parameter(description = "Email del autor", required = true) @PathVariable String email) {

        AutorResponseDTO autor = autorService.buscarAutorPorEmail(email);
        return ResponseEntity.ok(autor);
    }

    // GET por país
    @GetMapping("/pais/{pais}")
    @Operation(summary = "Buscar autores por país", description = "Busca autores por país de residencia")
    @ApiResponse(responseCode = "200", description = "Lista de autores del país especificado")
    public ResponseEntity<List<AutorResponseDTO>> buscarAutoresPorPais(
            @Parameter(description = "País de residencia", required = true) @PathVariable String pais) {

        List<AutorResponseDTO> autores = autorService.buscarAutoresPorPais(pais);
        return ResponseEntity.ok(autores);
    }

    // GET por nombre
    @GetMapping("/buscar")
    @Operation(summary = "Buscar autores por nombre", description = "Busca autores por nombres o apellidos")
    @ApiResponse(responseCode = "200", description = "Lista de autores que coinciden con el término de búsqueda")
    public ResponseEntity<List<AutorResponseDTO>> buscarAutoresPorNombre(
            @Parameter(description = "Término de búsqueda en nombres o apellidos", required = true) @RequestParam String termino) {

        List<AutorResponseDTO> autores = autorService.buscarAutoresPorNombre(termino);
        return ResponseEntity.ok(autores);
    }

    // PUT actualizar autor
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar autor", description = "Actualiza los datos de un autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
            @ApiResponse(responseCode = "409", description = "Email ya existe en otro autor")
    })
    public ResponseEntity<AutorResponseDTO> actualizarAutor(
            @Parameter(description = "ID del autor", required = true) @PathVariable Long id,
            @Parameter(description = "Nuevos datos del autor", required = true) @Valid @RequestBody AutorRequestDTO autorRequestDTO) {

        AutorResponseDTO autorActualizado = autorService.actualizarAutor(id, autorRequestDTO);
        return ResponseEntity.ok(autorActualizado);
    }

    // DELETE eliminar autor
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar autor", description = "Elimina un autor del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Autor eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado"),
            @ApiResponse(responseCode = "409", description = "No se puede eliminar autor con blogs asociados")
    })
    public ResponseEntity<Void> eliminarAutor(
            @Parameter(description = "ID del autor", required = true) @PathVariable Long id) {

        autorService.eliminarAutor(id);
        return ResponseEntity.noContent().build();
    }
}
