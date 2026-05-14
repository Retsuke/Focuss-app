package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> u.getPassword().equals(password));
    }

    public Usuario register(Usuario usuario) {
        Optional<Usuario> existe = usuarioRepository.findByEmail(usuario.getEmail());
        if (existe.isPresent()) return null;
        return usuarioRepository.save(usuario);
    }
}