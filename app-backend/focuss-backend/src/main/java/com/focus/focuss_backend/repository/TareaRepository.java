package com.focus.focuss_backend.repository;

import com.focus.focuss_backend.model.Tarea;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class TareaRepository {

    private List<Tarea> tareas = new ArrayList<>(List.of(
            new Tarea(1L, "Estudiar matemáticas", 25, false, 1L),
            new Tarea(2L, "Leer capítulo 3", 30, false, 1L),
            new Tarea(3L, "Hacer ejercicios", 45, true, 1L),
            new Tarea(4L, "Repasar apuntes", 20, false, 2L)
    ));

    public List<Tarea> findAll() {
        return tareas;
    }

    public List<Tarea> findByUsuarioId(Long usuarioId) {
        return tareas.stream()
                .filter(t -> t.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    public Optional<Tarea> findById(Long id) {
        return tareas.stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public Tarea save(Tarea tarea) {
        tarea.setId((long) (tareas.size() + 1));
        tareas.add(tarea);
        return tarea;
    }

    public boolean deleteById(Long id) {
        return tareas.removeIf(t -> t.getId().equals(id));
    }

    public Tarea update(Tarea tarea) {
        for (int i = 0; i < tareas.size(); i++) {
            if (tareas.get(i).getId().equals(tarea.getId())) {
                tareas.set(i, tarea);
                return tarea;
            }
        }
        return null;
    }
}