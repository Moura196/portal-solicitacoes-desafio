package moura.gabriel.portal_solicitacoes.dtos;

public record DashboardResponseDTO(

    long totalSolicitacoes,
    long totalAbertas,
    long totalEmAtendimento,
    long totalConcluidas

) {
    
}
