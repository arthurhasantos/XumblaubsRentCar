package com.app.aluguel.model;

public enum TipoUsuario {
    CLIENTE("Cliente"),
    AGENTE("Agente"),
    ADMIN("Administrador");
    
    private final String descricao;
    
    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
}
