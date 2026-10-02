package moura.gabriel.portal_solicitacoes.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import moura.gabriel.portal_solicitacoes.dtos.AlterarSolicitacaoDTO;
import moura.gabriel.portal_solicitacoes.dtos.AlterarStatusRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.DashboardDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoFiltroDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoResponseDTO;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;
import moura.gabriel.portal_solicitacoes.models.Status;
import moura.gabriel.portal_solicitacoes.services.SolicitacaoService;

@RestController
@RequestMapping("/solicitacoes")
public class SolicitacaoController {

    private final SolicitacaoService solicitacaoService;

    public SolicitacaoController(SolicitacaoService solicitacaoService) {
        this.solicitacaoService = solicitacaoService;
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<SolicitacaoResponseDTO> criarSolicitacao(
        @Valid @RequestBody SolicitacaoRequestDTO solicitacaoDTO) {
        // TODO: Provisório: passamos o ID 1L diretamente, já que ainda não tenho o Spring Security
        Long usuarioMockId = 1L;
        Solicitacao solicitacao = solicitacaoService.criarSolicitacao(solicitacaoDTO, usuarioMockId);
        return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacao));
    }

    @PatchMapping("editar/{solicitacaoID}")
    public ResponseEntity<SolicitacaoResponseDTO> editarSolicitacaoAberta(
        @PathVariable Long solicitacaoID, @RequestBody AlterarSolicitacaoDTO solicitacaoDTO) {
        Solicitacao solicitacaoEditada = solicitacaoService.editarSolicitacaoAberta(solicitacaoID, solicitacaoDTO);
        return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacaoEditada));
    }

    @DeleteMapping("/excluir/{solicitacaoID}")
    public ResponseEntity<Void> excluirSolicitacaoAberta(
        @PathVariable Long solicitacaoID) {
        solicitacaoService.excluirSolicitacaoAberta(solicitacaoID);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<SolicitacaoResponseDTO>> listarSolicitacoes(
        @ModelAttribute SolicitacaoFiltroDTO filtroDTO) {
        List<Solicitacao> solicitacoes = solicitacaoService.listarSolicitacoes(filtroDTO);
        return ResponseEntity.ok(solicitacoes.stream()
            .map(solicitacaoService::converterParaDTO)
            .toList());
    }

    @GetMapping("/detalhar/{solicitacaoID}")
    public ResponseEntity<SolicitacaoResponseDTO> detalharSolicitacao(
        @PathVariable Long solicitacaoID) {
        Solicitacao solicitacao = solicitacaoService.detalharSolicitacao(solicitacaoID);
        return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacao));
    }

    @PatchMapping("/alterarStatus/{solicitacaoID}")
    public ResponseEntity<SolicitacaoResponseDTO> alterarStatus(
        @PathVariable Long solicitacaoID, @Valid @RequestBody AlterarStatusRequestDTO novoStatusDTO) {
        Solicitacao solicitacaoAlterada = solicitacaoService.alterarStatus(solicitacaoID, novoStatusDTO);
        return ResponseEntity.ok(solicitacaoService.converterParaDTO(solicitacaoAlterada));
    }

    @GetMapping("/dashboard")
    public ResponseEntity<DashboardDTO> metricasDashboard() {
        DashboardDTO dashboard = new DashboardDTO(
            solicitacaoService.countTotalSolicitacoes(),
            solicitacaoService.countTotalPorStatus(Status.ABERTO),
            solicitacaoService.countTotalPorStatus(Status.EM_ATENDIMENTO),
            solicitacaoService.countTotalPorStatus(Status.CONCLUIDO)
        );
        return ResponseEntity.ok(dashboard);
    }

}
