package br.com.moser.mosermoney.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

/**
 * @author Juliano Moser
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "permissao")
public class Permissao {

    @Id
    private Long codigo;
    private String descricao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permissao permissao = (Permissao) o;
        return Objects.equals(codigo, permissao.codigo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo);
    }
}
