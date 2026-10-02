package moura.gabriel.portal_solicitacoes.dtos;

public record DashboardDTO(

    long totalSolicitacoes,
    long totalAbertas,
    long totalEmAtendimento,
    long totalConcluidas

) {
    
}
