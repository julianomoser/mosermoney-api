package br.com.moser.mosermoney.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

/**
 * @author Juliano Moser
 */
@NoRepositoryBean
public interface CustomJpaRepository<T, ID> extends JpaRepository<T, ID> {

    Optional<T> findFirst();

    void detach(T entity);
}
