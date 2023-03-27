package br.com.moser.mosermoney.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Juliano Moser
 */
public @interface CheckSecurity {

    @interface Categoria {
        @PreAuthorize("hasAnyAuthority('SCOPE_write') and hasAnyAuthority('ROLE_CADASTRAR_CATEGORIA')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAnyAuthority('SCOPE_read') and hasAnyAuthority('ROLE_PESQUISAR_CATEGORIA')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }
    }

    @interface Lancamento {
        @PreAuthorize("hasAnyAuthority('SCOPE_write') and hasAnyAuthority('ROLE_CADASTRAR_LANCAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAnyAuthority('SCOPE_read') and hasAnyAuthority('ROLE_PESQUISAR_LANCAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

        @PreAuthorize("hasAnyAuthority('SCOPE_write') and hasAnyAuthority('ROLE_REMOVER_LANCAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeRemover {
        }
    }

    @interface Pessoa {
        @PreAuthorize("hasAnyAuthority('SCOPE_write') and hasAnyAuthority('ROLE_CADASTRAR_PESSOA')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar {
        }

        @PreAuthorize("hasAnyAuthority('SCOPE_read') and hasAnyAuthority('ROLE_PESQUISAR_LANCAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar {
        }

        @PreAuthorize("hasAnyAuthority('SCOPE_write') and hasAnyAuthority('ROLE_REMOVER_PESSOA')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeRemover {
        }
    }

}
