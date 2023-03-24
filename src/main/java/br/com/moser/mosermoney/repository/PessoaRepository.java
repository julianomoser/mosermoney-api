package br.com.moser.mosermoney.repository;

import br.com.moser.mosermoney.model.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Juliano Moser
 */
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
