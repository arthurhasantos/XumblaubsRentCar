# Mudanças Realizadas

## Reorganização da Estrutura do Projeto

### ✅ Alterações Implementadas:

1. **Pasta `backend` renomeada para `back`**
   - Todos os arquivos do Spring Boot foram movidos para a nova pasta `back/`
   - Scripts de execução atualizados (`start-backend.bat`)
   - Documentação atualizada (`SETUP.md`)

2. **Pasta `js` criada para arquivos JavaScript (REVERTIDO)**
   - Arquivos de configuração movidos de volta para a raiz
   - `next.config.js` - necessário na raiz para o Next.js
   - `tailwind.config.js` - necessário na raiz para o Tailwind CSS
   - `postcss.config.js` - necessário na raiz para o PostCSS

### 📁 Nova Estrutura:

```
sismop/
├── app/                    # Frontend Next.js
├── components/             # Componentes React
├── contexts/              # Contextos React (Auth)
├── lib/                   # Utilitários e serviços
├── types/                 # Tipos TypeScript
├── next.config.js         # Configuração Next.js
├── tailwind.config.js     # Configuração Tailwind CSS
├── postcss.config.js      # Configuração PostCSS
├── back/                  # Backend Spring Boot (RENOMEADO)
│   ├── pom.xml
│   ├── src/main/java/com/sismop/
│   └── src/main/resources/
└── SETUP.md              # Documentação atualizada
```

### 🔧 Arquivos Atualizados:

- `SETUP.md` - Instruções atualizadas para refletir nova estrutura
- `start-backend.bat` - Script atualizado para usar pasta `back/`

### 🚀 Como Executar (Atualizado):

**Backend:**
```bash
cd back
./mvnw spring-boot:run
```

**Frontend:**
```bash
npm run dev
```

Todas as funcionalidades do sistema de login continuam funcionando normalmente com a nova estrutura organizacional.
