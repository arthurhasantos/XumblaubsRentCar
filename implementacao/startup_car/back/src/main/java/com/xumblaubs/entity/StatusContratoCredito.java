package com.xumblaubs.entity;

public enum StatusContratoCredito {
    PENDENTE("Pendente"),
    ATIVO("Ativo"),
    QUITADO("Quitado"),
    CANCELADO("Cancelado"),
    VENCIDO("Vencido");

    private final String descricao;

    StatusContratoCredito(String descricao) {
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
