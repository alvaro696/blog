package com.blog.blog.dto;

import jakarta.validation.constraints.*;

public class ComentarioRequestDTO {

    @NotBlank(message = "El nombre del comentarista es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombreComentarista;

    @NotBlank(message = "El correo del comentarista es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede exceder 150 caracteres")
    private String emailComentarista;

    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    private String paisComentarista;

    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    private String contenido;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    private Integer puntuacion;

    public ComentarioRequestDTO() {
    }

    public ComentarioRequestDTO(String nombreComentarista, String emailComentarista,
            String paisComentarista, String contenido, Integer puntuacion) {
        this.nombreComentarista = nombreComentarista;
        this.emailComentarista = emailComentarista;
        this.paisComentarista = paisComentarista;
        this.contenido = contenido;
        this.puntuacion = puntuacion;
    }

    public String getNombreComentarista() {
        return nombreComentarista;
    }

    public void setNombreComentarista(String nombreComentarista) {
        this.nombreComentarista = nombreComentarista;
    }

    public String getEmailComentarista() {
        return emailComentarista;
    }

    public void setEmailComentarista(String emailComentarista) {
        this.emailComentarista = emailComentarista;
    }

    public String getPaisComentarista() {
        return paisComentarista;
    }

    public void setPaisComentarista(String paisComentarista) {
        this.paisComentarista = paisComentarista;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "ComentarioRequestDTO{" +
                "nombreComentarista='" + nombreComentarista + '\'' +
                ", emailComentarista='" + emailComentarista + '\'' +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
