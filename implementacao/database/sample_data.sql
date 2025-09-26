-- Script para inserir dados de exemplo no sistema de aluguel de carros
-- Execute este script após a aplicação ter criado as tabelas

-- Inserir usuários de exemplo
INSERT INTO usuarios (username, email, password, nome_completo, tipo, data_criacao, ativo) VALUES
('admin', 'admin@xumblaubs.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Administrador do Sistema', 'ADMIN', NOW(), true),
('cliente1', 'joao@email.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'João Silva', 'CLIENTE', NOW(), true),
('agente1', 'banco@exemplo.com', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'Banco Exemplo', 'AGENTE', NOW(), true);

-- Inserir clientes
INSERT INTO clientes (nome, rg, cpf, endereco, profissao, email, telefone) VALUES
('João Silva', '123456789', '123.456.789-00', 'Rua das Flores, 123, São Paulo - SP', 'Engenheiro', 'joao@email.com', '(11) 99999-9999'),
('Maria Santos', '987654321', '987.654.321-00', 'Av. Paulista, 456, São Paulo - SP', 'Advogada', 'maria@email.com', '(11) 88888-8888'),
('Pedro Costa', '456789123', '456.789.123-00', 'Rua da Consolação, 789, São Paulo - SP', 'Médico', 'pedro@email.com', '(11) 77777-7777');

-- Inserir empregos e rendas
INSERT INTO empregos_rendas (entidade_empregadora, renda, observacoes, cliente_id) VALUES
('Empresa ABC Ltda', 8000.00, 'Trabalho principal', 1),
('Freelance Projetos', 2000.00, 'Trabalho adicional', 1),
('Escritório de Advocacia XYZ', 12000.00, 'Sócia do escritório', 2),
('Hospital São Paulo', 15000.00, 'Médico especialista', 3);

-- Inserir agentes
INSERT INTO agentes (nome, cnpj, endereco, email, telefone, tipo, observacoes) VALUES
('Banco Exemplo S.A.', '12.345.678/0001-90', 'Av. Faria Lima, 1000, São Paulo - SP', 'banco@exemplo.com', '(11) 3333-3333', 'BANCO', 'Banco parceiro do sistema'),
('Empresa de Carros Ltda', '98.765.432/0001-10', 'Rua dos Automóveis, 500, São Paulo - SP', 'empresa@carros.com', '(11) 4444-4444', 'EMPRESA', 'Empresa de automóveis');

-- Inserir automóveis
INSERT INTO automoveis (matricula, ano, marca, modelo, placa, valor_aluguel, tipo_contrato, status, observacoes) VALUES
('AUTO001', 2023, 'Toyota', 'Corolla', 'ABC-1234', 150.00, 'CLIENTE', 'DISPONIVEL', 'Veículo em excelente estado'),
('AUTO002', 2022, 'Honda', 'Civic', 'DEF-5678', 180.00, 'EMPRESA', 'DISPONIVEL', 'Veículo seminovo'),
('AUTO003', 2024, 'Volkswagen', 'Golf', 'GHI-9012', 200.00, 'BANCO', 'DISPONIVEL', 'Veículo zero quilômetro'),
('AUTO004', 2021, 'Ford', 'Focus', 'JKL-3456', 120.00, 'CLIENTE', 'ALUGADO', 'Veículo atualmente alugado'),
('AUTO005', 2023, 'Chevrolet', 'Cruze', 'MNO-7890', 160.00, 'EMPRESA', 'DISPONIVEL', 'Veículo econômico');

-- Inserir pedidos de aluguel
INSERT INTO pedidos_aluguel (cliente_id, automovel_id, data_inicio, data_fim, valor_total, status, observacoes, data_criacao) VALUES
(1, 1, '2024-02-01', '2024-02-15', 2250.00, 'PENDENTE', 'Pedido para viagem de negócios', NOW()),
(2, 2, '2024-02-10', '2024-02-20', 1800.00, 'APROVADO', 'Pedido aprovado para evento', NOW()),
(3, 3, '2024-02-05', '2024-02-12', 1400.00, 'REJEITADO', 'Pedido rejeitado por análise financeira', NOW());

-- Inserir contratos
INSERT INTO contratos (cliente_id, automovel_id, pedido_id, data_inicio, data_fim, valor_total, status, data_criacao) VALUES
(2, 2, 2, '2024-02-10', '2024-02-20', 1800.00, 'ATIVO', NOW());

-- Inserir notificações de exemplo
INSERT INTO notificacoes (titulo, mensagem, tipo, usuario_id, data_criacao, lida, ativa) VALUES
('Bem-vindo ao Sistema', 'Seja bem-vindo ao sistema de aluguel de carros!', 'SISTEMA', 1, NOW(), false, true),
('Novo Pedido Criado', 'Um novo pedido de aluguel foi criado e está aguardando análise.', 'PEDIDO_CRIADO', 3, NOW(), false, true),
('Pedido Aprovado', 'Seu pedido de aluguel foi aprovado!', 'PEDIDO_APROVADO', 2, NOW(), true, true);

-- Comentários sobre os dados inseridos
COMMENT ON TABLE usuarios IS 'Usuários do sistema com diferentes tipos de acesso';
COMMENT ON TABLE clientes IS 'Clientes cadastrados no sistema';
COMMENT ON TABLE empregos_rendas IS 'Empregos e rendas dos clientes (máximo 3)';
COMMENT ON TABLE agentes IS 'Agentes (bancos e empresas) que analisam pedidos';
COMMENT ON TABLE automoveis IS 'Automóveis disponíveis para aluguel';
COMMENT ON TABLE pedidos_aluguel IS 'Pedidos de aluguel criados pelos clientes';
COMMENT ON TABLE contratos IS 'Contratos executados após aprovação de pedidos';
COMMENT ON TABLE notificacoes IS 'Sistema de notificações para usuários';
