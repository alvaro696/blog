package com.blog.blog.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad que representa un blog
 */
@Entity
@Table(name = "blogs")
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del blog es obligatorio")
    @Size(max = 200, message = "El título no puede exceder 200 caracteres")
    @Column(nullable = false, length = 200)
    private String titulo;

    @NotBlank(message = "El tema del blog es obligatorio")
    @Size(max = 100, message = "El tema no puede exceder 100 caracteres")
    @Column(nullable = false, length = 100)
    private String tema;

    @NotBlank(message = "El contenido del blog es obligatorio")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String contenido;

    @NotNull(message = "La periodicidad es obligatoria")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Periodicidad periodicidad;

    @NotNull(message = "Debe especificar si permite comentarios")
    @Column(name = "permite_comentarios", nullable = false)
    private Boolean permiteComentarios;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", nullable = false)
    private LocalDateTime fechaActualizacion;

    @NotNull(message = "El autor es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = false)
    private Autor autor;

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comentario> comentarios = new ArrayList<>();

    @OneToMany(mappedBy = "blog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<HistorialBlog> historial = new ArrayList<>();

    // Constructor por defecto
    public Blog() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    // Constructor con parámetros
    public Blog(String titulo, String tema, String contenido, Periodicidad periodicidad,
            Boolean permiteComentarios, Autor autor) {
        this();
        this.titulo = titulo;
        this.tema = tema;
        this.contenido = contenido;
        this.periodicidad = periodicidad;
        this.permiteComentarios = permiteComentarios;
        this.autor = autor;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public LocalDateTime getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Comentario> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<Comentario> comentarios) {
        this.comentarios = comentarios;
    }

    public List<HistorialBlog> getHistorial() {
        return historial;
    }

    public void setHistorial(List<HistorialBlog> historial) {
        this.historial = historial;
    }

    // nombre completo
    public int getConteoComentarios() {
        return comentarios != null ? comentarios.size() : 0;
    }

    // verifica comentarios
    public boolean tieneComentarios() {
        return comentarios != null && !comentarios.isEmpty();
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.fechaActualizacion = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", periodicidad=" + periodicidad +
                ", permiteComentarios=" + permiteComentarios +
                '}';
    }
}
