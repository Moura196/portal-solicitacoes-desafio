package moura.gabriel.portal_solicitacoes.dtos;

import java.time.LocalDateTime;

public record ErrorResponseDTO(

    String mensagem,
    LocalDateTime timestamp

) {}
