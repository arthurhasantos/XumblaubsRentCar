package com.xumblaubs.entity;

public enum TipoProprietario {
    CLIENTE("Cliente"),
    EMPRESA("Empresa"),
    BANCO("Banco");
    
    private final String descricao;
    
    TipoProprietario(String descricao) {
        this.descricao = descricao;
    }
    
    public String getDescricao() {
        return descricao;
    }
    
    @Override
    public String toString() {
        return descricao;
    }
}
