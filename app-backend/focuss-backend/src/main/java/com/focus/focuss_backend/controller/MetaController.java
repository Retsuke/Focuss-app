package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.model.Meta;
import com.focus.focuss_backend.service.MetaService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/metas")
@CrossOrigin(origins = "*")
public class MetaController {

    private final MetaService metaService;

    public MetaController(MetaService metaService) {
        this.metaService = metaService;
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> getMetasByUsuario(@PathVariable Long usuarioId,
                                                @AuthenticationPrincipal Long authUsuarioId) {
        if (!usuarioId.equals(authUsuarioId)) {
            return ResponseEntity.status(403).body(Map.of("error", "No autorizado"));
        }
        return ResponseEntity.ok(metaService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<?> crearMeta(@Valid @RequestBody Meta meta,
                                        @AuthenticationPrincipal Long authUsuarioId) {
        meta.setUsuarioId(authUsuarioId);
        Meta creada = metaService.save(meta);
        if (creada == null) {
            return ResponseEntity.status(402).body(Map.of("error",
                    "Límite de metas alcanzado. Actualiza a Premium para metas ilimitadas."));
        }
        return ResponseEntity.ok(creada);
    }
}
