package moura.gabriel.portal_solicitacoes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import moura.gabriel.portal_solicitacoes.models.Solicitacao;

public interface SolicitacaoRepository extends JpaRepository<Solicitacao, Long> {
    
}
