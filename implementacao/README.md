# Sistema de Aluguel de Carros - Xumblaubs Rent Car

Sistema completo de gestão de aluguel de carros com análise financeira por agentes (empresas e bancos).

## Funcionalidades

### Para Clientes
- Cadastro completo com dados pessoais e profissionais
- Gestão de empregos e rendas (máximo 3)
- Criação, modificação, consulta e cancelamento de pedidos de aluguel
- Visualização de contratos ativos

### Para Agentes (Empresas e Bancos)
- Análise financeira de pedidos de aluguel
- Aprovação ou rejeição de pedidos
- Gestão de contratos de crédito
- Avaliação de capacidade de pagamento

### Sistema Geral
- Gestão completa de automóveis (matrícula, ano, marca, modelo, placa)
- Controle de propriedade (cliente, empresa ou banco)
- Sistema de contratos com crédito
- Interface web moderna e responsiva
- Persistência no PostgreSQL

## Tecnologias Utilizadas

- **Backend**: Spring Boot 3.5.6
- **Banco de Dados**: PostgreSQL
- **Frontend**: Thymeleaf + Bootstrap 5
- **Segurança**: Spring Security
- **Java**: 21

## Pré-requisitos

1. **Java 21** instalado
2. **PostgreSQL** instalado e rodando
3. **Maven** (incluído no projeto)

## Instalação e Configuração

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

### 2. Configurar a Aplicação

Edite o arquivo `src/main/resources/application.properties` se necessário:

```properties
# Configuração do PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/xumblaubs_rentcar
spring.datasource.username=postgres
spring.datasource.password=1234
```

### 3. Executar a Aplicação

```bash
# Navegar para o diretório do projeto
cd implementacao

# Executar a aplicação
./mvnw spring-boot:run

# Ou no Windows
mvnw.cmd spring-boot:run
```

### 4. Acessar o Sistema

- **URL**: http://localhost:8080
- **Login padrão**: admin / admin123

## Estrutura do Projeto

```
src/main/java/com/app/aluguel/
├── config/                 # Configurações (Security, etc.)
├── controller/            # Controladores REST
├── controller/ui/         # Controladores para interface web
├── model/                 # Entidades JPA
├── repository/            # Repositórios JPA
└── service/              # Serviços de negócio

src/main/resources/
├── templates/             # Páginas Thymeleaf
│   ├── auth/             # Páginas de autenticação
│   ├── clientes/         # Páginas de clientes
│   └── ...
└── application.properties # Configurações
```

## Modelos de Dados

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

## API REST

### Endpoints Principais

- `GET /api/clientes` - Listar clientes
- `POST /api/clientes` - Criar cliente
- `GET /api/automoveis` - Listar automóveis
- `GET /api/pedidos` - Listar pedidos
- `POST /api/pedidos` - Criar pedido
- `POST /api/pedidos/{id}/aprovar` - Aprovar pedido
- `POST /api/pedidos/{id}/rejeitar` - Rejeitar pedido

## Interface Web

### Páginas Disponíveis

- `/` - Página inicial
- `/dashboard` - Dashboard principal
- `/login` - Login de usuário
- `/register` - Cadastro de usuário
- `/clientes` - Gestão de clientes
- `/automoveis` - Gestão de automóveis
- `/pedidos` - Gestão de pedidos
- `/contratos` - Gestão de contratos

## Segurança

- Autenticação obrigatória para uso do sistema
- Diferentes tipos de usuário (Cliente, Agente, Admin)
- Criptografia de senhas com BCrypt
- Validação de dados de entrada

## Desenvolvimento

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

## Suporte

Para dúvidas ou problemas, consulte a documentação do Spring Boot ou PostgreSQL.

## Licença

Este projeto é para fins educacionais e de demonstração.
