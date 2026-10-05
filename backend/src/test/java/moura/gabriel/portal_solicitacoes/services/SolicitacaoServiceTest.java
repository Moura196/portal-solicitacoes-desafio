package moura.gabriel.portal_solicitacoes.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import moura.gabriel.portal_solicitacoes.dtos.*;
import moura.gabriel.portal_solicitacoes.exceptions.RecursoNaoEncontradoException;
import moura.gabriel.portal_solicitacoes.models.Categoria;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;
import moura.gabriel.portal_solicitacoes.models.Status;
import moura.gabriel.portal_solicitacoes.models.Usuario;
import moura.gabriel.portal_solicitacoes.repositories.SolicitacaoRepository;
import moura.gabriel.portal_solicitacoes.repositories.UsuarioRepository;

@ExtendWith(MockitoExtension.class)
public class SolicitacaoServiceTest {

    @Mock
    private SolicitacaoRepository solicitacaoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private SolicitacaoService solicitacaoService;

    private Solicitacao solicitacaoAberta;
    private Solicitacao solicitacaoEmAtendimento;
    private Solicitacao solicitacaoConcluida;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(10L);
        usuario.setNome("João");
        usuario.setEmail("joao@test.com");

        solicitacaoAberta = new Solicitacao();
        solicitacaoAberta.setId(1L);
        solicitacaoAberta.setStatus(Status.ABERTO);
        solicitacaoAberta.setTitulo("Titulo Aberto");
        solicitacaoAberta.setDescricao("Descricao");
        solicitacaoAberta.setCategoria(Categoria.TI);
        solicitacaoAberta.setUsuario(usuario);

        solicitacaoEmAtendimento = new Solicitacao();
        solicitacaoEmAtendimento.setId(2L);
        solicitacaoEmAtendimento.setStatus(Status.EM_ATENDIMENTO);
        solicitacaoEmAtendimento.setUsuario(usuario);

