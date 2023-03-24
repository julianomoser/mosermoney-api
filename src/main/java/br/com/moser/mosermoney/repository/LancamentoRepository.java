package br.com.moser.mosermoney.repository;

import br.com.moser.mosermoney.model.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Juliano Moser
 */
public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
