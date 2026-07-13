package com.focus.focuss_backend.controller;

import com.focus.focuss_backend.dto.AuthResponse;
import com.focus.focuss_backend.dto.UsuarioResponse;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.security.JwtService;
import com.focus.focuss_backend.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioService usuarioService;
    private final JwtService jwtService;

    public AuthController(UsuarioService usuarioService, JwtService jwtService) {
        this.usuarioService = usuarioService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email    = body.get("email");
        String password = body.get("password");
        Optional<Usuario> usuario = usuarioService.login(email, password);

        if (usuario.isPresent()) {
            String token = jwtService.generateToken(usuario.get());
            return ResponseEntity.ok(new AuthResponse(UsuarioResponse.from(usuario.get()), token));
        }
        return ResponseEntity.status(401).body(Map.of("error", "Email o contraseña incorrectos"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.register(usuario);

        if (nuevo == null) {
            return ResponseEntity.status(409).body(Map.of("error", "Ya existe una cuenta con ese email"));
        }
        String token = jwtService.generateToken(nuevo);
        return ResponseEntity.ok(new AuthResponse(UsuarioResponse.from(nuevo), token));
    }
}
