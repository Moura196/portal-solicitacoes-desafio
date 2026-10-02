package moura.gabriel.portal_solicitacoes.dtos;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import moura.gabriel.portal_solicitacoes.models.Categoria;
import moura.gabriel.portal_solicitacoes.models.Status;

public record SolicitacaoFiltroDTO(

    Categoria categoria,
    Status status,
    String titulo,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataInicio,
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataFim
    
) {}
