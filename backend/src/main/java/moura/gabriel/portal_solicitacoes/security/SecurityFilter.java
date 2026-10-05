package moura.gabriel.portal_solicitacoes.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.filter.OncePerRequestFilter;

import moura.gabriel.portal_solicitacoes.exceptions.TokenInvalidoException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import moura.gabriel.portal_solicitacoes.repositories.UsuarioRepository;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UsuarioRepository usuarioRepository;
    
    private final HandlerExceptionResolver resolver;

    public SecurityFilter(TokenService tokenService, UsuarioRepository usuarioRepository, 
            @Qualifier("handlerExceptionResolver") HandlerExceptionResolver resolver) {
        this.tokenService = tokenService;
        this.usuarioRepository = usuarioRepository;
        this.resolver = resolver;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var email = tokenService.validateToken(token);
                UserDetails user = usuarioRepository.findByEmail(email).orElse(null);

                if (user != null) {
                    var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(request, response);
        } catch (TokenInvalidoException ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}
