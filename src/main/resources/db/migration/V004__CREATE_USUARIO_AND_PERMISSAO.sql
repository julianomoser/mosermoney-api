create table usuario
(
    codigo        BIGINT(20) PRIMARY KEY,
    nome          VARCHAR(50)  NOT NULL,
    email         VARCHAR(50)  NOT NULL,
    senha         VARCHAR(150) NOT NULL,
    data_cadastro datetime     NOT NULL
) engine = InnoDB
  default charset = utf8;

create table permissao
(
    codigo    BIGINT(20) PRIMARY KEY,
    descricao VARCHAR(50) NOT NULL
) engine = InnoDB
  default charset = utf8;

create table usuario_permissao
(
    codigo_usuario   BIGINT(20) NOT NULL,
    codigo_permissao BIGINT(20) NOT NULL,
    PRIMARY KEY (codigo_usuario, codigo_permissao),
    FOREIGN KEY (codigo_usuario) REFERENCES usuario (codigo),
    FOREIGN KEY (codigo_permissao) REFERENCES permissao (codigo)
) engine = InnoDB
  default charset = utf8;
