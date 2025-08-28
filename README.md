**Projeto desenvolvido para a disciplina Desenvolvimento de Sistemas Web II.**


_Informações - DB_


-- Criação de tabelas

-- Criar DB "paokentin"

CREATE DATABASE paokentin;


-- Criar tabela "Pao"

CREATE TABLE IF NOT EXISTS Pao (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao VARCHAR(255),
    tempoPreparo BIGINT NOT NULL,
    cor VARCHAR(20) NOT NULL
);


-- Criar tabela "Fornada"

CREATE TABLE IF NOT EXISTS Fornada (
    id SERIAL PRIMARY KEY,
    paoId INT NOT NULL,
    horaInicio TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (paoId) REFERENCES Pao(id)
);

-- Inserção de dados iniciais

-- Inserir pães

INSERT INTO Pao (nome, descricao, tempoPreparo, cor) VALUES
('Frances', 'Pão francês tradicional', 120, 'AMARELO'),
('Pao Doce', 'Pão doce com açúcar e canela', 60, 'VERMELHO');


-- Inserir fornadas

INSERT INTO Fornada (paoId, horaInicio) VALUES
(1, '2025-08-24 07:00:00'),
(2, '2025-08-24 08:30:00')
