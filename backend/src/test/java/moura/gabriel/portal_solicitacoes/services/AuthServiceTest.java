package moura.gabriel.portal_solicitacoes.services;

import moura.gabriel.portal_solicitacoes.models.Usuario;
import moura.gabriel.portal_solicitacoes.repositories.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setEmail("test@test.com");
        usuario.setSenha("123456");
    }

    @Test
    @DisplayName("Deve carregar o usuário por email quando ele existir")
    void deveCarregarUsuario() {
        when(usuarioRepository.findByEmail("test@test.com")).thenReturn(Optional.of(usuario));

        UserDetails result = authService.loadUserByUsername("test@test.com");

        assertEquals(usuario, result);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o usuário não existir")
    void deveLancarExcecao() {
        when(usuarioRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> authService.loadUserByUsername("notfound@test.com"));
    }
}
