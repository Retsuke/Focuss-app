package com.focus.focuss_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "metas")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descripcion;
    private int progreso;
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