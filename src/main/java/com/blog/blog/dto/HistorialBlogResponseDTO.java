package com.blog.blog.dto;

import java.time.LocalDateTime;
import com.blog.blog.entity.Periodicidad;

public class HistorialBlogResponseDTO {
    
    private Long id;
    private Integer version;
    private String tituloAnterior;
    private String temaAnterior;
    private String contenidoAnterior;
    private Periodicidad periodicidadAnterior;
    private Boolean permitiaComentarios;
    private String tipoCambio;
    private LocalDateTime fechaCambio;

    public HistorialBlogResponseDTO() {}

    public HistorialBlogResponseDTO(Long id, Integer version, String tituloAnterior, 
                                  String temaAnterior, String contenidoAnterior, 
                                  Periodicidad periodicidadAnterior, Boolean permitiaComentarios,
                                  String tipoCambio, LocalDateTime fechaCambio) {
        this.id = id;
        this.version = version;
        this.tituloAnterior = tituloAnterior;
        this.temaAnterior = temaAnterior;
        this.contenidoAnterior = contenidoAnterior;
        this.periodicidadAnterior = periodicidadAnterior;
        this.permitiaComentarios = permitiaComentarios;
        this.tipoCambio = tipoCambio;
        this.fechaCambio = fechaCambio;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getTipoCambio() {
        return tipoCambio;
    }

    public void setTipoCambio(String tipoCambio) {
        this.tipoCambio = tipoCambio;
    }

    public LocalDateTime getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDateTime fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    @Override
    public String toString() {
        return "HistorialBlogResponseDTO{" +
                "id=" + id +
                ", version=" + version +
                ", tituloAnterior='" + tituloAnterior + '\'' +
                ", temaAnterior='" + temaAnterior + '\'' +
                ", contenidoAnterior='" + contenidoAnterior + '\'' +
                ", periodicidadAnterior=" + periodicidadAnterior +
                ", permitiaComentarios=" + permitiaComentarios +
                ", tipoCambio='" + tipoCambio + '\'' +
                ", fechaCambio=" + fechaCambio +
                '}';
    }
}