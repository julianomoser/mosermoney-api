package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class CategoriaNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1;

    public CategoriaNaoEncontradoException(String message) {
        super(message);
    }

    public CategoriaNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe um cadastro de categoria com código %d", usuarioId));
    }
}
