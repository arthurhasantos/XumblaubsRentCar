# 📊 Sistema de Logs e Monitoramento - Xumblaubs Backend

## 🚀 Configuração de Criação Automática de Tabelas

O projeto está configurado para **criar automaticamente** todas as tabelas do banco de dados quando iniciado.

### ⚙️ Configurações Principais

**application.yml:**
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: create-drop  # Cria e recria tabelas a cada inicialização
    show-sql: true           # Mostra SQL no console
    properties:
      hibernate:
        hbm2ddl:
          auto: create-drop  # Garantia adicional de criação
```

## 📝 Sistema de Logs Implementado

### 🎯 Logs por Categoria

#### 1. **Logs de Aplicação** (`logs/xumblaubs-backend.log`)
- ✅ Inicialização da aplicação
- ✅ Criação de tabelas
- ✅ Operações CRUD
- ✅ Erros da aplicação

#### 2. **Logs de Banco de Dados** (`logs/database-operations.log`)
- ✅ SQL executado (CREATE, INSERT, UPDATE, DELETE, SELECT)
- ✅ Parâmetros das consultas
- ✅ Tempo de execução
- ✅ Conexões com banco

#### 3. **Logs no Console**
- ✅ Logs em tempo real
- ✅ Cores e formatação
- ✅ Logs de erro destacados

### 🔍 O que os Logs Mostram

#### **Durante a Inicialização:**
```
🚀 Iniciando verificação e criação das tabelas do banco de dados...
🔍 Verificando conexão com o banco de dados...
✅ Conexão com banco estabelecida: Conexão OK
🔍 Verificando criação das tabelas...
✅ Tabela 'clientes' criada com sucesso!
📊 Tabela 'clientes' contém 0 registros
✅ Tabela 'users' criada com sucesso!
📊 Tabela 'users' contém 0 registros
🔍 Listando entidades JPA mapeadas...
📋 Entidades JPA encontradas: 2
  - Cliente (tabela: Cliente)
  - User (tabela: User)
✅ Inicialização do banco de dados concluída com sucesso!
```

#### **Durante Operações CRUD:**
```
📝 INSERT realizado na tabela: Cliente - ID: 1
🔄 UPDATE realizado na tabela: Cliente - ID: 1
🗑️ DELETE realizado na tabela: Cliente - ID: 1
```

#### **SQL Executado:**
```
Hibernate: create table clientes (id bigserial not null, ativo boolean not null, created_at timestamp(6) not null, cpf varchar(14) not null, endereco varchar(200) not null, nome varchar(100) not null, profissao varchar(100) not null, rg varchar(20) not null, updated_at timestamp(6), primary key (id))
Hibernate: create table users (id bigserial not null, created_at timestamp(6) not null, email varchar(50) not null, enabled boolean not null, name varchar(50) not null, password varchar(120) not null, role varchar(20) not null, updated_at timestamp(6), primary key (id))
```

## 🏥 Monitoramento de Saúde

### **Endpoint de Saúde:**
```
GET http://localhost:8080/api/actuator/health
```

**Resposta de Sucesso:**
```json
{
  "status": "UP",
  "components": {
    "database": {
      "status": "UP",
      "details": {
        "connection": "OK",
        "tables": {
          "clientes_count": 0,
          "clientes_status": "OK",
          "users_count": 0,
          "users_status": "OK",
          "database_version": "PostgreSQL 15.4"
        }
      }
    }
  }
}
```

### **Outros Endpoints de Monitoramento:**
- `GET /api/actuator/info` - Informações da aplicação
- `GET /api/actuator/metrics` - Métricas da aplicação

## 🚀 Como Executar e Verificar

### **1. Iniciar o Backend:**
```bash
cd implementacao/startup_car/back
start-backend.bat
```

### **2. Verificar Logs em Tempo Real:**
- **Console:** Logs aparecem diretamente no terminal
- **Arquivo:** `logs/xumblaubs-backend.log`
- **Banco:** `logs/database-operations.log`

### **3. Testar Criação de Tabelas:**
```bash
# Usar o arquivo de teste
test-database-creation.http
```

### **4. Verificar Saúde do Banco:**
```bash
curl http://localhost:8080/api/actuator/health
```

## 🔧 Resolução de Problemas

### **❌ Erro: "Tabela não encontrada"**
**Logs mostram:**
```
⚠️ Tabela 'clientes' não foi encontrada!
❌ Erro durante a inicialização do banco de dados
```

**Soluções:**
1. Verificar se o PostgreSQL está rodando
2. Verificar se o banco `xumblaubs_rentcar` existe
3. Verificar credenciais no `application.yml`

### **❌ Erro: "Conexão recusada"**
**Logs mostram:**
```
❌ Erro na conexão com o banco: Connection refused
```

**Soluções:**
1. Verificar se PostgreSQL está rodando na porta 5432
2. Verificar se o usuário `postgres` tem acesso
3. Verificar se a senha está correta

### **❌ Erro: "DDL falhou"**
**Logs mostram:**
```
❌ Erro ao verificar tabelas: DDL execution failed
```

**Soluções:**
1. Verificar permissões do usuário no banco
2. Verificar se o schema `public` existe
3. Verificar se não há conflitos de nomes

## 📁 Estrutura de Arquivos de Log

```
back/
├── logs/
│   ├── xumblaubs-backend.log          # Log principal da aplicação
│   ├── database-operations.log        # Log específico do banco
│   └── xumblaubs-backend.2024-09-19.0.log  # Logs rotativos
├── src/main/resources/
│   ├── application.yml                # Configuração principal
│   └── logback-spring.xml            # Configuração de logs
└── test-database-creation.http       # Testes de verificação
```

## 🎯 Benefícios Implementados

✅ **Criação Automática:** Tabelas criadas automaticamente  
✅ **Logs Detalhados:** Rastreamento completo de operações  
✅ **Monitoramento:** Saúde do banco em tempo real  
✅ **Debugging:** Logs específicos para cada categoria  
✅ **Rotação:** Logs não crescem indefinidamente  
✅ **Performance:** Logs otimizados para produção  

## 🚨 Importante

- **Desenvolvimento:** `ddl-auto: create-drop` (recria tabelas)
- **Produção:** Alterar para `ddl-auto: validate` ou `update`
- **Logs:** Arquivos são criados automaticamente na pasta `logs/`
- **Monitoramento:** Endpoints disponíveis apenas em desenvolvimento
