package br.com.moser.mosermoney.exceptionhandler;

import lombok.Getter;

/**
 * @author Juliano Moser
 */
@Getter
public enum ProblemType {

    DADOS_INVALIDOS("Dados inválidos", "/dados-invalidos"),
    ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
    PARAMETRO_INVALIDO("Parânetro invalido", "/parametro-invalido"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem incompreensível", "/mensagem-incompreensivel"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ENTIDADE_EM_USO("Entidade em uso", "/entidade-em-uso"),
    ERRO_NEGOCIO("Violação de regra de negócio", "/erro-negocio"),
    ACESSO_NEGADO("Acesso negado", "/acesso-negado");

    private final String title;
    private final String uri;

    ProblemType(String title, String path) {
        this.title = title;
        this.uri = "https://mosermoney.com.br" + path;
    }
}
