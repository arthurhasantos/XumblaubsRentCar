# Sistema de Aluguel de Carros - Xumblaubs Rent Car

Sistema completo de gestão de aluguel de carros com análise financeira por agentes (empresas e bancos).

## 🚗 Funcionalidades

### Para Clientes
- ✅ Cadastro completo com dados pessoais e profissionais
- ✅ Gestão de empregos e rendas (máximo 3)
- ✅ Criação, modificação, consulta e cancelamento de pedidos de aluguel
- ✅ Visualização de contratos ativos
- ✅ Sistema de notificações em tempo real

### Para Agentes (Empresas e Bancos)
- ✅ Análise financeira automatizada de pedidos de aluguel
- ✅ Aprovação ou rejeição de pedidos com parecer detalhado
- ✅ Gestão de contratos de crédito
- ✅ Avaliação de capacidade de pagamento
- ✅ Dashboard com estatísticas

### Sistema Geral
- ✅ Gestão completa de automóveis (matrícula, ano, marca, modelo, placa)
- ✅ Controle de propriedade (cliente, empresa ou banco)
- ✅ Sistema de contratos com crédito
- ✅ Interface web moderna e responsiva
- ✅ Persistência no PostgreSQL
- ✅ Sistema de autenticação e autorização
- ✅ API REST completa

## 🛠️ Tecnologias Utilizadas

- **Backend**: Spring Boot 3.5.6
- **Banco de Dados**: PostgreSQL
- **Frontend**: Thymeleaf + Bootstrap 5
- **Segurança**: Spring Security
- **Java**: 21
- **Build**: Maven

## 📋 Pré-requisitos

1. **Java 21** instalado
2. **PostgreSQL** instalado e rodando
3. **Maven** (incluído no projeto)

## 🚀 Instalação e Configuração

### 1. Configurar o PostgreSQL

```sql
-- Execute no PostgreSQL
CREATE DATABASE xumblaubs_rentcar
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
```

### 2. Executar a Aplicação

#### Windows:
```bash
cd implementacao
run.bat
```

#### Linux/Mac:
```bash
cd implementacao
chmod +x run.sh
./run.sh
```

#### Manual:
```bash
cd implementacao
./mvnw spring-boot:run
```

### 3. Acessar o Sistema

- **URL**: http://localhost:8080
- **Login padrão**: admin / admin123

## 📁 Estrutura do Projeto

```
implementacao/
├── src/main/java/com/app/aluguel/
│   ├── config/                 # Configurações (Security, etc.)
│   ├── controller/            # Controladores REST
│   ├── controller/ui/         # Controladores para interface web
│   ├── model/                 # Entidades JPA
│   ├── repository/            # Repositórios JPA
│   └── service/              # Serviços de negócio
├── src/main/resources/
│   ├── templates/             # Páginas Thymeleaf
│   └── application.properties # Configurações
├── database/                  # Scripts SQL
├── run.bat                    # Script Windows
├── run.sh                     # Script Linux/Mac
└── README.md                  # Documentação
```

## 🎯 Modelos de Dados

### Cliente
- Dados pessoais (nome, RG, CPF, endereço, profissão)
- Contato (email, telefone)
- Empregos e rendas (máximo 3)
- Relacionamento com usuário do sistema

### Automóvel
- Identificação (matrícula, placa)
- Características (ano, marca, modelo)
- Valor de aluguel
- Tipo de contrato (propriedade do cliente, empresa ou banco)
- Status (disponível, alugado, manutenção, indisponível)

### Pedido de Aluguel
- Cliente solicitante
- Automóvel desejado
- Período de aluguel
- Valor total
- Status (pendente, aprovado, rejeitado, cancelado, executado)
- Análise financeira por agente

### Contrato
- Execução de pedido aprovado
- Período de vigência
- Status (ativo, finalizado, cancelado)
- Possível associação com contrato de crédito

### Agente
- Empresas ou bancos
- Dados corporativos (nome, CNPJ, endereço)
- Capacidade de análise financeira
- Gestão de contratos de crédito

## 🔌 API REST

### Endpoints Principais

- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Criar cliente
- `GET /api/automoveis` - Listar automóveis
- `GET /api/pedidos` - Listar pedidos
- `POST /api/pedidos` - Criar pedido
- `POST /api/pedidos/{id}/aprovar` - Aprovar pedido
- `POST /api/pedidos/{id}/rejeitar` - Rejeitar pedido
- `GET /api/avaliacao/pedido/{id}` - Analisar pedido financeiramente
- `GET /api/notificacoes/usuario/{id}` - Listar notificações

## 🌐 Interface Web

### Páginas Disponíveis

- `/` - Página inicial
- `/dashboard` - Dashboard principal
- `/login` - Login de usuário
- `/register` - Cadastro de usuário
- `/clientes` - Gestão de clientes
- `/automoveis` - Gestão de automóveis
- `/pedidos` - Gestão de pedidos
- `/contratos` - Gestão de contratos

## 🔒 Segurança

- Autenticação obrigatória para uso do sistema
- Diferentes tipos de usuário (Cliente, Agente, Admin)
- Criptografia de senhas com BCrypt
- Validação de dados de entrada

## 📊 Sistema de Avaliação Financeira

O sistema inclui análise financeira automatizada que considera:

