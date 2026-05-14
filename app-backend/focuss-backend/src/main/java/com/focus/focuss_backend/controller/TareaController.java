package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.model.Tarea;
import com.focus.focuss_backend.service.TareaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = "*")
public class TareaController {

    private final TareaService tareaService;

    public TareaController(TareaService tareaService) {
        this.tareaService = tareaService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Tarea>> getTareasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(tareaService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Tarea> crearTarea(@RequestBody Tarea tarea) {
        return ResponseEntity.ok(tareaService.save(tarea));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Long id, @RequestBody Tarea tarea) {
        tarea.setId(id);
        Tarea actualizada = tareaService.update(tarea);
        if (actualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id) {
        boolean eliminada = tareaService.deleteById(id);
        if (!eliminada) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(Map.of("mensaje", "Tarea eliminada"));
    }
}