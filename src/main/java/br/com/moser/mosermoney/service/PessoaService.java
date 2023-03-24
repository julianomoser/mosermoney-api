package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.repository.CategoriaRepository;
import br.com.moser.mosermoney.repository.PessoaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Juliano Moser
 */
@Service
public class PessoaService {

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listAll() {
        return repository.findAll();
    }

    public Pessoa save(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    public Optional<Pessoa> findById(Long codigo) {
        return repository.findById(codigo);
    }
}
