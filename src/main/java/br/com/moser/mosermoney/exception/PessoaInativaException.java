package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class PessoaInativaException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1;

    public PessoaInativaException(String message) {
        super(message);
    }

    public PessoaInativaException(Long usuarioId) {
        this(String.format("Pessoa com código %d está inativa", usuarioId));
    }
}
