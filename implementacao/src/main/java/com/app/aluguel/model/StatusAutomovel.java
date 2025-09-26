package com.app.aluguel.model;

public enum StatusAutomovel {
    DISPONIVEL("Disponível"),
    ALUGADO("Alugado"),
    MANUTENCAO("Em Manutenção"),
    INDISPONIVEL("Indisponível");
    
    private final String descricao;
    
    StatusAutomovel(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
