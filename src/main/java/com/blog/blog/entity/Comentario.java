package com.blog.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comentarios")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del comentarista es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Column(name = "nombre_comentarista", nullable = false, length = 100)
    private String nombreComentarista;

    @NotBlank(message = "El correo del comentarista es obligatorio")
    @Email(message = "El correo electrónico debe tener un formato válido")
    @Size(max = 150, message = "El correo no puede exceder 150 caracteres")
    @Column(name = "email_comentarista", nullable = false, length = 150)
    private String emailComentarista;

    @NotBlank(message = "El país de residencia es obligatorio")
    @Size(max = 100, message = "El país no puede exceder 100 caracteres")
    @Column(name = "pais_comentarista", nullable = false, length = 100)
    private String paisComentarista;

    @NotBlank(message = "El contenido del comentario es obligatorio")
    @Size(max = 1000, message = "El comentario no puede exceder 1000 caracteres")
    @Column(nullable = false, length = 1000)
    private String contenido;

    @NotNull(message = "La puntuación es obligatoria")
    @Min(value = 0, message = "La puntuación mínima es 0")
    @Max(value = 10, message = "La puntuación máxima es 10")
    @Column(nullable = false)
    private Integer puntuacion;

    @Column(name = "fecha_comentario", nullable = false)
    private LocalDateTime fechaComentario;

    @NotNull(message = "El blog es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    @JsonIgnore
    private Blog blog;

    public Comentario() {
        this.fechaComentario = LocalDateTime.now();
    }

    public Comentario(String nombreComentarista, String emailComentarista, String paisComentarista,
            String contenido, Integer puntuacion, Blog blog) {
        this();
        this.nombreComentarista = nombreComentarista;
        this.emailComentarista = emailComentarista;
        this.paisComentarista = paisComentarista;
        this.contenido = contenido;
        this.puntuacion = puntuacion;
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(LocalDateTime fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaComentario = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", nombreComentarista='" + nombreComentarista + '\'' +
                ", emailComentarista='" + emailComentarista + '\'' +
                ", puntuacion=" + puntuacion +
                ", fechaComentario=" + fechaComentario +
                '}';
    }
}
