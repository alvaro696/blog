package com.blog.blog.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "historial_blogs")
public class HistorialBlog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El blog es obligatorio")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blog_id", nullable = false)
    private Blog blog;

    @NotNull(message = "La versión es obligatoria")
    @Column(nullable = false)
    private Integer version;

    @Size(max = 200, message = "El título anterior no puede exceder 200 caracteres")
    @Column(name = "titulo_anterior", length = 200)
    private String tituloAnterior;

    @Size(max = 100, message = "El tema anterior no puede exceder 100 caracteres")
    @Column(name = "tema_anterior", length = 100)
    private String temaAnterior;

    @Column(name = "contenido_anterior", columnDefinition = "TEXT")
    private String contenidoAnterior;

    @Enumerated(EnumType.STRING)
    @Column(name = "periodicidad_anterior")
    private Periodicidad periodicidadAnterior;

    @Column(name = "permitia_comentarios")
    private Boolean permitiaComentarios;

    @NotNull(message = "La fecha de cambio es obligatoria")
    @Column(name = "fecha_cambio", nullable = false)
    private LocalDateTime fechaCambio;

    @NotBlank(message = "El tipo de cambio es obligatorio")
    @Size(max = 500, message = "El tipo de cambio no puede exceder 500 caracteres")
    @Column(name = "tipo_cambio", nullable = false, length = 500)
    private String tipoCambio;

    public HistorialBlog() {
        this.fechaCambio = LocalDateTime.now();
    }

    public HistorialBlog(Blog blog, Integer version, String tituloAnterior, String temaAnterior,
            String contenidoAnterior, Periodicidad periodicidadAnterior,
            Boolean permitiaComentarios, String tipoCambio) {
        this();
        this.blog = blog;
        this.version = version;
        this.tituloAnterior = tituloAnterior;
        this.temaAnterior = temaAnterior;
        this.contenidoAnterior = contenidoAnterior;
        this.periodicidadAnterior = periodicidadAnterior;
        this.permitiaComentarios = permitiaComentarios;
        this.tipoCambio = tipoCambio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getTituloAnterior() {
        return tituloAnterior;
    }

    public void setTituloAnterior(String tituloAnterior) {
        this.tituloAnterior = tituloAnterior;
    }

    public String getTemaAnterior() {
        return temaAnterior;
    }

    public void setTemaAnterior(String temaAnterior) {
        this.temaAnterior = temaAnterior;
    }

    public String getContenidoAnterior() {
        return contenidoAnterior;
    }

    public void setContenidoAnterior(String contenidoAnterior) {
        this.contenidoAnterior = contenidoAnterior;
    }

    public Periodicidad getPeriodicidadAnterior() {
        return periodicidadAnterior;
    }

    public void setPeriodicidadAnterior(Periodicidad periodicidadAnterior) {
        this.periodicidadAnterior = periodicidadAnterior;
    }

    public Boolean getPermitiaComentarios() {
        return permitiaComentarios;
    }

    public void setPermitiaComentarios(Boolean permitiaComentarios) {
        this.permitiaComentarios = permitiaComentarios;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    @PrePersist
    protected void onCreate() {
        this.fechaCambio = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "HistorialBlog{" +
                "id=" + id +
                ", version=" + version +
                ", tipoCambio='" + tipoCambio + '\'' +
                ", fechaCambio=" + fechaCambio +
                '}';
    }
}
