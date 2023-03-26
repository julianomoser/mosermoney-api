package br.com.moser.mosermoney.repository.lancamento;

import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.Lancamento_;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Juliano Moser
 */
public class LancamentoRepositoryQueryImpl implements LancamentoRepositoryQuery {

    @PersistenceContext
    private EntityManager manager;


    @Override
    public List<Lancamento> filtrar(LancamentoFilter lancamentoFilter) {
        var builder = manager.getCriteriaBuilder();
        var query = builder.createQuery(Lancamento.class);
        var root = query.from(Lancamento.class);

        query.where(criarWheres(lancamentoFilter, builder, root));
        query.orderBy(builder.asc(root.get(Lancamento_.dataVencimento)));

        return manager.createQuery(query).getResultList();
    }

    private Predicate[] criarWheres(LancamentoFilter filter, CriteriaBuilder builder, Root<Lancamento> root) {
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

        return predicates.toArray(new Predicate[0]);
    }
}
