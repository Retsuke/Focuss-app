package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.Meta;
import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.MetaRepository;
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
class MetaServiceTest {

    @Mock
    private MetaRepository metaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private MetaService metaService;

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
    void cuentaGratuitaBajoElLimitePuedeCrearMeta() {
        Usuario usuario = usuarioGratuito();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(metaRepository.findByUsuarioId(1L)).thenReturn(Collections.nCopies(1, new Meta()));
        when(metaRepository.save(any(Meta.class))).thenAnswer(inv -> inv.getArgument(0));

        Meta nueva = new Meta(null, "Leer 10 libros", 0, 1L);
        Meta resultado = metaService.save(nueva);

        assertNotNull(resultado);
    }

    @Test
    void cuentaGratuitaEnElLimiteNoPuedeCrearMasMetas() {
        Usuario usuario = usuarioGratuito();
        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario));
        when(metaRepository.findByUsuarioId(1L)).thenReturn(Collections.nCopies(2, new Meta()));

        Meta tercera = new Meta(null, "Meta numero 3", 0, 1L);
        Meta resultado = metaService.save(tercera);

        assertNull(resultado);
        verify(metaRepository, never()).save(any());
    }

    @Test
    void cuentaPremiumPuedeSuperarElLimiteDeDosMetas() {
        Usuario usuario = usuarioPremium();
        when(usuarioRepository.findById(2L)).thenReturn(Optional.of(usuario));
        when(metaRepository.save(any(Meta.class))).thenAnswer(inv -> inv.getArgument(0));

        Meta quinta = new Meta(null, "Meta numero 5", 0, 2L);
        Meta resultado = metaService.save(quinta);

        assertNotNull(resultado);
        verify(metaRepository, never()).findByUsuarioId(any());
    }
}
