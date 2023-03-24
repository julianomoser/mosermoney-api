package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class LancamentoNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1;

    public LancamentoNaoEncontradoException(String message) {
        super(message);
    }

    public LancamentoNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe um cadastro de lançamento com código %d", usuarioId));
    }
}
