package moura.gabriel.portal_solicitacoes.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import moura.gabriel.portal_solicitacoes.models.Categoria;

public record SolicitacaoRequestDTO(
    
    @NotBlank(message = "O título é obrigatório")
    String titulo,
    
    @NotBlank(message = "A descrição é obrigatória")
    String descricao,
    
    @NotNull(message = "A categoria é obrigatória")
    Categoria categoria

) {}
