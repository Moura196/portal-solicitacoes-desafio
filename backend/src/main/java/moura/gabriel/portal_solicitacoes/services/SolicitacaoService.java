package moura.gabriel.portal_solicitacoes.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import moura.gabriel.portal_solicitacoes.dtos.AlterarSolicitacaoDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoResponseDTO;
import moura.gabriel.portal_solicitacoes.dtos.UsuarioResponseDTO;
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
        // TODO: Por enquanto que não implementei a função de autenticação, estarei fazendo isso.
        // Preciso implementar o Spring Security para pegar o usuário logado. Retirar usuarioID como
        // parâmetro e usuarioRepository do construtor
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

    public Solicitacao editarSolicitacaoAberta(
        Long solicitacaoID, AlterarSolicitacaoDTO solicitacaoDTO) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoID)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));

        if (solicitacao.getStatus() != Status.ABERTO) {
            throw new IllegalStateException("Só é possível editar uma solicitação que esteja com o status ABERTO!");
        }

        if(solicitacaoDTO.titulo() != null && !solicitacaoDTO.titulo().isBlank()) {
            solicitacao.setTitulo(solicitacaoDTO.titulo());
        }
        
        if(solicitacaoDTO.descricao() != null && !solicitacaoDTO.descricao().isBlank()) {
            solicitacao.setDescricao(solicitacaoDTO.descricao());
        }
        
        if(solicitacaoDTO.categoria() != null) {
            solicitacao.setCategoria(solicitacaoDTO.categoria());
        }
        
        return solicitacaoRepository.save(solicitacao);
    }

    public void excluirSolicitacaoAberta(Long solicitacaoID) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoID)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        
        if (solicitacao.getStatus() == Status.ABERTO) {
            solicitacaoRepository.delete(solicitacao);
        } else {
            throw new IllegalStateException("Só é possível excluir uma solicitação que esteja com o status ABERTO!");
        }
    }

    public List<Solicitacao> listarSolicitacoes() {
        return solicitacaoRepository.findAll();
    }

    public Solicitacao detalharSolicitacao(Long solicitacaoID) {
        Solicitacao solicitacao = solicitacaoRepository.findById(solicitacaoID)
            .orElseThrow(() -> new RuntimeException("Solicitação não encontrada!"));
        return solicitacao;        
    }
    
    public SolicitacaoResponseDTO converterParaDTO(Solicitacao solicitacao) {
        UsuarioResponseDTO usuarioDTO = new UsuarioResponseDTO(
            solicitacao.getUsuario().getId(),
            solicitacao.getUsuario().getNome(),
            solicitacao.getUsuario().getEmail()
        );
        return new SolicitacaoResponseDTO(
            solicitacao.getId(),
            solicitacao.getTitulo(),
            solicitacao.getDescricao(),
            solicitacao.getCategoria(),
            solicitacao.getStatus(),
            solicitacao.getDataAbertura(),
            usuarioDTO
        );
    }
}