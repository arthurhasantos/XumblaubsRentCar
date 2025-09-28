package com.xumblaubs.entity;

public enum StatusPedido {
    PENDENTE("Pendente", "Aguardando aprovação"),
    APROVADO("Aprovado", "Pedido aprovado pelo administrador"),
    REJEITADO("Rejeitado", "Pedido rejeitado pelo administrador"),
    EM_ANDAMENTO("Em Andamento", "Aluguel em andamento"),
    FINALIZADO("Finalizado", "Aluguel finalizado");

    private final String descricao;
    private final String detalhes;

    StatusPedido(String descricao, String detalhes) {
        this.descricao = descricao;
        this.detalhes = detalhes;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getDetalhes() {
        return detalhes;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
