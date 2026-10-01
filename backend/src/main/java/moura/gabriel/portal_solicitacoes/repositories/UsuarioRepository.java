package moura.gabriel.portal_solicitacoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import moura.gabriel.portal_solicitacoes.models.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
        
}