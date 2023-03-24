package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class NegocioException extends RuntimeException {

    private static final long serialVersionUID = 1;

    public NegocioException(String message) {
        super(message);
    }

    public NegocioException(String message, Throwable cause) {
        super(message, cause);
    }
}
