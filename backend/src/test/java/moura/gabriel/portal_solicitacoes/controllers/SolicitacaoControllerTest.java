package moura.gabriel.portal_solicitacoes.controllers;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import moura.gabriel.portal_solicitacoes.dtos.AlterarSolicitacaoDTO;
import moura.gabriel.portal_solicitacoes.dtos.AlterarStatusRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.DashboardResponseDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoFiltroDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoRequestDTO;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoResponseDTO;
import moura.gabriel.portal_solicitacoes.models.Categoria;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;
import moura.gabriel.portal_solicitacoes.models.Status;
import moura.gabriel.portal_solicitacoes.models.Usuario;
import moura.gabriel.portal_solicitacoes.services.SolicitacaoService;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoControllerTest {

    @Mock
    private SolicitacaoService solicitacaoService;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private SolicitacaoController solicitacaoController;

    private Solicitacao solicitacao;
    private SolicitacaoResponseDTO solicitacaoResponseDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);

        solicitacao = new Solicitacao();
        solicitacao.setId(10L);
        solicitacao.setStatus(Status.ABERTO);

        solicitacaoResponseDTO = new SolicitacaoResponseDTO(10L, "T", "D", Categoria.TI, Status.ABERTO, null, null);

        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("Deve criar solicitação")
    void deveCriarSolicitacao() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(usuario);
        
        SolicitacaoRequestDTO requestDTO = new SolicitacaoRequestDTO("T", "D", Categoria.TI);
        when(solicitacaoService.criarSolicitacao(requestDTO, 1L)).thenReturn(solicitacao);
        when(solicitacaoService.converterParaDTO(solicitacao)).thenReturn(solicitacaoResponseDTO);

        ResponseEntity<SolicitacaoResponseDTO> response = solicitacaoController.criarSolicitacao(requestDTO);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(solicitacaoResponseDTO, response.getBody());
    }

    @Test
    @DisplayName("Deve editar solicitação")
    void deveEditarSolicitacao() {
        AlterarSolicitacaoDTO dto = new AlterarSolicitacaoDTO("T", "D", Categoria.TI);
        when(solicitacaoService.editarSolicitacaoAberta(10L, dto)).thenReturn(solicitacao);
        when(solicitacaoService.converterParaDTO(solicitacao)).thenReturn(solicitacaoResponseDTO);

        ResponseEntity<SolicitacaoResponseDTO> response = solicitacaoController.editarSolicitacaoAberta(10L, dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve excluir solicitação")
    void deveExcluirSolicitacao() {
        ResponseEntity<Void> response = solicitacaoController.excluirSolicitacaoAberta(10L);
        verify(solicitacaoService).excluirSolicitacaoAberta(10L);
        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve listar solicitações")
    void deveListarSolicitacoes() {
        SolicitacaoFiltroDTO filtro = new SolicitacaoFiltroDTO(null, null, null, null, null);
        when(solicitacaoService.listarSolicitacoes(filtro)).thenReturn(List.of(solicitacao));
        when(solicitacaoService.converterParaDTO(solicitacao)).thenReturn(solicitacaoResponseDTO);

        ResponseEntity<List<SolicitacaoResponseDTO>> response = solicitacaoController.listarSolicitacoes(filtro);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(1, response.getBody().size());
    }

    @Test
    @DisplayName("Deve detalhar solicitação")
    void deveDetalharSolicitacao() {
        when(solicitacaoService.detalharSolicitacao(10L)).thenReturn(solicitacao);
        when(solicitacaoService.converterParaDTO(solicitacao)).thenReturn(solicitacaoResponseDTO);

        ResponseEntity<SolicitacaoResponseDTO> response = solicitacaoController.detalharSolicitacao(10L);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve alterar status")
    void deveAlterarStatus() {
        AlterarStatusRequestDTO dto = new AlterarStatusRequestDTO(Status.EM_ATENDIMENTO);
        when(solicitacaoService.alterarStatus(10L, dto)).thenReturn(solicitacao);
        when(solicitacaoService.converterParaDTO(solicitacao)).thenReturn(solicitacaoResponseDTO);

        ResponseEntity<SolicitacaoResponseDTO> response = solicitacaoController.alterarStatus(10L, dto);

        assertEquals(200, response.getStatusCode().value());
    }

    @Test
    @DisplayName("Deve retornar métricas do dashboard")
    void deveRetornarMetricas() {
        when(solicitacaoService.countTotalSolicitacoes()).thenReturn(10L);
        when(solicitacaoService.countTotalPorStatus(Status.ABERTO)).thenReturn(5L);
        when(solicitacaoService.countTotalPorStatus(Status.EM_ATENDIMENTO)).thenReturn(3L);
        when(solicitacaoService.countTotalPorStatus(Status.CONCLUIDO)).thenReturn(2L);

        ResponseEntity<DashboardResponseDTO> response = solicitacaoController.metricasDashboard();

        assertEquals(200, response.getStatusCode().value());
        assertEquals(10L, response.getBody().totalSolicitacoes());
        assertEquals(5L, response.getBody().totalAbertas());
    }
}
