package moura.gabriel.portal_solicitacoes.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import moura.gabriel.portal_solicitacoes.exceptions.TokenInvalidoException;
import moura.gabriel.portal_solicitacoes.models.Usuario;

public class TokenServiceTest {

    private TokenService tokenService;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "my-secret-key-super-secure");

        usuario = new Usuario();
        usuario.setEmail("test@test.com");
    }

    @Test
    @DisplayName("Deve gerar um token válido")
    void deveGerarToken() {
        String token = tokenService.generateToken(usuario);
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    @DisplayName("Deve validar um token e retornar o email (subject)")
    void deveValidarToken() {
        String token = tokenService.generateToken(usuario);
        String subject = tokenService.validateToken(token);
        assertEquals("test@test.com", subject);
    }

    @Test
    @DisplayName("Deve lançar exceção ao validar token inválido")
    void deveLancarExcecaoTokenInvalido() {
        assertThrows(TokenInvalidoException.class, () -> tokenService.validateToken("invalid-token"));
    }
}
