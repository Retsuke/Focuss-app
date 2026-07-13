package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Tarea;
import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.TareaRepository;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TareaService {

    private static final int LIMITE_TAREAS_GRATUITA = 5;
    private static final int TIEMPO_FIJO_GRATUITA = 25;

    private final TareaRepository tareaRepository;
    private final UsuarioRepository usuarioRepository;

    public TareaService(TareaRepository tareaRepository, UsuarioRepository usuarioRepository) {
        this.tareaRepository = tareaRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public List<Tarea> findByUsuarioId(Long usuarioId) {
        return tareaRepository.findByUsuarioId(usuarioId);
    }

    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    public Tarea save(Tarea tarea) {
        Usuario usuario = usuarioRepository.findById(tarea.getUsuarioId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (usuario.getTipoCuenta() == TipoCuenta.GRATUITA) {
            long actuales = tareaRepository.findByUsuarioId(tarea.getUsuarioId()).size();
            if (actuales >= LIMITE_TAREAS_GRATUITA) {
                return null;
            }
            tarea.setTiempo(TIEMPO_FIJO_GRATUITA);
        }

        return tareaRepository.save(tarea);
    }

    public Tarea update(Tarea tarea) {
        Usuario usuario = usuarioRepository.findById(tarea.getUsuarioId())
                .orElseThrow(() -> new IllegalStateException("Usuario no encontrado"));

        if (usuario.getTipoCuenta() == TipoCuenta.GRATUITA) {
            tarea.setTiempo(TIEMPO_FIJO_GRATUITA);
        }

        return tareaRepository.save(tarea);
    }

    public boolean deleteById(Long id) {
        if (tareaRepository.existsById(id)) {
            tareaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
