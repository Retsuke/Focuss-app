package com.focus.focuss_backend.dto;

import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;

public record UsuarioResponse(Long id, String nombre, String email, TipoCuenta tipoCuenta) {

    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNombre(), usuario.getEmail(), usuario.getTipoCuenta());
    }
}
