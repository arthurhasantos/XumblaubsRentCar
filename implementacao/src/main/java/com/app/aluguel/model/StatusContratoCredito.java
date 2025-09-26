package com.app.aluguel.model;

public enum StatusContratoCredito {
    ATIVO("Ativo"),
    QUITADO("Quitado"),
    CANCELADO("Cancelado");
    
    private final String descricao;
    
    StatusContratoCredito(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
