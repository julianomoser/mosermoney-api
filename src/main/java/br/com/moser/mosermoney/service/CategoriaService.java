package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.exception.CategoriaNaoEncontradoException;
import br.com.moser.mosermoney.exception.PessoaNaoEncontradoException;
import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juliano Moser
 */
@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<Categoria> listAll() {
        return repository.findAll();
    }

    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    public Optional<Categoria> findById(Long codigo) {
        return repository.findById(codigo);
    }

    public Categoria findOrFail(Long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new CategoriaNaoEncontradoException(codigo));
    }
}
