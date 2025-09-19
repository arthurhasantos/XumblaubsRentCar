# XumblaubsRentCar
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
