package com.blog.blog.dto;

import com.blog.blog.entity.Periodicidad;
import java.time.LocalDateTime;
import java.util.List;

public class BlogResponseDTO {

    private Long id;
    private String titulo;
    private String tema;
    private String contenido;
    private Periodicidad periodicidad;
    private Boolean permiteComentarios;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    // autor
    private AutorResponseDTO autor;

    // Comentarios
    private List<ComentarioResponseDTO> comentarios;
    private Integer totalComentarios;

    // estadísticas
    private ResumenPuntuacionesDTO resumenPuntuaciones;

    public BlogResponseDTO() {
    }

    public BlogResponseDTO(Long id, String titulo, String tema, String contenido,
            Periodicidad periodicidad, Boolean permiteComentarios,
            LocalDateTime fechaCreacion, LocalDateTime fechaActualizacion) {
        this.id = id;
        this.titulo = titulo;
        this.tema = tema;
        this.contenido = contenido;
        this.periodicidad = periodicidad;
        this.permiteComentarios = permiteComentarios;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.totalComentarios = 0;
    }

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

    public AutorResponseDTO getAutor() {
        return autor;
    }

    public void setAutor(AutorResponseDTO autor) {
        this.autor = autor;
    }

    public List<ComentarioResponseDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioResponseDTO> comentarios) {
        this.comentarios = comentarios;
        this.totalComentarios = comentarios != null ? comentarios.size() : 0;
    }

    public Integer getTotalComentarios() {
        return totalComentarios;
    }

    public void setTotalComentarios(Integer totalComentarios) {
        this.totalComentarios = totalComentarios;
    }

    public ResumenPuntuacionesDTO getResumenPuntuaciones() {
        return resumenPuntuaciones;
    }

    public void setResumenPuntuaciones(ResumenPuntuacionesDTO resumenPuntuaciones) {
        this.resumenPuntuaciones = resumenPuntuaciones;
    }

    @Override
    public String toString() {
        return "BlogResponseDTO{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", tema='" + tema + '\'' +
                ", periodicidad=" + periodicidad +
                ", permiteComentarios=" + permiteComentarios +
                ", totalComentarios=" + totalComentarios +
                '}';
    }

    // resumen de puntuaciones
    public static class ResumenPuntuacionesDTO {
        private Integer minimo;
        private Integer maximo;
        private Double promedio;
        private Long totalVotos;

        public ResumenPuntuacionesDTO() {
        }

        public ResumenPuntuacionesDTO(Integer minimo, Integer maximo, Double promedio, Long totalVotos) {
            this.minimo = minimo;
            this.maximo = maximo;
            this.promedio = promedio;
            this.totalVotos = totalVotos;
        }

        public Integer getMinimo() {
            return minimo;
        }

        public void setMinimo(Integer minimo) {
            this.minimo = minimo;
        }

        public Integer getMaximo() {
            return maximo;
        }

        public void setMaximo(Integer maximo) {
            this.maximo = maximo;
        }

        public Double getPromedio() {
            return promedio;
        }

        public void setPromedio(Double promedio) {
            this.promedio = promedio;
        }

        public Long getTotalVotos() {
            return totalVotos;
        }

        public void setTotalVotos(Long totalVotos) {
            this.totalVotos = totalVotos;
        }

        @Override
        public String toString() {
            return "ResumenPuntuacionesDTO{" +
                    "minimo=" + minimo +
                    ", maximo=" + maximo +
                    ", promedio=" + promedio +
                    ", totalVotos=" + totalVotos +
                    '}';
        }
    }

    // respuesta de comentarios
    public static class ComentarioResponseDTO {
        private Long id;
        private String nombreComentarista;
        private String emailComentarista;
        private String paisComentarista;
        private String contenido;
        private Integer puntuacion;
        private LocalDateTime fechaComentario;

        public ComentarioResponseDTO() {
        }

        public ComentarioResponseDTO(Long id, String nombreComentarista, String emailComentarista,
                String paisComentarista, String contenido, Integer puntuacion,
                LocalDateTime fechaComentario) {
            this.id = id;
            this.nombreComentarista = nombreComentarista;
            this.emailComentarista = emailComentarista;
            this.paisComentarista = paisComentarista;
            this.contenido = contenido;
            this.puntuacion = puntuacion;
            this.fechaComentario = fechaComentario;
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

        @Override
        public String toString() {
            return "ComentarioResponseDTO{" +
                    "id=" + id +
                    ", nombreComentarista='" + nombreComentarista + '\'' +
                    ", puntuacion=" + puntuacion +
                    ", fechaComentario=" + fechaComentario +
                    '}';
        }
    }
}
