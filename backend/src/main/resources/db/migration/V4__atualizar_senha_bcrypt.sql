-- Atualiza a senha do usuário Avaliador para o hash BCrypt correspondente a "123456"
UPDATE desafio.usuarios
SET senha = '$2a$10$jqnOWTKHfHTEx0J8R5ap3ehYEzFMH6fTTTOV7TZK/r6bChpFXkAsW'
WHERE email = 'avaliador@bitsolucoes.com';
