package com.focus.focuss_backend.service;

import com.focus.focuss_backend.model.TipoCuenta;
import com.focus.focuss_backend.model.Usuario;
import com.focus.focuss_backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void loginConPasswordCorrectoDevuelveElUsuario() {
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@email.com", "hash-guardado");
        when(usuarioRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("12345678", "hash-guardado")).thenReturn(true);

        Optional<Usuario> resultado = usuarioService.login("juan@email.com", "12345678");

        assertTrue(resultado.isPresent());
        assertEquals("juan@email.com", resultado.get().getEmail());
    }

    @Test
    void loginConPasswordIncorrectoNoDevuelveUsuario() {
        Usuario usuario = new Usuario(1L, "Juan Perez", "juan@email.com", "hash-guardado");
        when(usuarioRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("clave-incorrecta", "hash-guardado")).thenReturn(false);

        Optional<Usuario> resultado = usuarioService.login("juan@email.com", "clave-incorrecta");

        assertTrue(resultado.isEmpty());
    }

    @Test
    void loginConEmailInexistenteNoDevuelveUsuario() {
        when(usuarioRepository.findByEmail("noexiste@email.com")).thenReturn(Optional.empty());

        Optional<Usuario> resultado = usuarioService.login("noexiste@email.com", "12345678");

        assertTrue(resultado.isEmpty());
        verify(passwordEncoder, never()).matches(anyString(), anyString());
    }

    @Test
    void registerConEmailYaExistenteDevuelveNull() {
        Usuario existente = new Usuario(1L, "Juan Perez", "juan@email.com", "hash");
        when(usuarioRepository.findByEmail("juan@email.com")).thenReturn(Optional.of(existente));

        Usuario nuevo = new Usuario(null, "Juan Perez", "juan@email.com", "12345678");
        Usuario resultado = usuarioService.register(nuevo);

        assertNull(resultado);
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void registerConEmailNuevoHasheaElPasswordYAsignaCuentaGratuita() {
        when(usuarioRepository.findByEmail("maria@email.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("12345678")).thenReturn("password-hasheado");
        when(usuarioRepository.save(any(Usuario.class))).thenAnswer(inv -> inv.getArgument(0));

        Usuario nuevo = new Usuario(null, "Maria Garcia", "maria@email.com", "12345678");
        Usuario resultado = usuarioService.register(nuevo);

        assertNotNull(resultado);
        assertEquals("password-hasheado", resultado.getPassword());
        assertEquals(TipoCuenta.GRATUITA, resultado.getTipoCuenta());
    }
}
