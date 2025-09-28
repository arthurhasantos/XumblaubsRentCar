package com.xumblaubs.dto;

import com.xumblaubs.entity.Banco;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BancoResponse {

    private Long id;
    private String nome;
    private String codigo;
    private String cnpj;
    private String endereco;
    private String telefone;
    private String email;
    private BigDecimal taxaJurosPadrao;
    private BigDecimal limiteCreditoMaximo;
    private Boolean ativo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Construtor
    public BancoResponse() {}

    public BancoResponse(Banco banco) {
        this.id = banco.getId();
        this.nome = banco.getNome();
        this.codigo = banco.getCodigo();
        this.cnpj = banco.getCnpj();
        this.endereco = banco.getEndereco();
        this.telefone = banco.getTelefone();
        this.email = banco.getEmail();
        this.taxaJurosPadrao = banco.getTaxaJurosPadrao();
        this.limiteCreditoMaximo = banco.getLimiteCreditoMaximo();
        this.ativo = banco.getAtivo();
        this.createdAt = banco.getCreatedAt();
        this.updatedAt = banco.getUpdatedAt();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getTaxaJurosPadrao() {
        return taxaJurosPadrao;
    }

    public void setTaxaJurosPadrao(BigDecimal taxaJurosPadrao) {
        this.taxaJurosPadrao = taxaJurosPadrao;
    }

    public BigDecimal getLimiteCreditoMaximo() {
        return limiteCreditoMaximo;
    }

    public void setLimiteCreditoMaximo(BigDecimal limiteCreditoMaximo) {
        this.limiteCreditoMaximo = limiteCreditoMaximo;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "BancoResponse{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", codigo='" + codigo + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
