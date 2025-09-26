package com.app.aluguel.model;

public enum TipoNotificacao {
    PEDIDO_CRIADO("Novo Pedido"),
    PEDIDO_APROVADO("Pedido Aprovado"),
    PEDIDO_REJEITADO("Pedido Rejeitado"),
    PEDIDO_CANCELADO("Pedido Cancelado"),
    CONTRATO_CRIADO("Contrato Criado"),
    CONTRATO_FINALIZADO("Contrato Finalizado"),
    PAGAMENTO_VENCIDO("Pagamento Vencido"),
    LEMBRETE("Lembrete"),
    SISTEMA("Notificação do Sistema");
    
    private final String descricao;
    
    TipoNotificacao(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
