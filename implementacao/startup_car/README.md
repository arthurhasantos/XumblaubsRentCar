# 🚀 Sistema Xumblaubs RentCar - CRUD de Clientes

Sistema completo de gerenciamento de clientes com frontend Next.js e backend Spring Boot, configurado para criação automática de tabelas e logs detalhados.

## 🎯 Funcionalidades

- ✅ **CRUD Completo de Clientes** (Create, Read, Update, Delete)
- ✅ **Criação Automática de Tabelas** no banco PostgreSQL
- ✅ **Logs Detalhados** para debugging e monitoramento
- ✅ **Interface Moderna** com Next.js e Tailwind CSS
- ✅ **API REST** com Spring Boot
- ✅ **Validações** completas de dados
- ✅ **Monitoramento de Saúde** do banco de dados

## 🚀 Como Executar

### **Método Único - Iniciar Tudo de Uma Vez:**

```bash
# Na raiz do projeto
npm run dev
```

**Isso irá iniciar automaticamente:**
- 🌐 **Frontend Next.js** na porta `3000`
- 🔧 **Backend Spring Boot** na porta `8080`
- 📊 **Logs detalhados** no console e arquivos

### **Métodos Individuais:**

```bash
# Apenas Frontend
npm run dev:frontend

# Apenas Backend
npm run dev:backend
```

### **Outros Comandos Úteis:**

```bash
# Instalar dependências do frontend
npm run install:frontend

# Build do frontend
npm run build

# Instalar todas as dependências
npm run install:all
```

## 🗄️ Configuração do Banco de Dados

### **1. Criar Banco no PgAdmin:**
- Nome: `xumblaubs_rentcar`
- Owner: `postgres`
- Porta: `5432`

### **2. Configuração Automática:**
- ✅ Tabelas criadas automaticamente na inicialização
- ✅ Logs de criação exibidos no console
- ✅ Verificação de saúde do banco

## 📊 Monitoramento e Logs

### **Logs em Tempo Real:**
- **Console:** Logs aparecem durante a execução
- **Arquivo:** `back/logs/xumblaubs-backend.log`
- **Banco:** `back/logs/database-operations.log`

### **Endpoints de Monitoramento:**
- `GET http://localhost:8080/api/actuator/health` - Saúde do banco
- `GET http://localhost:8080/api/actuator/info` - Informações da aplicação
- `GET http://localhost:8080/api/actuator/metrics` - Métricas

### **O que os Logs Mostram:**
```
🚀 Iniciando verificação e criação das tabelas do banco de dados...
✅ Conexão com banco estabelecida: Conexão OK
✅ Tabela 'clientes' criada com sucesso!
📊 Tabela 'clientes' contém 0 registros
✅ Tabela 'users' criada com sucesso!
📊 Tabela 'users' contém 0 registros
✅ Inicialização do banco de dados concluída com sucesso!
```

## 🧪 Testando o Sistema

### **1. Acessar o Frontend:**
```
http://localhost:3000
```

### **2. Credenciais de Login:**
```
Email: admin@admin.com
Senha: admin
```

### **3. Testar API Diretamente:**
```bash
# Usar os arquivos de teste
back/test-clientes.http
back/test-database-creation.http
```

### **4. Exemplo de Criação de Cliente:**
```bash
POST http://localhost:8080/api/clientes
Content-Type: application/json

{
  "rg": "1234567890",
  "cpf": "123.456.789-00",
  "nome": "João Silva",
  "endereco": "Rua das Flores, 123",
  "profissao": "Engenheiro"
}
```

## 📁 Estrutura do Projeto

```
startup_car/
├── front/                 # Frontend Next.js
│   ├── app/              # App Router (Next.js 13+)
│   │   ├── clientes/     # Página CRUD de clientes
│   │   ├── signin/       # Página de login
│   │   ├── signup/       # Página de registro
│   │   ├── components/   # Componentes específicos do app
│   │   ├── hooks/        # Hooks customizados
│   │   ├── types/        # Tipos TypeScript
│   │   └── utils/        # Utilitários
│   ├── components/       # Componentes globais
│   ├── contexts/         # Contextos (Auth)
│   ├── lib/              # Bibliotecas e configurações
│   ├── public/           # Assets estáticos
│   ├── styles/           # Estilos globais
│   ├── types/            # Tipos globais
│   ├── package.json      # Dependências frontend
│   ├── next.config.js    # Configuração Next.js
│   └── tailwind.config.js # Configuração Tailwind
├── back/                 # Backend Spring Boot
│   ├── src/main/java/com/xumblaubs/
│   │   ├── config/       # Configurações e inicializadores
│   │   ├── controller/   # Controllers REST
│   │   ├── entity/       # Entidades JPA
│   │   ├── repository/   # Repositórios
│   │   ├── service/      # Serviços de negócio
│   │   └── security/     # Configurações de segurança
│   ├── logs/             # Arquivos de log
│   └── test-*.http       # Arquivos de teste
├── package.json          # Scripts npm para gerenciar o projeto
└── README.md             # Documentação
```

## 🔧 Configurações Técnicas

### **Frontend:**
- **Next.js 13** com App Router
- **TypeScript** para tipagem
- **Tailwind CSS** para estilização
- **React Hook Form** para formulários
- **React Hot Toast** para notificações

### **Backend:**
- **Spring Boot 3.2** com Java 17
- **Spring Data JPA** para persistência
- **PostgreSQL** como banco de dados
- **Spring Security** com JWT
- **Hibernate** para criação automática de tabelas
- **Spring Boot Actuator** para monitoramento

### **Banco de Dados:**
- **PostgreSQL 15+**
- **Criação automática** de tabelas via Hibernate
- **Logs detalhados** de todas as operações
- **Pool de conexões** HikariCP otimizado

## 🚨 Resolução de Problemas

### **❌ Erro: "Porta já em uso"**
```bash
# Verificar processos nas portas
netstat -ano | findstr :3000
netstat -ano | findstr :8080

# Parar processos se necessário
taskkill /PID <PID> /F
```

### **❌ Erro: "Banco não encontrado"**
1. Verificar se PostgreSQL está rodando
2. Criar banco `xumblaubs_rentcar` no PgAdmin
3. Verificar credenciais em `back/src/main/resources/application.yml`

### **❌ Erro: "Tabelas não criadas"**
- Verificar logs no console
- Verificar arquivo `back/logs/xumblaubs-backend.log`
- Verificar endpoint de saúde: `http://localhost:8080/api/actuator/health`

## 📚 Documentação Adicional

- `back/LOGS_README.md` - Documentação completa dos logs
- `SETUP.md` - Configuração detalhada
- `CHANGES.md` - Histórico de mudanças

## 🎯 Próximos Passos

- [ ] Implementar autenticação real
- [ ] Adicionar mais validações
- [ ] Implementar testes automatizados
- [ ] Adicionar documentação da API
- [ ] Configurar CI/CD

---

**🚀 Sistema pronto para uso! Execute `npm run dev` e comece a usar o CRUD de clientes.**