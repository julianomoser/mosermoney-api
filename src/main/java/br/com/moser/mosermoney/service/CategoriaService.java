package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
