package br.com.moser.mosermoney.repository;

import br.com.moser.mosermoney.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Juliano Moser
 */
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
