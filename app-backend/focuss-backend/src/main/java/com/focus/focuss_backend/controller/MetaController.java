package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.model.Meta;
import com.focus.focuss_backend.service.MetaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public ResponseEntity<List<Meta>> getMetasByUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(metaService.findByUsuarioId(usuarioId));
    }

    @PostMapping
    public ResponseEntity<Meta> crearMeta(@RequestBody Meta meta) {
        return ResponseEntity.ok(metaService.save(meta));
    }
}