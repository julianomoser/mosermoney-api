package br.com.moser.mosermoney.service;

import br.com.moser.mosermoney.exception.EntidadeEmUsoException;
import br.com.moser.mosermoney.exception.NegocioException;
import br.com.moser.mosermoney.exception.UsuarioNaoEncontradoException;
import br.com.moser.mosermoney.model.Usuario;
import br.com.moser.mosermoney.repository.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Juliano Moser
 */
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Service
public class UsuarioService {

    private static final String MSG_USUARIO_EM_USO = "Usuário de código %d não pode ser removida, pois está em uso ";

    private final UsuarioRepository usuarioRepository;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public Usuario salvar(Usuario usuario) {

        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioAtual = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioAtual.isPresent() && !usuarioAtual.get().getCodigo().equals(usuario)) {
            throw new NegocioException(
                    String.format("Já existe um usuário cadastrado com email %s", usuario.getEmail()));
        }

        if (usuario.isNovo()) {
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }

        return usuarioRepository.save(usuario);
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = findOrFail(usuarioId);

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    @Transactional
    public void excluir(Long usuarioId) {
        try {
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new UsuarioNaoEncontradoException(usuarioId);

        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_EM_USO, usuarioId)
            );
        }
    }

    public Usuario findOrFail(Long usuarioId) {
        return usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }
}
