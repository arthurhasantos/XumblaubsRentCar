# CRUD de Clientes - Sistema de Aluguel de Carros

## Visão Geral

Este documento descreve a implementação completa do CRUD (Create, Read, Update, Delete) para a entidade Cliente no sistema de aluguel de carros.

## Funcionalidades Implementadas

### Backend (Spring Boot)

#### Entidade Cliente
- **Localização**: `back/src/main/java/com/sismop/entity/Cliente.java`
- **Campos**:
  - `id`: Identificador único (auto-gerado)
  - `nome`: Nome completo do cliente (obrigatório, máx 100 caracteres)
  - `cpf`: CPF no formato 000.000.000-00 (obrigatório, único)
  - `telefone`: Telefone no formato (00) 00000-0000 (obrigatório)
  - `email`: Email válido (obrigatório, único)
  - `endereco`: Endereço completo (opcional, máx 200 caracteres)
  - `ativo`: Status do cliente (padrão: true)
  - `createdAt`: Data de criação (auto-gerada)
  - `updatedAt`: Data de atualização (auto-gerada)

#### Repository
- **Localização**: `back/src/main/java/com/sismop/repository/ClienteRepository.java`
- **Métodos disponíveis**:
  - `findByCpf(String cpf)`: Buscar por CPF
  - `findByEmail(String email)`: Buscar por email
  - `existsByCpf(String cpf)`: Verificar se CPF existe
  - `existsByEmail(String email)`: Verificar se email existe
  - `findByAtivoTrue()`: Buscar apenas clientes ativos
  - `findByNomeContainingAndAtivoTrue(String nome)`: Buscar por nome (parcial)
  - `findByCpfContainingAndAtivoTrue(String cpf)`: Buscar por CPF (parcial)

#### DTOs
- **ClienteRequest**: `back/src/main/java/com/sismop/dto/ClienteRequest.java`
  - DTO para criação e atualização de clientes
  - Inclui validações de formato
- **ClienteResponse**: `back/src/main/java/com/sismop/dto/ClienteResponse.java`
  - DTO para resposta da API
  - Inclui todos os campos da entidade

#### Service
- **Localização**: `back/src/main/java/com/sismop/service/ClienteService.java`
- **Métodos implementados**:
  - `findAll()`: Listar todos os clientes
  - `findAllAtivos()`: Listar apenas clientes ativos
  - `findById(Long id)`: Buscar por ID
  - `findByCpf(String cpf)`: Buscar por CPF
  - `findByNomeContaining(String nome)`: Buscar por nome
  - `create(ClienteRequest)`: Criar novo cliente
  - `update(Long id, ClienteRequest)`: Atualizar cliente
  - `delete(Long id)`: Desativar cliente (soft delete)
  - `hardDelete(Long id)`: Remover permanentemente
  - `activate(Long id)`: Reativar cliente

#### Controller
- **Localização**: `back/src/main/java/com/sismop/controller/ClienteController.java`
- **Endpoints disponíveis**:
  - `GET /api/clientes` - Listar clientes (com parâmetro `incluirInativos`)
  - `GET /api/clientes/{id}` - Buscar cliente por ID
  - `GET /api/clientes/cpf/{cpf}` - Buscar cliente por CPF
  - `GET /api/clientes/buscar?nome={nome}` - Buscar clientes por nome
  - `POST /api/clientes` - Criar novo cliente
  - `PUT /api/clientes/{id}` - Atualizar cliente
  - `DELETE /api/clientes/{id}` - Desativar cliente
  - `DELETE /api/clientes/{id}/hard` - Remover permanentemente
  - `PUT /api/clientes/{id}/ativar` - Reativar cliente

### Frontend (Next.js)

#### Página de Gerenciamento
- **Localização**: `app/clientes/page.tsx`
- **Funcionalidades**:
  - Listagem de clientes em tabela responsiva
  - Busca por nome em tempo real
  - Filtro para mostrar/ocultar clientes inativos
  - Modal para criação e edição de clientes
  - Validação de formulários
  - Formatação automática de CPF e telefone
  - Ações de ativar/desativar clientes
  - Feedback visual com toasts

#### Navegação
- **Localização**: `components/Header/menuData.tsx`
- Adicionado item "Clientes" no menu principal

## Como Usar

### 1. Iniciar o Backend
```bash
cd cursor/startup_car/back
./mvnw spring-boot:run
```
O backend estará disponível em `http://localhost:8080`

### 2. Iniciar o Frontend
```bash
cd cursor/startup_car
npm run dev
```
O frontend estará disponível em `http://localhost:3000`

### 3. Acessar a Página de Clientes
- Navegue para `http://localhost:3000/clientes`
- Ou clique em "Clientes" no menu principal

## Validações Implementadas

### Backend
- **Nome**: Obrigatório, máximo 100 caracteres
- **CPF**: Obrigatório, formato 000.000.000-00, único
- **Telefone**: Obrigatório, formato (00) 00000-0000
- **Email**: Obrigatório, formato válido, único
- **Endereço**: Opcional, máximo 200 caracteres

### Frontend
- Validação em tempo real dos campos
- Formatação automática de CPF e telefone
- Mensagens de erro específicas
- Prevenção de duplicação de CPF e email

## Recursos Técnicos

### Backend
- **Spring Boot 3.2.0**
- **Spring Data JPA** para persistência
- **Spring Validation** para validações
- **PostgreSQL** como banco de dados
- **CORS** configurado para permitir requisições do frontend

### Frontend
- **Next.js 13.5.6**
- **React Hook Form** para gerenciamento de formulários
- **React Hot Toast** para notificações
- **Tailwind CSS** para estilização
- **TypeScript** para tipagem

## Estrutura de Arquivos

```
cursor/startup_car/
├── back/
│   └── src/main/java/com/sismop/
│       ├── entity/Cliente.java
│       ├── repository/ClienteRepository.java
│       ├── dto/
│       │   ├── ClienteRequest.java
│       │   └── ClienteResponse.java
│       ├── service/ClienteService.java
│       └── controller/ClienteController.java
├── app/
│   └── clientes/page.tsx
└── components/Header/menuData.tsx
```

## Próximos Passos

Para expandir o sistema, considere implementar:
1. CRUD para Carros
2. CRUD para Aluguéis
3. Relatórios e dashboards
4. Autenticação e autorização
5. Upload de documentos
6. Histórico de alterações
7. Exportação de dados
