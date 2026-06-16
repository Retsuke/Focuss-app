package com.focus.focuss_backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private int tiempo;
    private boolean done;
    private Long usuarioId;

    public Tarea() {}

    public Tarea(Long id, String nombre, int tiempo, boolean done, Long usuarioId) {
        this.id = id;
        this.nombre = nombre;
        this.tiempo = tiempo;
        this.done = done;
        this.usuarioId = usuarioId;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public int getTiempo() { return tiempo; }
    public boolean isDone() { return done; }
    public Long getUsuarioId() { return usuarioId; }

    public void setId(Long id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTiempo(int tiempo) { this.tiempo = tiempo; }
    public void setDone(boolean done) { this.done = done; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
}