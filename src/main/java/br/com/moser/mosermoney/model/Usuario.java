package br.com.moser.mosermoney.model;

/**
 * @author Juliano Moser
 */

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    private Long codigo;
    private String nome;
    private String email;
    private String senha;
    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "usuario_permissao", joinColumns = @JoinColumn(name = "codigo_usuario")
            , inverseJoinColumns = @JoinColumn(name = "codigo_permissao"))
    private Set<Permissao> permissoes = new HashSet<>();

    public boolean isNovo() {
        return getCodigo() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(codigo, usuario.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