- **Renda do cliente** (40% do score)
- **Estabilidade profissional** (30% do score)
- **Valor do aluguel** (20% do score)
- **Dados pessoais** (10% do score)

### Critérios de Aprovação:
- Score ≥ 80: Aprovação automática
- Score ≥ 60: Aprovação com análise
- Score < 60: Rejeição ou análise manual

## 🔔 Sistema de Notificações

- Notificações em tempo real
- Diferentes tipos (pedidos, contratos, pagamentos)
- Controle de leitura
- Notificações para todos os usuários

## 🧪 Dados de Exemplo

O sistema inclui dados de exemplo para teste:
- 3 clientes com diferentes perfis
- 5 automóveis de diferentes tipos
- Pedidos em diferentes status
- Notificações de exemplo

## 📝 Desenvolvimento

### Adicionar Nova Funcionalidade

1. Criar/atualizar modelo em `model/`
2. Criar repositório em `repository/`
3. Criar serviço em `service/`
4. Criar controlador em `controller/`
5. Criar páginas Thymeleaf em `templates/`

### Executar Testes

```bash
./mvnw test
```

## 📞 Suporte

Para dúvidas ou problemas, consulte a documentação do Spring Boot ou PostgreSQL.

## 📄 Licença

Este projeto é para fins educacionais e de demonstração.

---

**Desenvolvido com ❤️ para o sistema de aluguel de carros Xumblaubs Rent Car**
Laboratório de Desenvolvimento de Software - Engenharia de Software - Campus Lourdes - PLU - Noite - 2025/2 - Laboratório 2

# Histórias do Usuário – Sistema de Gestão de Aluguéis de Automóveis

## 1. Cadastro de Cliente
**História:**  
Como um **cliente**, quero realizar meu **cadastro no sistema** para poder **efetuar pedidos de aluguel**.

**Critérios de Aceitação:**
- O cadastro deve exigir: RG, CPF, Nome, Endereço, Profissão.
- O cliente pode informar até **3 empregadoras** e respectivos rendimentos.
- O sistema deve validar CPF e campos obrigatórios.

---

## 2. Introduzir Pedido de Aluguel
**História:**  
Como um **cliente**, quero **introduzir um pedido de aluguel** para solicitar um automóvel.

**Critérios de Aceitação:**
- O pedido deve conter os dados do automóvel desejado e período do aluguel.
- O sistema deve registrar a data de criação do pedido.
- O pedido só pode ser submetido se o cliente estiver autenticado.

---

## 3. Modificar Pedido (Cliente)
**História:**  
Como um **cliente**, quero **modificar meus pedidos** para corrigir ou atualizar informações.

**Critérios de Aceitação:**
- O cliente só pode modificar pedidos em estado **Rascunho** ou **Submetido**.
- Alterações devem ser registradas no histórico do pedido.
- Após modificação, o pedido deve manter consistência dos dados.

---

## 4. Consultar Pedido
**História:**  
Como um **cliente**, quero **consultar meus pedidos** para acompanhar o status.

**Critérios de Aceitação:**
- O cliente deve visualizar: número, automóvel, status, data de criação.
- O sistema deve permitir listar todos os pedidos do cliente.

---

## 5. Cancelar Pedido
**História:**  
Como um **cliente**, quero **cancelar pedidos** para desistir do aluguel.

**Critérios de Aceitação:**
- O cancelamento só é possível antes da execução do contrato.
- O status deve ser alterado para **Cancelado**.
- O sistema deve registrar a data do cancelamento.

---

## 6. Avaliar Pedido
**História:**  
Como um **agente**, quero **avaliar pedidos** para emitir parecer financeiro.

**Critérios de Aceitação:**
- O agente pode aprovar ou reprovar um pedido.
- O parecer deve conter data, comentário e resultado.
- Pedidos reprovados não podem gerar contratos.

---

## 7. Modificar Pedido (Agente)
**História:**  
Como um **agente**, quero **modificar pedidos** para ajustar dados necessários na análise.

**Critérios de Aceitação:**
- Apenas agentes podem modificar pedidos em **análise**.
- Alterações devem ser registradas em log.
- O cliente deve ser notificado sobre a modificação.

---

## 8. Executar Contrato de Aluguel
**História:**  
Como um **agente**, quero **executar o contrato de aluguel** para formalizar o pedido aprovado.

**Critérios de Aceitação:**
- Apenas pedidos **aprovados** podem gerar contratos.
- O contrato deve estar vinculado a um automóvel.
- O sistema deve armazenar número, datas e valores do contrato.

---

## 9. Associar Contrato de Crédito
**História:**  
Como um **banco**, quero **associar um contrato de crédito** a um aluguel para financiar o cliente.

**Critérios de Aceitação:**
- O crédito deve estar vinculado a um contrato de aluguel.
- O contrato de crédito deve conter valor, prazo e taxa de juros.
- Só bancos podem conceder crédito.

---

## 10. Registrar Automóvel
**História:**  
Como um **agente**, quero **registrar automóveis** para que possam ser alugados.

**Critérios de Aceitação:**
- O registro deve conter: matrícula, ano, marca, modelo e placa.
- O automóvel deve estar vinculado a um proprietário (Cliente, Banco ou Empresa).
- O sistema deve validar duplicidade de matrícula/placa.
- 
