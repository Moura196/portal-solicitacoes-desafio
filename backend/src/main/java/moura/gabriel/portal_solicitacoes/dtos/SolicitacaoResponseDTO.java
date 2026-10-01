package moura.gabriel.portal_solicitacoes.dtos;

import java.time.LocalDateTime;

import moura.gabriel.portal_solicitacoes.models.Categoria;
import moura.gabriel.portal_solicitacoes.models.Status;

public record SolicitacaoResponseDTO(

    Long id,
    String titulo,
    String descricao,
    Categoria categoria,
    Status status,
    LocalDateTime dataAbertura,
    UsuarioResponseDTO solicitante
    
) {}
