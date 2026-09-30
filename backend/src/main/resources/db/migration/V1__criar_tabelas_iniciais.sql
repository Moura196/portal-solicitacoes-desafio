create schema if not exists desafio;

CREATE TABLE desafio.usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    criado_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE desafio.solicitacoes (
    id             SERIAL        PRIMARY KEY,
    titulo         VARCHAR(200)  NOT NULL,
    descricao      TEXT          NOT NULL,
    categoria      VARCHAR(50)   NOT NULL
                   CHECK (categoria IN ('TI', 'RH', 'COMPRAS', 'FINANCEIRO', 'INFRAESTRUTURA')),
    status         VARCHAR(30)   NOT NULL DEFAULT 'ABERTO'
                   CHECK (status IN ('ABERTO', 'EM_ATENDIMENTO', 'CONCLUIDO')),
    usuario_id     INT           NOT NULL,
    data_abertura  TIMESTAMP     NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_solicitacao_usuario
        FOREIGN KEY (usuario_id) REFERENCES desafio.usuarios(id)
);

-- Inserindo usuários de teste conforme requisito do desafio
INSERT INTO desafio.usuarios (nome, email, senha) VALUES ('Avaliador', 'avaliador@bitsolucoes.com', '123456');