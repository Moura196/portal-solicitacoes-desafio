package moura.gabriel.portal_solicitacoes.dtos;

import jakarta.validation.constraints.NotNull;
import moura.gabriel.portal_solicitacoes.models.Status;

public record AlterarStatusRequestDTO(

    @NotNull(message = "O status é obrigatório")
    Status status
    
) {}
