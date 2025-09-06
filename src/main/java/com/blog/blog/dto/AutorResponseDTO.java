package com.blog.blog.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AutorResponseDTO {

    private Long id;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String nombreCompleto;
    private LocalDate fechaNacimiento;
    private String paisResidencia;
    private String email;
    private LocalDateTime fechaCreacion;
    private Integer totalBlogs;

    public AutorResponseDTO() {
    }

    public AutorResponseDTO(Long id, String nombres, String apellidoPaterno, String apellidoMaterno,
            LocalDate fechaNacimiento, String paisResidencia, String email,
            LocalDateTime fechaCreacion) {
        this.id = id;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.nombreCompleto = String.format("%s %s %s", nombres, apellidoPaterno, apellidoMaterno);
        this.fechaNacimiento = fechaNacimiento;
        this.paisResidencia = paisResidencia;
        this.email = email;
        this.fechaCreacion = fechaCreacion;
        this.totalBlogs = 0;
    }

    // getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
        updateNombreCompleto();
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
        updateNombreCompleto();
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
        updateNombreCompleto();
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPaisResidencia() {
        return paisResidencia;
    }

    public void setPaisResidencia(String paisResidencia) {
        this.paisResidencia = paisResidencia;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Integer getTotalBlogs() {
        return totalBlogs;
    }

    public void setTotalBlogs(Integer totalBlogs) {
        this.totalBlogs = totalBlogs;
    }

    private void updateNombreCompleto() {
        if (nombres != null && apellidoPaterno != null && apellidoMaterno != null) {
            this.nombreCompleto = String.format("%s %s %s", nombres, apellidoPaterno, apellidoMaterno);
        }
    }

    @Override
    public String toString() {
        return "AutorResponseDTO{" +
                "id=" + id +
                ", nombreCompleto='" + nombreCompleto + '\'' +
                ", email='" + email + '\'' +
                ", totalBlogs=" + totalBlogs +
                '}';
    }
}
