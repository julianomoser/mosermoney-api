package br.com.moser.mosermoney.repository;

import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.Lancamento_;
import br.com.moser.mosermoney.repository.lancamento.LancamentoRepositoryQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author Juliano Moser
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long>,
        LancamentoRepositoryQuery, JpaSpecificationExecutor<Lancamento> {
}
