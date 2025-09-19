# Sistema de Login - XUMBLAUBS

Este projeto implementa um sistema completo de autenticação com frontend Next.js e backend Spring Boot.

## Estrutura do Projeto

```
xumblaubs/
├── app/                    # Frontend Next.js
├── components/             # Componentes React
├── contexts/              # Contextos React (Auth)
├── lib/                   # Utilitários e serviços
├── types/                 # Tipos TypeScript
├── js/                    # Arquivos JavaScript
├── back/                  # Backend Spring Boot
└── SETUP.md              # Este arquivo
```

## Configuração do Backend (Spring Boot)

### 1. Banco de Dados PostgreSQL

1. Instale o PostgreSQL e pgAdmin 4
2. Crie um banco de dados chamado `xumblaubs_rentcar`
3. Configure as credenciais no arquivo `back/src/main/resources/application.yml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/xumblaubs_rentcar
    username: postgres
    password: admin
```

### 2. Executar o Backend

```bash
cd back
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080/api`

## Configuração do Frontend (Next.js)

### 1. Instalar Dependências

```bash
npm install
```

### 2. Configurar Variáveis de Ambiente

Crie um arquivo `.env.local` na raiz do projeto:

```env
NEXT_PUBLIC_API_URL=http://localhost:8080/api
```

### 3. Executar o Frontend

```bash
npm run dev
```

O frontend estará disponível em: `http://localhost:3000`

## Funcionalidades Implementadas

### Backend
- ✅ Autenticação JWT
- ✅ Registro de usuários
- ✅ Login de usuários
- ✅ Proteção de rotas
- ✅ Integração com PostgreSQL
- ✅ Criptografia de senhas (BCrypt)
- ✅ CORS configurado

### Frontend
- ✅ Páginas de login e registro
- ✅ Validação de formulários
- ✅ Gerenciamento de estado de autenticação
- ✅ Proteção de rotas
- ✅ Interceptadores HTTP
- ✅ Notificações (toast)
- ✅ Interface responsiva

## Endpoints da API

### Autenticação
- `POST /api/auth/signin` - Login
- `POST /api/auth/signup` - Registro
- `POST /api/auth/signout` - Logout

### Teste
- `GET /api/test/all` - Público
- `GET /api/test/user` - Requer autenticação
- `GET /api/test/admin` - Requer role ADMIN

## Como Testar

1. Acesse `http://localhost:3000`
2. Clique em "Sign Up" para criar uma conta
3. Faça login com suas credenciais
4. Acesse o dashboard em `/dashboard`
5. Teste o logout

## Estrutura do Banco de Dados

### Tabela: users
- `id` (BIGINT, PRIMARY KEY)
- `name` (VARCHAR(50), NOT NULL)
- `email` (VARCHAR(50), UNIQUE, NOT NULL)
- `password` (VARCHAR(120), NOT NULL)
- `role` (ENUM: USER, ADMIN)
- `enabled` (BOOLEAN, DEFAULT true)
- `created_at` (TIMESTAMP)
- `updated_at` (TIMESTAMP)

## Próximos Passos

- [ ] Implementar refresh token
- [ ] Adicionar recuperação de senha
- [ ] Implementar verificação de email
- [ ] Adicionar mais roles e permissões
- [ ] Implementar rate limiting
- [ ] Adicionar logs de auditoria
