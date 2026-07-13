package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Meta;
import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.MetaRepository;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private static final int LIMITE_METAS_GRATUITA = 2;

    private final MetaRepository metaRepository;
    private final UsuarioRepository usuarioRepository;

    public MetaService(MetaRepository metaRepository, UsuarioRepository usuarioRepository) {
        this.metaRepository = metaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Meta> findByUsuarioId(Long usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Meta> findById(Long id) {
        return metaRepository.findById(id);
    }

    public Meta save(Meta meta) {
        Usuario usuario = usuarioRepository.findById(meta.getUsuarioId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (usuario.getTipoCuenta() == TipoCuenta.GRATUITA) {
            long actuales = metaRepository.findByUsuarioId(meta.getUsuarioId()).size();
            if (actuales >= LIMITE_METAS_GRATUITA) {
                return null;
            }
        }

        return metaRepository.save(meta);
    }
}
