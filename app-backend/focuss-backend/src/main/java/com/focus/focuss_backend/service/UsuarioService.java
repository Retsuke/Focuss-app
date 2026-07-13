package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> login(String email, String password) {
        return usuarioRepository.findByEmail(email)
                .filter(u -> passwordEncoder.matches(password, u.getPassword()));
    }

    public Usuario register(Usuario usuario) {
        Optional<Usuario> existe = usuarioRepository.findByEmail(usuario.getEmail());
        if (existe.isPresent()) return null;
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setTipoCuenta(TipoCuenta.GRATUITA);
        return usuarioRepository.save(usuario);
    }

    public Optional<Usuario> upgradeToPremium(Long id) {
        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setTipoCuenta(TipoCuenta.PREMIUM);
            return usuarioRepository.save(usuario);
        });
    }
}