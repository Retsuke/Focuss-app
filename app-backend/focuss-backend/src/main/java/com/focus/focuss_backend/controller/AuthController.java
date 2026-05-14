package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.service.UsuarioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;

    public AuthController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");
        Optional<Usuario> usuario = usuarioService.login(email, password);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        }
        return ResponseEntity.status(401).body(Map.of("error", "Email o contraseña incorrectos"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.register(usuario);

        if (nuevo == null) {
            return ResponseEntity.status(409).body(Map.of("error", "Ya existe una cuenta con ese email"));
        }
        return ResponseEntity.ok(nuevo);
    }
}