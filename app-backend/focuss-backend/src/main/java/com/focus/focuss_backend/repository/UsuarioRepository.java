package com.focus.focuss_backend.repository;

import com.focus.focuss_backend.model.Usuario;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {

    private List<Usuario> usuarios = new ArrayList<>(List.of(
            new Usuario(1L, "Juan Pérez", "juan@email.com", "12345678"),
            new Usuario(2L, "María García", "maria@email.com", "12345678")
    ));

    public List<Usuario> findAll() {
        return usuarios;
    }

    public Optional<Usuario> findById(Long id) {
        return usuarios.stream().filter(u -> u.getId().equals(id)).findFirst();
    }

    public Optional<Usuario> findByEmail(String email) {
        return usuarios.stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    public Usuario save(Usuario usuario) {
        usuario.setId((long) (usuarios.size() + 1));
        usuarios.add(usuario);
        return usuario;
    }
}