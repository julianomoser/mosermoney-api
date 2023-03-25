package br.com.moser.mosermoney.repository.lancamento;

import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;

import java.util.List;

/**
 * @author Juliano Moser
 */
public interface LancamentoRepositoryQuery {

    List<Lancamento> filtrar(LancamentoFilter lancamentoFilter);
}
