-- Script para criar o banco de dados do sistema de aluguel de carros
-- Execute este script no PostgreSQL antes de executar a aplicação

-- Criar o banco de dados
CREATE DATABASE xumblaubs_rentcar
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Conectar ao banco criado
\c xumblaubs_rentcar;

-- Criar extensões necessárias
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Comentários sobre o sistema
COMMENT ON DATABASE xumblaubs_rentcar IS 'Sistema de gestão de aluguel de carros com análise financeira por agentes';

-- O Spring Boot com JPA/Hibernate irá criar as tabelas automaticamente
-- baseado nas entidades definidas no código Java
