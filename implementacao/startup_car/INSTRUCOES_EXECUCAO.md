# Instruções de Execução - CRUD de Clientes

## Pré-requisitos

- Java 17 ou superior
- Node.js 18 ou superior
- PostgreSQL (opcional - pode usar H2 em memória para testes)

## Passos para Executar

### 1. Configurar o Backend

```bash
# Navegar para a pasta do backend
cd cursor/startup_car/back

# Compilar o projeto
./mvnw clean compile

# Executar o backend
./mvnw spring-boot:run
```

O backend estará disponível em: `http://localhost:8080`

### 2. Configurar o Frontend

```bash
# Navegar para a pasta do frontend (em outro terminal)
cd cursor/startup_car

# Instalar dependências
npm install

# Executar o frontend
npm run dev
```

O frontend estará disponível em: `http://localhost:3000`

### 3. Acessar o Sistema

1. Abra o navegador e acesse: `http://localhost:3000`
2. Clique em "Clientes" no menu principal
3. Ou acesse diretamente: `http://localhost:3000/clientes`

## Funcionalidades Disponíveis

### ✅ Criar Cliente
- Clique em "+ Novo Cliente"
- Preencha os campos obrigatórios:
  - Nome
  - CPF (formato: 000.000.000-00)
  - Telefone (formato: (00) 00000-0000)
  - Email
- Endereço é opcional
- Clique em "Criar"

### ✅ Listar Clientes
- Visualize todos os clientes na tabela
- Use o checkbox "Mostrar inativos" para ver clientes desativados

### ✅ Buscar Clientes
- Digite o nome do cliente no campo de busca
- A busca é feita em tempo real

### ✅ Editar Cliente
- Clique em "Editar" na linha do cliente desejado
- Modifique os campos necessários
- Clique em "Atualizar"

### ✅ Desativar Cliente
- Clique em "Desativar" na linha do cliente
- Confirme a ação
- O cliente será marcado como inativo

### ✅ Ativar Cliente
- Para clientes inativos, clique em "Ativar"
- O cliente voltará ao status ativo

## Testando a API

Use o arquivo `test-clientes.http` para testar os endpoints diretamente:

1. Abra o arquivo no VS Code
2. Instale a extensão "REST Client" se não tiver
3. Clique em "Send Request" acima de cada endpoint

## Estrutura de Dados

### Cliente
```json
{
  "id": 1,
  "nome": "João Silva",
  "cpf": "123.456.789-00",
  "telefone": "(11) 99999-9999",
  "email": "joao.silva@email.com",
  "endereco": "Rua das Flores, 123",
  "ativo": true,
  "createdAt": "2024-01-01T10:00:00",
  "updatedAt": "2024-01-01T10:00:00"
}
```

## Validações

### Backend
- Nome: obrigatório, máximo 100 caracteres
- CPF: obrigatório, formato 000.000.000-00, único
- Telefone: obrigatório, formato (00) 00000-0000
- Email: obrigatório, formato válido, único
- Endereço: opcional, máximo 200 caracteres

### Frontend
- Validação em tempo real
- Formatação automática de CPF e telefone
- Mensagens de erro específicas

## Solução de Problemas

### Backend não inicia
- Verifique se a porta 8080 está livre
- Verifique se o Java 17+ está instalado
- Execute: `./mvnw clean install`

### Frontend não inicia
- Verifique se a porta 3000 está livre
- Verifique se o Node.js 18+ está instalado
- Execute: `npm install` novamente

### Erro de CORS
- O CORS já está configurado no backend
- Verifique se o backend está rodando na porta 8080

### Erro de conexão
- Verifique se ambos os serviços estão rodando
- Verifique as URLs nos logs do navegador

## Logs Úteis

### Backend
```bash
# Ver logs do Spring Boot
tail -f logs/spring.log
```

### Frontend
```bash
# Ver logs do Next.js
# Os logs aparecem no terminal onde executou npm run dev
```

## Próximos Passos

1. Implementar CRUD para Carros
2. Implementar CRUD para Aluguéis
3. Adicionar autenticação
4. Implementar relatórios
5. Adicionar testes automatizados
