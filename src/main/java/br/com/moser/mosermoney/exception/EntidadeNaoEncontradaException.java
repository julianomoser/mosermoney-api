package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public abstract class EntidadeNaoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1;

    public EntidadeNaoEncontradaException(String message) {
        super(message);
    }
}
