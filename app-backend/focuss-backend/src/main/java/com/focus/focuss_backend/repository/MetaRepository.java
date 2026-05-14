package com.focus.focuss_backend.repository;

import com.focus.focuss_backend.model.Meta;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MetaRepository {

    private List<Meta> metas = new ArrayList<>(List.of(
            new Meta(1L, "Leer 10 libros este año", 40, 1L),
            new Meta(2L, "Aprender Python avanzado", 70, 1L),
            new Meta(3L, "Hacer ejercicio 3 veces/semana", 20, 1L),
            new Meta(4L, "Estudiar inglés diario", 50, 2L)
    ));

    public List<Meta> findAll() {
        return metas;
    }

    public List<Meta> findByUsuarioId(Long usuarioId) {
        return metas.stream()
                .filter(m -> m.getUsuarioId().equals(usuarioId))
                .collect(Collectors.toList());
    }

    public Optional<Meta> findById(Long id) {
        return metas.stream().filter(m -> m.getId().equals(id)).findFirst();
    }

    public Meta save(Meta meta) {
        meta.setId((long) (metas.size() + 1));
        metas.add(meta);
        return meta;
    }
}