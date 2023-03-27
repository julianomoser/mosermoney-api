package br.com.moser.mosermoney.exception;

/**
 * @author Juliano Moser
 */
public class UsuarioNaoEncontradoException extends EntidadeNaoEncontradaException {

    private static final long serialVersionUID = 1;

    public UsuarioNaoEncontradoException(String message) {
        super(message);
    }

    public UsuarioNaoEncontradoException(Long usuarioId) {
        this(String.format("Não existe um cadastro de usuário com código %d", usuarioId));
    }
}
