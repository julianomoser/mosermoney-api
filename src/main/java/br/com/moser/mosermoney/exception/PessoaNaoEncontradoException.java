package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class PessoaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1;

    public PessoaNaoEncontradoException(String message) {
        super(message);
    }

    public PessoaNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe um cadastro de pessoa com código %d", usuarioId));
    }
}
