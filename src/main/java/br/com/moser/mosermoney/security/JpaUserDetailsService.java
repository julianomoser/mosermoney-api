package br.com.moser.mosermoney.security;

import br.com.moser.mosermoney.model.Usuario;
import br.com.moser.mosermoney.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author Juliano Moser
 */
@Primary
@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
        Usuario usuario = usuarioOptional.orElseThrow(() -> new UsernameNotFoundException("Usuário e/ou senha incorretos"));
        return new User(email, usuario.getSenha(), getAuthorities(usuario));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Usuario usuario) {
        return usuario.getPermissoes().stream()
                .map(permissao -> new SimpleGrantedAuthority(permissao.getDescricao().toUpperCase()))
                .collect(Collectors.toSet());
    }

}
