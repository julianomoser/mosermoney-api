package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.exception.EntidadeEmUsoException;
import br.com.moser.mosermoney.exception.PessoaNaoEncontradoException;
import br.com.moser.mosermoney.model.Pessoa;
import br.com.moser.mosermoney.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author Juliano Moser
 */
@Service
public class PessoaService {

    private static final String MSG_PESSOA_EM_USO = "Pessoa de código %d não pode ser removida, pois está em uso ";

    private final PessoaRepository repository;

    public PessoaService(PessoaRepository repository) {
        this.repository = repository;
    }

    public List<Pessoa> listAll() {
        return repository.findAll();
    }

    public Page<Pessoa> findByNomeContaining(String nome, Pageable pageable) {
        return repository.findByNomeContaining(nome, pageable);
    }

    public Pessoa save(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Transactional
    public Pessoa atualizar(Pessoa pessoa, Long codigo) {
        Pessoa pessoaAtual = this.findOrFail(codigo);
        BeanUtils.copyProperties(pessoa, pessoaAtual, "codigo");
        return repository.save(pessoaAtual);
    }

    @Transactional
    public void ativar(Long codigo) {
        Pessoa pessoa = this.findOrFail(codigo);
        pessoa.ativar();
    }

    @Transactional
    public void inativar(Long codigo) {
        Pessoa pessoa = this.findOrFail(codigo);
        pessoa.inativar();
    }


    public Optional<Pessoa> findById(Long codigo) {
        return repository.findById(codigo);
    }

    @Transactional
    public void deleteById(Long codigo) {
        try {
            repository.deleteById(codigo);
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new PessoaNaoEncontradoException(codigo);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PESSOA_EM_USO, codigo)
            );
        }
    }

    public Pessoa findOrFail(Long codigo) {
        return repository.findById(codigo)
                .orElseThrow(() -> new PessoaNaoEncontradoException(codigo));
    }
}
