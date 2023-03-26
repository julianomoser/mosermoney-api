package br.com.moser.mosermoney.repository.spec;

import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.Lancamento_;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;

/**
 * @author Juliano Moser
 */
public class LancamentoSpecs {
    public static Specification<Lancamento> usingFilter(LancamentoFilter filter) {
        return (root, query, builder) -> {

            var predicates = new ArrayList<Predicate>();

            if (!ObjectUtils.isEmpty(filter.getDescricao())) {
                predicates.add(builder.like(
                        builder.lower(root.get(Lancamento_.descricao)), "%" + filter.getDescricao().toLowerCase() + "%"));
            }

            if (filter.getDataVencimentoIncio() != null) {
                predicates.add(builder.greaterThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoIncio()));
            }

            if (filter.getDataVencimentoFim() != null) {
                predicates.add(builder.lessThanOrEqualTo(root.get(Lancamento_.dataVencimento), filter.getDataVencimentoFim()));
            }

            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
