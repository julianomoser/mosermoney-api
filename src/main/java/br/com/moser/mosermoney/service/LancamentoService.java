package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.exception.EntidadeEmUsoException;
import br.com.moser.mosermoney.exception.LancamentoNaoEncontradoException;
import br.com.moser.mosermoney.exception.PessoaInativaException;
import br.com.moser.mosermoney.repository.filter.LancamentoFilter;
import br.com.moser.mosermoney.model.Categoria;
import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.Lancamento_;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.repository.LancamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Juliano Moser
 */
@Service
public class LancamentoService {

    private static final String MSG_LANCAMENTO_EM_USO = "Lancamento de código %d não pode ser removida, pois está em uso ";
    private static final String MSG_PESSOA_INATIVA = "Pessoa está inativa, permitivo salvar lançamento somente para pessoas ativas.";

    private final LancamentoRepository repository;
    private final PessoaService pessoaService;
    private final CategoriaService categoriaService;

    public LancamentoService(LancamentoRepository repository, PessoaService pessoaService, CategoriaService categoriaService) {
        this.repository = repository;
        this.pessoaService = pessoaService;
        this.categoriaService = categoriaService;
    }

    public Lancamento save(Lancamento lancamento) {
        entityValidation(lancamento);

        return repository.save(lancamento);
    }

    public List<Lancamento> pesquisar(LancamentoFilter lancamentoFilter) {
        return repository.filtrar(lancamentoFilter);
    }


    public Optional<Lancamento> findById(Long codigo) {
        return repository.findById(codigo);
    }

    @Transactional
    public Lancamento atualizar(Lancamento lancamento, Long codigo) {
        Lancamento lancamentoAtual = this.findOrFail(codigo);
        entityValidation(lancamento);
        BeanUtils.copyProperties(lancamento, lancamentoAtual, "codigo");
        return repository.save(lancamentoAtual);
    }

    @Transactional
    public void deleteById(Long codigo) {
        try {
            repository.deleteById(codigo);
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new LancamentoNaoEncontradoException(codigo);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_LANCAMENTO_EM_USO, codigo)
            );
        }
    }

    private void entityValidation(Lancamento lancamento) {
        Long pessoaId = lancamento.getPessoa().getCodigo();
        Long categoriaId = lancamento.getCategoria().getCodigo();

        Pessoa pessoa = pessoaService.findOrFail(pessoaId);

        if (pessoa.isInativo()) {
            throw new PessoaInativaException(MSG_PESSOA_INATIVA);
        }

        Categoria categoria = categoriaService.findOrFail(categoriaId);

        lancamento.setPessoa(pessoa);
        lancamento.setCategoria(categoria);
    }

    public Lancamento findOrFail(Long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new LancamentoNaoEncontradoException(codigo));
    }
}
