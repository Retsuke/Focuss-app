package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Meta;
import com.focus.focuss_backend.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetaService {

    private final MetaRepository metaRepository;

    public MetaService(MetaRepository metaRepository) {
        this.metaRepository = metaRepository;
    }

    public List<Meta> findByUsuarioId(Long usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Meta> findById(Long id) {
        return metaRepository.findById(id);
    }

    public Meta save(Meta meta) {
        return metaRepository.save(meta);
    }
}