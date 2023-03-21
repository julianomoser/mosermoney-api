CREATE TABLE categoria (
    codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
    nome   VARCHAR(50) NOT NULL
) engine=InnoDB default charset=UTF8MB4;

INSERT INTO categoria (nome) values ('Lazer');
INSERT INTO categoria (nome) values ('Alimentação');
INSERT INTO categoria (nome) values ('Supermercado');
INSERT INTO categoria (nome) values ('Farmácia');
INSERT INTO categoria (nome) values ('Outros');