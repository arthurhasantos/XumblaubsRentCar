package com.app.aluguel.model;

public enum TipoAgente {
    BANCO("Banco"),
    EMPRESA("Empresa");
    
    private final String descricao;
    
    TipoAgente(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
