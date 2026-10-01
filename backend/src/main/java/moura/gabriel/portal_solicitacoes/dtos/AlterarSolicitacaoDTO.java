package moura.gabriel.portal_solicitacoes.dtos;

import moura.gabriel.portal_solicitacoes.models.Categoria;

public record AlterarSolicitacaoDTO(

    String titulo,
    String descricao,
    Categoria categoria

) {}
