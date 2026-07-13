package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.model.Tarea;
import com.focus.focuss_backend.service.TareaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> getTareasByUsuario(@PathVariable Long usuarioId,
                                                 @AuthenticationPrincipal Long authUsuarioId) {
        if (!usuarioId.equals(authUsuarioId)) {
            return ResponseEntity.status(403).body(Map.of("error", "No autorizado"));
        }
        return ResponseEntity.ok(tareaService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> crearTarea(@Valid @RequestBody Tarea tarea,
                                         @AuthenticationPrincipal Long authUsuarioId) {
        tarea.setUsuarioId(authUsuarioId);
        Tarea creada = tareaService.save(tarea);
        if (creada == null) {
            return ResponseEntity.status(402).body(Map.of("error",
                    "Límite de tareas alcanzado. Actualiza a Premium para tareas ilimitadas."));
        }
        return ResponseEntity.ok(creada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarTarea(@PathVariable Long id,
                                              @Valid @RequestBody Tarea tarea,
                                              @AuthenticationPrincipal Long authUsuarioId) {
        Optional<Tarea> existente = tareaService.findById(id);
        if (existente.isEmpty() || !existente.get().getUsuarioId().equals(authUsuarioId)) {
            return ResponseEntity.notFound().build();
        }
        tarea.setId(id);
        tarea.setUsuarioId(authUsuarioId);
        Tarea actualizada = tareaService.update(tarea);
        return ResponseEntity.ok(actualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarTarea(@PathVariable Long id,
                                            @AuthenticationPrincipal Long authUsuarioId) {
        Optional<Tarea> existente = tareaService.findById(id);
        if (existente.isEmpty() || !existente.get().getUsuarioId().equals(authUsuarioId)) {
            return ResponseEntity.notFound().build();
        }
        tareaService.deleteById(id);
        return ResponseEntity.ok(Map.of("mensaje", "Tarea eliminada"));
    }
}
