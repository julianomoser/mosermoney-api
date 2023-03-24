package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.exception.EntidadeEmUsoException;
import br.com.moser.mosermoney.exception.LancamentoNaoEncontradoException;
import br.com.moser.mosermoney.model.Lancamento;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.repository.LancamentoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Juliano Moser
 */
@Service
public class LancamentoService {

    private static final String MSG_LANCAMENTO_EM_USO = "Lancamento de código %d não pode ser removida, pois está em uso ";

    private final LancamentoRepository repository;

    public LancamentoService(LancamentoRepository repository) {
        this.repository = repository;
    }

    public Lancamento save(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    public List<Lancamento> listAll() {
        return repository.findAll();
    }


    public Optional<Lancamento> findById(Long codigo) {
        return repository.findById(codigo);
    }

    @Transactional
    public Lancamento atualizar(Lancamento lancamento, Long codigo) {
        Lancamento lancamentoAtual = this.findOrFail(codigo);
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

    public Lancamento findOrFail(Long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new LancamentoNaoEncontradoException(codigo));
    }
}
