package com.focus.focuss_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "La descripción es obligatoria")
    private String descripcion;

    @Min(value = 0, message = "El progreso no puede ser negativo")
    @Max(value = 100, message = "El progreso no puede ser mayor a 100")
    private int progreso;

    @NotNull(message = "El usuarioId es obligatorio")
    private Long usuarioId;

    public Meta() {}

    public Meta(Long id, String descripcion, int progreso, Long usuarioId) {
        this.id = id;
        this.descripcion = descripcion;
        this.progreso = progreso;
        this.usuarioId = usuarioId;
    }

    public Long getId() { return id; }
    public String getDescripcion() { return descripcion; }
    public int getProgreso() { return progreso; }
    public Long getUsuarioId() { return usuarioId; }

    public void setId(Long id) { this.id = id; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setProgreso(int progreso) { this.progreso = progreso; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}