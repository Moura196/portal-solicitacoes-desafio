package moura.gabriel.portal_solicitacoes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;
import moura.gabriel.portal_solicitacoes.services.SolicitacaoService;


@RestController 
@RequestMapping ("/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Solicitacao> criarSolicitacao(
        @RequestBody SolicitacaoRequestDTO solicitacaoDTO){
        try {
            if(solicitacaoDTO.titulo().isBlank() || solicitacaoDTO.descricao().isBlank() || solicitacaoDTO.categoria() == null){
                return ResponseEntity.badRequest().build();
            }

            // Provisório: passamos o ID 1L diretamente, já que ainda não tenho o Spring Security
            Long usuarioMockId = 1L;
            Solicitacao solicitacao = solicitacaoService.criarSolicitacao(solicitacaoDTO, usuarioMockId);
            return ResponseEntity.ok(solicitacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
