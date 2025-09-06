package com.blog.blog.dto;

import com.blog.blog.entity.Periodicidad;
import jakarta.validation.constraints.*;

public class BlogRequestDTO {

    @NotBlank(message = "El título del blog es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    private String titulo;

    @NotBlank(message = "El tema del blog es obligatorio")
    @Size(max = 100, message = "El tema no puede exceder 100 caracteres")
    private String tema;

    @NotBlank(message = "El contenido del blog es obligatorio")
    private String contenido;

    @NotNull(message = "La periodicidad es obligatoria")
    private Periodicidad periodicidad;

    @NotNull(message = "Debe especificar si permite comentarios")
    private Boolean permiteComentarios;

    @NotNull(message = "El ID del autor es obligatorio")
    private Long autorId;

    public BlogRequestDTO() {
    }

    public BlogRequestDTO(String titulo, String tema, String contenido,
            Periodicidad periodicidad, Boolean permiteComentarios, Long autorId) {
        this.titulo = titulo;
        this.tema = tema;
        this.contenido = contenido;
        this.periodicidad = periodicidad;
        this.permiteComentarios = permiteComentarios;
        this.autorId = autorId;
    }

    // getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Periodicidad getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(Periodicidad periodicidad) {
        this.periodicidad = periodicidad;
    }

    public Boolean getPermiteComentarios() {
        return permiteComentarios;
    }

    public void setPermiteComentarios(Boolean permiteComentarios) {
        this.permiteComentarios = permiteComentarios;
    }

    public Long getAutorId() {
        return autorId;
    }

    public void setAutorId(Long autorId) {
        this.autorId = autorId;
    }

    @Override
    public String toString() {
        return "BlogRequestDTO{" +
                "titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", periodicidad=" + periodicidad +
                ", permiteComentarios=" + permiteComentarios +
                ", autorId=" + autorId +
                '}';
    }
}
