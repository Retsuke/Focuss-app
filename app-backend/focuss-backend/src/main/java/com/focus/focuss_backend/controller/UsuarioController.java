package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.dto.UsuarioResponse;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PutMapping("/{id}/upgrade")
    public ResponseEntity<?> upgradeAPremium(@PathVariable Long id,
                                              @AuthenticationPrincipal Long authUsuarioId) {
        if (!id.equals(authUsuarioId)) {
            return ResponseEntity.status(403).body(Map.of("error", "No autorizado"));
        }
        Optional<Usuario> actualizado = usuarioService.upgradeToPremium(id);
        if (actualizado.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(UsuarioResponse.from(actualizado.get()));
    }
}
