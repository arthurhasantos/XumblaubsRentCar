# 🚀 Guia de Configuração Inicial - Xumblaubs RentCar

Este guia mostra exatamente o que fazer após clonar o repositório para rodar o projeto pela primeira vez.

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado:

- ✅ **Node.js** (versão 18 ou superior)
- ✅ **npm** (vem com o Node.js)
- ✅ **Java 17** ou superior
- ✅ **Maven** (para o backend Spring Boot)
- ✅ **PostgreSQL** (versão 15 ou superior)
- ✅ **PgAdmin** (opcional, para gerenciar o banco)

## 🔧 Passo a Passo Completo

### **1. Clone o Repositório**
```bash
git clone <URL_DO_REPOSITORIO>
cd XumblaubsRentCar/implementacao/startup_car
```

### **2. Verificar se está no diretório correto**
```bash
# No Windows (PowerShell)
Get-Location
# Deve mostrar: D:\...\XumblaubsRentCar\implementacao\startup_car

# No Linux/Mac
pwd
# Deve mostrar: /caminho/para/XumblaubsRentCar/implementacao/startup_car
```

### **3. Instalar Dependências do Projeto Principal**
```bash
npm install
```

### **4. Instalar Dependências do Frontend**
```bash
cd front
npm install
cd ..
```

### **5. Configurar o Banco de Dados PostgreSQL**

#### **5.1. Criar o Banco de Dados**
1. Abra o **PgAdmin** ou conecte via linha de comando
2. Crie um novo banco de dados com o nome: `xumblaubs_rentcar`
3. Defina o owner como: `postgres`

#### **5.2. Verificar Configurações do Banco**
O arquivo `back/src/main/resources/application.yml` deve ter:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/xumblaubs_rentcar
    username: postgres
    password: sua_senha_aqui
```

### **6. Iniciar o Projeto**

#### **Método Recomendado - Iniciar Tudo de Uma Vez:**
```bash
npm run dev
```

Este comando irá:
- ✅ Iniciar o **Frontend Next.js** na porta `3000`
- ✅ Iniciar o **Backend Spring Boot** na porta `8080`
- ✅ Criar automaticamente as tabelas no banco
- ✅ Mostrar logs detalhados no console

#### **Método Alternativo - Iniciar Separadamente:**

**Terminal 1 - Frontend:**
```bash
npm run dev:frontend
```

**Terminal 2 - Backend:**
```bash
npm run dev:backend
```

### **7. Verificar se Está Funcionando**

#### **7.1. Verificar Portas**
```bash
# No Windows
netstat -ano | findstr ":3000"  # Frontend
netstat -ano | findstr ":8080"  # Backend

# No Linux/Mac
lsof -i :3000  # Frontend
lsof -i :8080  # Backend
```

#### **7.2. Acessar a Aplicação**
- **Frontend:** http://localhost:3000
- **Backend API:** http://localhost:8080/api
- **Health Check:** http://localhost:8080/api/actuator/health

#### **7.3. Credenciais de Login**
```
Email: admin@admin.com
Senha: admin
```

## 🚨 Problemas Comuns e Soluções

### **❌ Erro: "Could not read package.json"**
**Problema:** Você não está no diretório correto do projeto.

**Solução:**
```bash
# Navegue para o diretório correto
cd "caminho/para/XumblaubsRentCar/implementacao/startup_car"

# Verifique se está no lugar certo
ls  # Deve mostrar: front/, back/, package.json, etc.
```

### **❌ Erro: "Porta já em uso"**
**Problema:** As portas 3000 ou 8080 já estão sendo usadas.

**Solução:**
```bash
# No Windows - Encontrar processo usando a porta
netstat -ano | findstr ":3000"
netstat -ano | findstr ":8080"

# Parar o processo (substitua PID pelo número encontrado)
taskkill /PID <PID> /F

# No Linux/Mac
lsof -i :3000
kill -9 <PID>
```

### **❌ Erro: "Banco não encontrado"**
**Problema:** O banco PostgreSQL não foi criado ou não está acessível.

**Solução:**
1. Verificar se PostgreSQL está rodando
2. Criar o banco `xumblaubs_rentcar` no PgAdmin
3. Verificar credenciais no arquivo `application.yml`

### **❌ Erro: "Maven não encontrado"**
**Problema:** Maven não está instalado ou não está no PATH.

**Solução:**
1. Instalar Maven
2. Adicionar Maven ao PATH do sistema
3. Verificar: `mvn --version`

### **❌ Frontend carrega mas Backend não responde**
**Problema:** Backend não iniciou corretamente.

**Solução:**
1. Verificar logs do backend no console
2. Verificar se Java está instalado: `java --version`
3. Verificar se Maven está funcionando: `mvn --version`
4. Tentar iniciar apenas o backend: `npm run dev:backend`

## 📊 Verificação Final

Após seguir todos os passos, você deve ver:

### **No Console:**
```
🚀 Iniciando verificação e criação das tabelas do banco de dados...
✅ Conexão com banco estabelecida: Conexão OK
✅ Tabela 'clientes' criada com sucesso!
✅ Tabela 'users' criada com sucesso!
✅ Inicialização do banco de dados concluída com sucesso!

▲ Next.js 13.5.11
- Local:        http://localhost:3000
✓ Ready in 1428ms
```

### **No Navegador:**
- Acesse http://localhost:3000
- Deve carregar a página de login
- Use as credenciais: admin@admin.com / admin

## 🎯 Comandos Úteis

```bash
# Parar todos os serviços
npm run stop  # ou Ctrl+C

# Reinstalar dependências
rm -rf node_modules front/node_modules
npm install
cd front && npm install && cd ..

# Ver logs do backend
tail -f back/logs/xumblaubs-backend.log

# Testar API
curl http://localhost:8080/api/actuator/health
```

## 📝 Notas Importantes

1. **Sempre navegue para o diretório correto** antes de executar comandos npm
2. **O backend pode demorar alguns minutos** para iniciar na primeira vez
3. **As tabelas são criadas automaticamente** - não precisa criar manualmente
4. **Os logs são salvos** em `back/logs/` para debugging
5. **Use Ctrl+C** para parar os serviços quando terminar

---

**🎉 Pronto! Seu projeto está configurado e funcionando!**

Para futuras execuções, basta navegar para o diretório do projeto e executar:
```bash
npm run dev
```