        solicitacaoConcluida = new Solicitacao();
        solicitacaoConcluida.setId(3L);
        solicitacaoConcluida.setStatus(Status.CONCLUIDO);
        solicitacaoConcluida.setUsuario(usuario);
    }

    @Test
    @DisplayName("Deve criar solicitação com sucesso")
    void deveCriarSolicitacao() {
        SolicitacaoRequestDTO dto = new SolicitacaoRequestDTO("T", "D", Categoria.COMPRAS);
        when(usuarioRepository.findById(10L)).thenReturn(Optional.of(usuario));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenAnswer(i -> i.getArguments()[0]);

        Solicitacao result = solicitacaoService.criarSolicitacao(dto, 10L);

        assertEquals("T", result.getTitulo());
        assertEquals("D", result.getDescricao());
        assertEquals(Categoria.COMPRAS, result.getCategoria());
        assertEquals(Status.ABERTO, result.getStatus());
        assertEquals(usuario, result.getUsuario());
        assertNotNull(result.getDataAbertura());
    }

    @Test
    @DisplayName("Deve falhar ao criar solicitação com usuário inexistente")
    void falharCriarSolicitacaoUsuarioInexistente() {
        SolicitacaoRequestDTO dto = new SolicitacaoRequestDTO("T", "D", Categoria.COMPRAS);
        when(usuarioRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(RecursoNaoEncontradoException.class, () -> solicitacaoService.criarSolicitacao(dto, 10L));
    }

    @Test
    @DisplayName("Deve editar solicitação aberta com sucesso")
    void deveEditarSolicitacaoAberta() {
        AlterarSolicitacaoDTO dto = new AlterarSolicitacaoDTO("Novo Titulo", "Nova Desc", Categoria.COMPRAS);
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenAnswer(i -> i.getArguments()[0]);

        Solicitacao result = solicitacaoService.editarSolicitacaoAberta(1L, dto);

        assertEquals("Novo Titulo", result.getTitulo());
        assertEquals("Nova Desc", result.getDescricao());
        assertEquals(Categoria.COMPRAS, result.getCategoria());
    }

    @Test
    @DisplayName("Não deve editar solicitação se os campos forem vazios")
    void naoDeveEditarSolicitacaoCamposVazios() {
        AlterarSolicitacaoDTO dto = new AlterarSolicitacaoDTO("", null, null);
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));

        assertThrows(IllegalStateException.class, () -> solicitacaoService.editarSolicitacaoAberta(1L, dto));
    }

    @Test
    @DisplayName("Não deve editar solicitação se não estiver aberta")
    void naoDeveEditarSolicitacaoNaoAberta() {
        AlterarSolicitacaoDTO dto = new AlterarSolicitacaoDTO("Novo Titulo", null, null);
        when(solicitacaoRepository.findById(2L)).thenReturn(Optional.of(solicitacaoEmAtendimento));

        assertThrows(IllegalStateException.class, () -> solicitacaoService.editarSolicitacaoAberta(2L, dto));
    }

    @Test
    @DisplayName("Deve excluir solicitação aberta")
    void deveExcluirSolicitacaoAberta() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));

        solicitacaoService.excluirSolicitacaoAberta(1L);

        verify((org.springframework.data.repository.CrudRepository<Solicitacao, Long>) solicitacaoRepository).delete(solicitacaoAberta);
    }

    @Test
    @DisplayName("Não deve excluir solicitação que não está aberta")
    void naoDeveExcluirSolicitacaoNaoAberta() {
        when(solicitacaoRepository.findById(2L)).thenReturn(Optional.of(solicitacaoEmAtendimento));

        assertThrows(IllegalStateException.class, () -> solicitacaoService.excluirSolicitacaoAberta(2L));
        verify((org.springframework.data.repository.CrudRepository<Solicitacao, Long>) solicitacaoRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Deve listar solicitações usando filtro")
    @SuppressWarnings("unchecked")
    void deveListarSolicitacoes() {
        SolicitacaoFiltroDTO filtro = new SolicitacaoFiltroDTO(Categoria.COMPRAS, Status.ABERTO, "T", null, null);
        when(solicitacaoRepository.findAll(any(Specification.class), any(Sort.class))).thenReturn(List.of(solicitacaoAberta));

        List<Solicitacao> res = solicitacaoService.listarSolicitacoes(filtro);

        assertEquals(1, res.size());
    }

    @Test
    @DisplayName("Deve detalhar solicitação")
    void deveDetalharSolicitacao() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));

        Solicitacao res = solicitacaoService.detalharSolicitacao(1L);

        assertEquals(solicitacaoAberta, res);
    }

    @Test
    @DisplayName("Deve contar total de solicitações")
    void deveContarTotalSolicitacoes() {
        when(solicitacaoRepository.count()).thenReturn(10L);
        assertEquals(10L, solicitacaoService.countTotalSolicitacoes());
    }

    @Test
    @DisplayName("Deve contar total por status")
    void deveContarTotalPorStatus() {
        when(solicitacaoRepository.countByStatus(Status.ABERTO)).thenReturn(5L);
        assertEquals(5L, solicitacaoService.countTotalPorStatus(Status.ABERTO));
    }

    @Test
    @DisplayName("Deve converter para DTO corretamente")
    void deveConverterParaDTO() {
        SolicitacaoResponseDTO dto = solicitacaoService.converterParaDTO(solicitacaoAberta);
        assertEquals(solicitacaoAberta.getId(), dto.id());
        assertEquals(solicitacaoAberta.getTitulo(), dto.titulo());
        assertEquals(usuario.getId(), dto.solicitante().id());
    }

    // -- Testes de Alterar Status Antigos -- //
    
    @Test
    @DisplayName("Deve permitir alterar status de ABERTO para EM_ATENDIMENTO")
    void devePermitirAlterarDeAbertoParaEmAtendimento() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenReturn(solicitacaoAberta);

        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.EM_ATENDIMENTO);
        Solicitacao resultado = solicitacaoService.alterarStatus(1L, request);

        assertEquals(Status.EM_ATENDIMENTO, resultado.getStatus());
        verify(solicitacaoRepository).save(solicitacaoAberta);
    }

    @Test
    @DisplayName("Não deve permitir alterar status de ABERTO para CONCLUIDO")
    void naoDevePermitirAlterarDeAbertoParaConcluido() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));
        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.CONCLUIDO);
        
        assertThrows(IllegalStateException.class, () -> solicitacaoService.alterarStatus(1L, request));
    }

    @Test
    @DisplayName("Deve permitir alterar status de EM_ATENDIMENTO para CONCLUIDO")
    void devePermitirAlterarDeEmAtendimentoParaConcluido() {
        when(solicitacaoRepository.findById(2L)).thenReturn(Optional.of(solicitacaoEmAtendimento));
        when(solicitacaoRepository.save(any(Solicitacao.class))).thenReturn(solicitacaoEmAtendimento);

        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.CONCLUIDO);
        Solicitacao resultado = solicitacaoService.alterarStatus(2L, request);

        assertEquals(Status.CONCLUIDO, resultado.getStatus());
        verify(solicitacaoRepository).save(solicitacaoEmAtendimento);
    }

    @Test
    @DisplayName("Não deve permitir alterar status de EM_ATENDIMENTO para ABERTO")
    void naoDevePermitirAlterarDeEmAtendimentoParaAberto() {
        when(solicitacaoRepository.findById(2L)).thenReturn(Optional.of(solicitacaoEmAtendimento));
        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.ABERTO);
        
        assertThrows(IllegalStateException.class, () -> solicitacaoService.alterarStatus(2L, request));
    }

    @Test
    @DisplayName("Não deve permitir alterar status de uma solicitação CONCLUIDA")
    void naoDevePermitirAlterarDeConcluidoParaQualquerOutro() {
        when(solicitacaoRepository.findById(3L)).thenReturn(Optional.of(solicitacaoConcluida));
        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.EM_ATENDIMENTO);
        
        assertThrows(IllegalStateException.class, () -> solicitacaoService.alterarStatus(3L, request));
    }
    
    @Test
    @DisplayName("Não deve permitir alterar para o mesmo status")
    void naoDevePermitirAlterarParaMesmoStatus() {
        when(solicitacaoRepository.findById(1L)).thenReturn(Optional.of(solicitacaoAberta));
        AlterarStatusRequestDTO request = new AlterarStatusRequestDTO(Status.ABERTO);
        
        assertThrows(IllegalStateException.class, () -> solicitacaoService.alterarStatus(1L, request));
    }
}
