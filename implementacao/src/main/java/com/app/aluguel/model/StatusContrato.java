package com.app.aluguel.model;

public enum StatusContrato {
    ATIVO("Ativo"),
    FINALIZADO("Finalizado"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    
    StatusContrato(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
