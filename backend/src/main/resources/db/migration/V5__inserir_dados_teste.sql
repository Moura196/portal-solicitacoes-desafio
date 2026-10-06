-- Inserindo um novo avaliador
INSERT INTO desafio.usuarios (nome, email, senha) 
VALUES ('Frederico Augusto', 'frederico@techreviewers.com', '$2a$10$jqnOWTKHfHTEx0J8R5ap3ehYEzFMH6fTTTOV7TZK/r6bChpFXkAsW');

-- Inserindo solicitações fictícias para o novo avaliador (ID 2 se for o segundo a ser inserido, ou pegamos pelo subselect)
INSERT INTO desafio.solicitacoes (titulo, descricao, categoria, status, usuario_id)
VALUES 
('Acesso ao servidor VPN', 'Solicito liberação de acesso à VPN corporativa para trabalho remoto.', 'TI', 'ABERTO', (SELECT id FROM desafio.usuarios WHERE email = 'frederico@techreviewers.com')),
('Compra de Monitores', 'Necessitamos de 2 monitores novos para a equipe de design.', 'COMPRAS', 'ABERTO', (SELECT id FROM desafio.usuarios WHERE email = 'avaliador@bitsolucoes.com')),
('Problema no Ar Condicionado', 'O ar condicionado da sala de reuniões 3 não está gelando.', 'INFRAESTRUTURA', 'EM_ATENDIMENTO', (SELECT id FROM desafio.usuarios WHERE email = 'frederico@techreviewers.com')),
('Pagamento de Fornecedor', 'Nota fiscal do fornecedor de licenças de software em anexo.', 'FINANCEIRO', 'CONCLUIDO', (SELECT id FROM desafio.usuarios WHERE email = 'avaliador@bitsolucoes.com')),
('Dúvida sobre Férias', 'Gostaria de saber quantos dias de férias tenho acumulado.', 'RH', 'CONCLUIDO', (SELECT id FROM desafio.usuarios WHERE email = 'frederico@techreviewers.com'));
