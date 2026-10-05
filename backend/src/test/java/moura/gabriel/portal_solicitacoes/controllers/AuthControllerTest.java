package moura.gabriel.portal_solicitacoes.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import moura.gabriel.portal_solicitacoes.dtos.AuthRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.LoginResponseDTO;
import moura.gabriel.portal_solicitacoes.models.Usuario;
import moura.gabriel.portal_solicitacoes.security.TokenService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenService tokenService;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private AuthController authController;

    @Test
    @DisplayName("Deve realizar login e retornar o token")
    void deveRealizarLogin() {
        AuthRequestDTO authDTO = new AuthRequestDTO("test@test.com", "123456");
        Usuario usuario = new Usuario();
        usuario.setEmail("test@test.com");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        when(tokenService.generateToken(usuario)).thenReturn("mocked-jwt-token");

        ResponseEntity<LoginResponseDTO> response = authController.login(authDTO);

        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals("mocked-jwt-token", response.getBody().token());
    }
}
