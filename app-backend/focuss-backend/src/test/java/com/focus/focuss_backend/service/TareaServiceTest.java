package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Tarea;
import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.TareaRepository;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TareaServiceTest {

    @Mock
    private TareaRepository tareaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private TareaService tareaService;

    private Usuario usuarioGratuito() {
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@email.com", "hash");
        usuario.setTipoCuenta(TipoCuenta.GRATUITA);
        return usuario;
    }

    private Usuario usuarioPremium() {
        Usuario usuario = new Usuario(2L, "Maria Garcia", "maria@email.com", "hash");
        usuario.setTipoCuenta(TipoCuenta.PREMIUM);
        return usuario;
    }

    @Test
    void cuentaGratuitaBajoElLimitePuedeCrearTarea() {
        Usuario usuario = usuarioGratuito();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.findByUsuarioId(1L)).thenReturn(Collections.nCopies(4, new Tarea()));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        Tarea nueva = new Tarea(null, "Estudiar", 25, false, 1L);
        Tarea resultado = tareaService.save(nueva);

        assertNotNull(resultado);
        verify(tareaRepository).save(nueva);
    }

    @Test
    void cuentaGratuitaEnElLimiteNoPuedeCrearMasTareas() {
        Usuario usuario = usuarioGratuito();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.findByUsuarioId(1L)).thenReturn(Collections.nCopies(5, new Tarea()));

        Tarea sexta = new Tarea(null, "Tarea numero 6", 25, false, 1L);
        Tarea resultado = tareaService.save(sexta);

        assertNull(resultado);
        verify(tareaRepository, never()).save(any());
    }

    @Test
    void cuentaPremiumPuedeSuperarElLimiteDeCincoTareas() {
        Usuario usuario = usuarioPremium();
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        Tarea decima = new Tarea(null, "Tarea numero 10", 40, false, 2L);
        Tarea resultado = tareaService.save(decima);

        assertNotNull(resultado);
        verify(tareaRepository, never()).findByUsuarioId(any());
    }

    @Test
    void cuentaGratuitaForzaElTiempoFijoDeVeinticincoMinutos() {
        Usuario usuario = usuarioGratuito();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.findByUsuarioId(1L)).thenReturn(List.of());
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        Tarea tarea = new Tarea(null, "Estudiar", 99, false, 1L);
        Tarea resultado = tareaService.save(tarea);

        assertEquals(25, resultado.getTiempo());
    }

    @Test
    void cuentaPremiumRespetaElTiempoPersonalizado() {
        Usuario usuario = usuarioPremium();
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(tareaRepository.save(any(Tarea.class))).thenAnswer(inv -> inv.getArgument(0));

        Tarea tarea = new Tarea(null, "Sesion larga", 90, false, 2L);
        Tarea resultado = tareaService.save(tarea);

        assertEquals(90, resultado.getTiempo());
    }
}
