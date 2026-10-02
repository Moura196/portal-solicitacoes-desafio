package moura.gabriel.portal_solicitacoes.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;
import moura.gabriel.portal_solicitacoes.dtos.SolicitacaoFiltroDTO;
import moura.gabriel.portal_solicitacoes.models.Solicitacao;

public class SolicitacaoSpecification {

    public static Specification<Solicitacao> listarPorFiltros(SolicitacaoFiltroDTO filtroDTO) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filtroDTO.categoria() != null) {
                predicates.add(criteriaBuilder.equal(root.get("categoria"), filtroDTO.categoria()));
            }
            
            if (filtroDTO.status() != null) {
                predicates.add(criteriaBuilder.equal(root.get("status"), filtroDTO.status()));
            }
            
            if (filtroDTO.titulo() != null && !filtroDTO.titulo().trim().isEmpty()) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("titulo")), "%" + filtroDTO.titulo().toLowerCase() + "%"));
            }
            
            if (filtroDTO.dataInicio() != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("dataAbertura"), filtroDTO.dataInicio().atStartOfDay()));
            }
            
            if (filtroDTO.dataFim() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("dataAbertura"), filtroDTO.dataFim().atTime(23, 59, 59, 999999999)));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
