package moura.gabriel.portal_solicitacoes.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import moura.gabriel.portal_solicitacoes.dtos.AlterarSolicitacaoDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoResponseDTO;
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
    public ResponseEntity<SolicitacaoResponseDTO> criarSolicitacao(
        @Valid @RequestBody SolicitacaoRequestDTO solicitacaoDTO){
        try {
            // TODO: Provisório: passamos o ID 1L diretamente, já que ainda não tenho o Spring Security
            Long usuarioMockId = 1L;
            Solicitacao solicitacao = solicitacaoService.criarSolicitacao(solicitacaoDTO, usuarioMockId);
            return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacao));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("editar/{solicitacaoID}")
    public ResponseEntity<SolicitacaoResponseDTO> editarSolicitacaoAberta(
        @PathVariable Long solicitacaoID, @RequestBody AlterarSolicitacaoDTO solicitacaoDTO) {
        try {
            Solicitacao solicitacaoEditada = solicitacaoService.editarSolicitacaoAberta(solicitacaoID, solicitacaoDTO);
            return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacaoEditada));
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/excluir/{solicitacaoID}")
    public ResponseEntity<Solicitacao> excluirSolicitacaoAberta(
        @PathVariable Long solicitacaoID) {
        try {
            solicitacaoService.excluirSolicitacaoAberta(solicitacaoID);
            return ResponseEntity.ok().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarSolicitacoes() {
        try {
            List<Solicitacao> solicitacoes = solicitacaoService.listarSolicitacoes();
            return ResponseEntity.ok(solicitacoes.stream()
                .map(solicitacaoService::converterParaDTO)
                .toList());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/detalhar/{solicitacaoID}")
    public ResponseEntity<SolicitacaoResponseDTO> detalharSolicitacao(
        @PathVariable Long solicitacaoID) {
        try {
            Solicitacao solicitacao = solicitacaoService.detalharSolicitacao(solicitacaoID);
            return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacao));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }    
    
}
