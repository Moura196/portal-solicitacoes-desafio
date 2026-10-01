package moura.gabriel.portal_solicitacoes.services;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;
import moura.gabriel.portal_solicitacoes.models.Status;
import moura.gabriel.portal_solicitacoes.models.Usuario;
import moura.gabriel.portal_solicitacoes.repositories.SolicitacaoRepository;
import moura.gabriel.portal_solicitacoes.repositories.UsuarioRepository;

@Service 
public class SolicitacaoService {

    private final SolicitacaoRepository solicitacaoRepository;
    private final UsuarioRepository usuarioRepository;

    public SolicitacaoService(SolicitacaoRepository solicitacaoRepository, UsuarioRepository usuarioRepository) {
        this.solicitacaoRepository = solicitacaoRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Solicitacao criarSolicitacao(SolicitacaoRequestDTO solicitacaoDTO, 
        Long usuarioID) {
        // todo: Por enquanto que não implementei a função de autenticação, estarei fazendo isso.
        // Preciso implementar o Spring Security para pegar o usuário logado
        Usuario usuario = usuarioRepository.findById(usuarioID)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        
        Solicitacao solicitacao = new Solicitacao();
        solicitacao.setTitulo(solicitacaoDTO.titulo());
        solicitacao.setDescricao(solicitacaoDTO.descricao());
        solicitacao.setCategoria(solicitacaoDTO.categoria());
        solicitacao.setStatus(Status.ABERTO);
        solicitacao.setUsuario(usuario);
        solicitacao.setDataAbertura(LocalDateTime.now());

        return solicitacaoRepository.save(solicitacao);
    }
    
}
