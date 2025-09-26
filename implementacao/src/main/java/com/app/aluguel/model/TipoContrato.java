package com.app.aluguel.model;

public enum TipoContrato {
    CLIENTE("Propriedade do Cliente"),
    EMPRESA("Propriedade da Empresa"),
    BANCO("Propriedade do Banco");
    
    private final String descricao;
    
    TipoContrato(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
