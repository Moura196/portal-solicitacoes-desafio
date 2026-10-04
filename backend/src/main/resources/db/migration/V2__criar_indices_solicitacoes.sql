-- Criando índices para buscas e filtros por Status, Categoria, Data e Título
CREATE INDEX idx_solicitacoes_status ON desafio.solicitacoes(status);

CREATE INDEX idx_solicitacoes_categoria ON desafio.solicitacoes(categoria);

CREATE INDEX idx_solicitacoes_data_abertura ON desafio.solicitacoes(data_abertura);

CREATE INDEX idx_solicitacoes_titulo ON desafio.solicitacoes(titulo);